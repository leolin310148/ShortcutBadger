package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

public class ZTEHomeBadger implements Badger {

    private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount)
            throws ShortcutBadgeException {
        Bundle extra = new Bundle();
        extra.putInt("app_badge_count", badgeCount);
        extra.putString("app_badge_component_name", componentName.flattenToString());
        try {
            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB) {
                context.getContentResolver().call(
                        Uri.parse("content://com.android.launcher3.cornermark.unreadbadge"),
                        "setAppUnreadCount", null, extra);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ZTEHomeBadger", "executeBadge() exception!!");
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return new ArrayList<String>(0);
    }
} 

