package me.leolin.shortcutbadger.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Manages SharedPreferences
 * @author Ravi Vadera
 */
public class PreferenceHelper
{

    public static final String KEY_BADGE_COUNT = "PrefBadgeCount";

    /**
     * Stores Badge Count in Preference
     * @param context Context
     * @param badgeCount Badge Count
     */
    public static void setBadgeCount(Context context, int badgeCount)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_BADGE_COUNT, badgeCount);
        editor.commit();
    }

    /**
     * Gets Current Badge Count from Preference
     * @param context Context
     * @return Current Badge Count
     */
    public static int getBadgeCount(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(KEY_BADGE_COUNT, 0);
    }
}