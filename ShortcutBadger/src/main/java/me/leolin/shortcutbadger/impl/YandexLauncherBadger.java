package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.util.Collections;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * @author Nikolay Pakhomov
 * created 16/04/2018
 */
public class YandexLauncherBadger implements Badger {

    private static final String CONTENT_URI = "content://com.yandex.launcher.badges";

    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_PACKAGE = "package";
    private static final String COLUMN_BADGES_COUNT = "badges_count";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CLASS, componentName.getClassName());
        contentValues.put(COLUMN_PACKAGE, componentName.getPackageName());
        contentValues.put(COLUMN_BADGES_COUNT, badgeCount);

        context.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Collections.singletonList("com.yandex.launcher");
    }
}
