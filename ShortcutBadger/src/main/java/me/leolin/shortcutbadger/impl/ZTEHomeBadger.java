package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

public class ZTEHomeBadger implements Badger {

    public static final String PACKAGE_NAME_MFV = "com.zte.mifavor.launcher";
    public static final String PACKAGE_NAME_STOCK = "com.android.launcher3";
    private static boolean mMFVBadger = false;

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount)
            throws ShortcutBadgeException {
        Bundle extra = new Bundle();
        extra.putInt("app_badge_count", badgeCount);
        extra.putString("app_badge_component_name", componentName.flattenToString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mMFVBadger) {
                context.getContentResolver().call(
                        Uri.parse("content://com.zte.mifavor.launcher.unreadbadge"),
                        "setAppUnreadCount", null, extra);
            } else {
                context.getContentResolver().call(
                        Uri.parse("content://com.android.launcher3.cornermark.unreadbadge"),
                        "setAppUnreadCount", null, extra);
            }
        }
    }

    public static boolean isMFVLauncher(String currentHomePackage) {
        if (PACKAGE_NAME_MFV.equals(currentHomePackage)) {
            mMFVBadger = true;
        } else {
            mMFVBadger = false;
        }
        return mMFVBadger;
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Collections.singletonList(PACKAGE_NAME_MFV);
    }
} 

