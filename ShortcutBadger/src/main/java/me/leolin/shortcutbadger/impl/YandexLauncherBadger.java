package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * @author Nikolay Pakhomov
 * created 16/04/2018
 */
public class YandexLauncherBadger implements Badger {

    public static final String PACKAGE_NAME = "com.yandex.launcher";

    private static final String AUTHORITY = "com.yandex.launcher.badges_external";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private static final String METHOD_TO_CALL = "setBadgeNumber";

    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_PACKAGE = "package";
    private static final String COLUMN_BADGES_COUNT = "badges_count";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Bundle extras = new Bundle();
        extras.putString(COLUMN_CLASS, componentName.getClassName());
        extras.putString(COLUMN_PACKAGE, componentName.getPackageName());
        extras.putString(COLUMN_BADGES_COUNT, String.valueOf(badgeCount));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            context.getContentResolver().call(CONTENT_URI, METHOD_TO_CALL, null, extras);
        }
    }

    public static boolean isVersionSupported(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            try {
                context.getContentResolver().call(CONTENT_URI, "", null, null);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Collections.singletonList(PACKAGE_NAME);
    }
}
