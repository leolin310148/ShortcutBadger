package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2017/2/22.
 */
public class SolidLauncherBadger  extends AbsBadger {

    private static final String INTENT_ACTION = "com.majeur.launcher.intent.action.UPDATE_BADGE";
    private static final String EXTRA_PACKAGENAME = "com.majeur.launcher.intent.extra.BADGE_PACKAGE";
    private static final String EXTRA_COUNT = "com.majeur.launcher.intent.extra.BADGE_COUNT";
    private static final String EXTRA_CLASS = "com.majeur.launcher.intent.extra.BADGE_CLASS";


    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();

        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(EXTRA_PACKAGENAME, packageName);
        intent.putExtra(EXTRA_CLASS, className);
        intent.putExtra(EXTRA_COUNT, badgeCount);

        if (canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
        } else {
            throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.majeur.launcher");
    }
}
