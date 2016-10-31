package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import me.leolin.shortcutbadger.impl.AdwHomeBadger;
import me.leolin.shortcutbadger.impl.ApexHomeBadger;
import me.leolin.shortcutbadger.impl.AsusHomeLauncher;
import me.leolin.shortcutbadger.impl.DefaultBadger;
import me.leolin.shortcutbadger.impl.HuaweiHomeBadger;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import me.leolin.shortcutbadger.impl.NovaHomeBadger;
import me.leolin.shortcutbadger.impl.OPPOHomeBader;
import me.leolin.shortcutbadger.impl.SamsungHomeBadger;
import me.leolin.shortcutbadger.impl.SonyHomeBadger;
import me.leolin.shortcutbadger.impl.XiaomiHomeBadger;
import me.leolin.shortcutbadger.impl.ZukHomeBadger;


/**
 * @author Leo Lin
 */
public final class ShortcutBadger {

    private static final String LOG_TAG = "ShortcutBadger";

    private static final List<Class<? extends Badger>> BADGERS = new LinkedList<Class<? extends Badger>>();

    static {
        BADGERS.add(AdwHomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(AsusHomeLauncher.class);
        BADGERS.add(HuaweiHomeBadger.class);
//        BADGERS.add(LGHomeBadger.class);
        BADGERS.add(OPPOHomeBader.class);
        BADGERS.add(SamsungHomeBadger.class);
        BADGERS.add(ZukHomeBadger.class);
    }

    private static Badger sShortcutBadger;
    private static ComponentName sComponentName;

    /**
     * Tries to update the notification count
     *
     * @param context    Caller context
     * @param badgeCount Desired badge count
     * @return true in case of success, false otherwise
     */
    public static boolean applyCount(Context context, int badgeCount) {
        try {
            applyCountOrThrow(context, badgeCount);
            return true;
        } catch (ShortcutBadgeException e) {
            if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
                Log.d(LOG_TAG, "Unable to execute badge", e);
            }
            return false;
        }
    }

    /**
     * Tries to update the notification count, throw a {@link ShortcutBadgeException} if it fails
     *
     * @param context    Caller context
     * @param badgeCount Desired badge count
     */
    public static void applyCountOrThrow(Context context, int badgeCount) throws ShortcutBadgeException {
        if (sShortcutBadger == null) {
            boolean launcherReady = initBadger(context);

            if (!launcherReady)
                throw new ShortcutBadgeException("No default launcher available");
        }

        try {
            sShortcutBadger.executeBadge(context, sComponentName, badgeCount);
        } catch (Exception e) {
            throw new ShortcutBadgeException("Unable to execute badge", e);
        }
    }

    /**
     * Tries to remove the notification count
     *
     * @param context Caller context
     * @return true in case of success, false otherwise
     */
    public static boolean removeCount(Context context) {
        return applyCount(context, 0);
    }

    /**
     * Tries to remove the notification count, throw a {@link ShortcutBadgeException} if it fails
     *
     * @param context Caller context
     */
    public static void removeCountOrThrow(Context context) throws ShortcutBadgeException {
        applyCountOrThrow(context, 0);
    }

    // Initialize Badger if a launcher is availalble (eg. set as default on the device)
    // Returns true if a launcher is available, in this case, the Badger will be set and sShortcutBadger will be non null.
    private static boolean initBadger(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntent == null) {
            Log.e(LOG_TAG, "Unable to find launch intent for package " + context.getPackageName());
            return false;
        }

        sComponentName = launchIntent.getComponent();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfo == null || resolveInfo.activityInfo.name.toLowerCase().contains("resolver"))
            return false;

        String currentHomePackage = resolveInfo.activityInfo.packageName;

        for (Class<? extends Badger> badger : BADGERS) {
            Badger shortcutBadger = null;
            try {
                shortcutBadger = badger.newInstance();
            } catch (Exception ignored) {
            }
            if (shortcutBadger != null && shortcutBadger.getSupportLaunchers().contains(currentHomePackage)) {
                sShortcutBadger = shortcutBadger;
                break;
            }
        }

        if (sShortcutBadger == null) {
            if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi"))
                sShortcutBadger = new XiaomiHomeBadger();
            else if (Build.MANUFACTURER.equalsIgnoreCase("ZUK"))
                sShortcutBadger = new ZukHomeBadger();
            else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO"))
                sShortcutBadger = new OPPOHomeBader();
            else
                sShortcutBadger = new DefaultBadger();
        }

        return true;
    }

    // Avoid anybody to instantiate this class
    private ShortcutBadger() {

    }
}
