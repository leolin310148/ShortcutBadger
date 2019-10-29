package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * @author leolin
 */
public class TranssionHomeBadger implements Badger {

    private static final String ACTION_UNREAD_CHANGED = "com.mediatek.action.UNREAD_CHANGED";
    private static final String EXTRA_UNREAD_COMPONENT = "com.mediatek.intent.extra.UNREAD_COMPONENT";
    private static final String EXTRA_UNREAD_NUMBER = "com.mediatek.intent.extra.UNREAD_NUMBER";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Intent intent = new Intent(ACTION_UNREAD_CHANGED);
        intent.putExtra(EXTRA_UNREAD_COMPONENT,componentName);
        intent.putExtra(EXTRA_UNREAD_NUMBER, badgeCount);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.transsion.XOSLauncher",
                "com.transsion.hilauncher",
                "com.transsion.itel.launcher"
        );
    }
}

