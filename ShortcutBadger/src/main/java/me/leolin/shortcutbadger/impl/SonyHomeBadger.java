package me.leolin.shortcutbadger.impl;

import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;


/**
 * @author Leo Lin
 */
public class SonyHomeBadger implements Badger {

    private static final String INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
    private static final String INTENT_EXTRA_PACKAGE_NAME = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME";
    private static final String INTENT_EXTRA_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE";
    private static final String INTENT_EXTRA_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";

    private static final String PROVIDER_CONTENT_URI = "content://com.sonymobile.home.resourceprovider/badge";
    private static final String PROVIDER_COLUMNS_BADGE_COUNT = "badge_count";
    private static final String PROVIDER_COLUMNS_PACKAGE_NAME = "package_name";
    private static final String PROVIDER_COLUMNS_ACTIVITY_NAME = "activity_name";
    private static final String SONY_HOME_PROVIDER_NAME = "com.sonymobile.home.resourceprovider";
    private final Uri BADGE_CONTENT_URI = Uri.parse(PROVIDER_CONTENT_URI);

    private AsyncQueryHandler mQueryHandler;

    @Override
    public void executeBadge(Context context, ComponentName componentName,
                             int badgeCount) throws ShortcutBadgeException {
        if (sonyBadgeContentProviderExists(context)) {
            executeBadgeByContentProvider(context, componentName, badgeCount);
        } else {
            executeBadgeByBroadcast(context, componentName, badgeCount);
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.sonyericsson.home");
    }

    private static void executeBadgeByBroadcast(Context context, ComponentName componentName,
                                                int badgeCount) {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_PACKAGE_NAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());
        intent.putExtra(INTENT_EXTRA_MESSAGE, String.valueOf(badgeCount));
        intent.putExtra(INTENT_EXTRA_SHOW_MESSAGE, badgeCount > 0);
        context.sendBroadcast(intent);
    }

    /**
     * Send request to Sony badge content provider to set badge in Sony home launcher.
     *
     * @param context       the context to use
     * @param componentName the componentName to use
     * @param badgeCount    the badge count
     */
    private void executeBadgeByContentProvider(Context context, ComponentName componentName,
                                               int badgeCount) {
        if (badgeCount < 0) {
            return;
        }

        if (mQueryHandler == null) {
            mQueryHandler = new AsyncQueryHandler(
                    context.getApplicationContext().getContentResolver()) {
            };
        }
        insertBadgeAsync(badgeCount, componentName.getPackageName(), componentName.getClassName());
    }

    /**
     * Insert a badge associated with the specified package and activity names
     * asynchronously. The package and activity names must correspond to an
     * activity that holds an intent filter with action
     * "android.intent.action.MAIN" and category
     * "android.intent.category.LAUNCHER" in the manifest. Also, it is not
     * allowed to publish badges on behalf of another client, so the package and
     * activity names must belong to the process from which the insert is made.
     * To be able to insert badges, the app must have the PROVIDER_INSERT_BADGE
     * permission in the manifest file. In case these conditions are not
     * fulfilled, or any content values are missing, there will be an unhandled
     * exception on the background thread.
     *
     * @param badgeCount   the badge count
     * @param packageName  the package name
     * @param activityName the activity name
     */
    private void insertBadgeAsync(int badgeCount, String packageName, String activityName) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(PROVIDER_COLUMNS_BADGE_COUNT, badgeCount);
        contentValues.put(PROVIDER_COLUMNS_PACKAGE_NAME, packageName);
        contentValues.put(PROVIDER_COLUMNS_ACTIVITY_NAME, activityName);

        // The badge must be inserted on a background thread
        mQueryHandler.startInsert(0, null, BADGE_CONTENT_URI, contentValues);
    }

    /**
     * Check if the latest Sony badge content provider exists .
     *
     * @param context the context to use
     * @return true if Sony badge content provider exists, otherwise false.
     */
    private static boolean sonyBadgeContentProviderExists(Context context) {
        boolean exists = false;
        ProviderInfo info = context.getPackageManager().resolveContentProvider(SONY_HOME_PROVIDER_NAME, 0);
        if (info != null) {
            exists = true;
        }
        return exists;
    }
}
