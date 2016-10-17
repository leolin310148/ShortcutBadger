package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.CloseHelper;

/**
 * Created by NingSo on 2016/10/16.下午4:30
 *
 * @author: NingSo
 * @Email: ningso.ping@gmail.com
 */

public class LenovoHomeBager implements Badger {
    private static int haslenovoLanucher = -1;
    private static final String LENOVO_PACKAGE_NAME = "com.lenovo.launcher";
    private static final String CONTENT_URI = "content://com.lenovo.launcher.badge/lenovo_badges";
    private static final String[] CONTENT_PROJECTION = new String[]{"package", "class", "badgecount", "extraData"};

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        String launcherClassName = componentName.getClassName();
        if (launcherClassName == null) {
            Log.d(LenovoHomeBager.class.getSimpleName(), "Main activity is null");
            return;
        }
        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(mUri, CONTENT_PROJECTION, "package=?", new String[]{componentName.getPackageName()}, null);
            if (cursor != null) {
                int count = cursor.getCount();
                if (count <= 0) {
                    ContentValues contentValues = getContentValues(componentName, badgeCount);
                    contentResolver.insert(mUri, contentValues);
                } else {
                    while (cursor.moveToFirst()) {
                        ContentValues contentValues = getContentValues(componentName, badgeCount);
                        contentResolver.update(mUri, contentValues, "package=?", new String[]{componentName.getPackageName()});
                    }
                }
            } else {
                ContentValues contentValues = getContentValues(componentName, badgeCount);
                contentResolver.insert(mUri, contentValues);
            }
        } finally {
            CloseHelper.close(cursor);
        }
    }

    private ContentValues getContentValues(ComponentName componentName, int badgeCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("package", componentName.getPackageName());
        contentValues.put("class", componentName.getClassName());
        contentValues.put("badgecount", badgeCount);
        contentValues.put("extraData", "");
        return contentValues;
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Collections.singletonList("com.lenovo.launcher");
    }

    public static boolean islenovoLanucher(Context context) {
        if (Build.VERSION.SDK_INT < 15) {
            return false;
        }
        if (haslenovoLanucher == -1) {
            try {
                if (Float.parseFloat(context.getPackageManager().getPackageInfo(LENOVO_PACKAGE_NAME, 0).versionName.substring(0, 3)) >= 6.7f) {
                    haslenovoLanucher = 1;
                    return true;
                }
                haslenovoLanucher = 0;
                return false;
            } catch (PackageManager.NameNotFoundException e) {
                haslenovoLanucher = 0;
                return false;
            } catch (Exception e1) {
                haslenovoLanucher = 0;
                return false;
            }
        } else return haslenovoLanucher == 1;
    }
}