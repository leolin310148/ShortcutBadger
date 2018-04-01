package me.leolin.shortcutbadger.impl;

import android.os.Build;

interface IntentConstants {

    String DEFAULT_INTENT_ACTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? "me.leolin.shortcutbadger.BADGE_COUNT_UPDATE"
            : "android.intent.action.BADGE_COUNT_UPDATE";
}
