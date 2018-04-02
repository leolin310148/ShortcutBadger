package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.BroadcastHelper;

/**
 * @author leolin
 */
public class DefaultBadger implements Badger {
    private static final String INTENT_ACTION = IntentConstants.DEFAULT_INTENT_ACTION;
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, badgeCount);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());

        BroadcastHelper.sendDefaultIntentExplicitly(context, intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "fr.neamar.kiss",
                "com.quaap.launchtime",
                "com.quaap.launchtime_official"
        );
    }

    boolean isSupported(Context context) {
        Intent intent = new Intent(INTENT_ACTION);
        return BroadcastHelper.resolveBroadcast(context, intent).size() > 0
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                    && BroadcastHelper.resolveBroadcast(context, new Intent(IntentConstants.DEFAULT_OREO_INTENT_ACTION)).size() > 0);
    }
}
