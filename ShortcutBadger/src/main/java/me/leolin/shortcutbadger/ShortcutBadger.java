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
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import me.leolin.shortcutbadger.impl.NovaHomeBadger;
import me.leolin.shortcutbadger.impl.SolidHomeBadger;
import me.leolin.shortcutbadger.impl.SonyHomeBadger;
import me.leolin.shortcutbadger.impl.XiaomiHomeBadger;


/**
 * @author Leo Lin
 */
public final class ShortcutBadger {

    private static final String LOG_TAG = ShortcutBadger.class.getSimpleName();

    private static final List<Class<? extends Badger>> BADGERS = new LinkedList<Class<? extends Badger>>();

    static {
        BADGERS.add(AdwHomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SolidHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(AsusHomeLauncher.class);
//        BADGERS.add(LGHomeBadger.class);
//        BADGERS.add(SamsungHomeBadger.class);
    }

    private static Badger sShortcutBadger;
    private static ComponentName sComponentName;

    /**
     * Tries to update the notification count
     * @param context Caller context
     * @param badgeCount Desired badge count
     * @return true in case of success, false otherwise
     */
    public static boolean applyCount(Context context, int badgeCount) {
        try {
            applyCountOrThrow(context, badgeCount);
            return true;
        } catch (ShortcutBadgeException e) {
            Log.e(LOG_TAG, "Unable to execute badge:" + e.getMessage());
            return false;
        }
    }

    /**
     * Tries to update the notification count, throw a {@link ShortcutBadgeException} if it fails
     * @param context Caller context
     * @param badgeCount Desired badge count
     */
    public static void applyCountOrThrow(Context context, int badgeCount) throws ShortcutBadgeException {
        if (sShortcutBadger == null)
            initBadger(context);

        try {
            sShortcutBadger.executeBadge(context, sComponentName, badgeCount);
        } catch (Throwable e) {
            throw new ShortcutBadgeException("Unable to execute badge:" + e.getMessage());
        }
    }

    /**
     * Tries to remove the notification count
     * @param context Caller context
     * @return true in case of success, false otherwise
     */
    public static boolean removeCount(Context context) {
        return applyCount(context, 0);
    }

    /**
     * Tries to remove the notification count, throw a {@link ShortcutBadgeException} if it fails
     * @param context Caller context
     */
    public static void removeCountOrThrow(Context context) throws ShortcutBadgeException {
        applyCountOrThrow(context, 0);
    }

    private static void initBadger(Context context) {
        //find the home launcher Package
        try {
            sComponentName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent();

            Log.d(LOG_TAG, "Finding badger");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String currentHomePackage = resolveInfo.activityInfo.packageName;

            for (Class<? extends Badger> badger : BADGERS) {
                Badger shortcutBadger = badger.newInstance();
                if (shortcutBadger.getSupportLaunchers().contains(currentHomePackage)) {
                    sShortcutBadger = shortcutBadger;
                    break;
                }
            }

            if (sShortcutBadger == null && Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                sShortcutBadger = new XiaomiHomeBadger();
                return;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        if (sShortcutBadger == null)
            sShortcutBadger = new DefaultBadger();


        Log.d(LOG_TAG, "Current badger:" + sShortcutBadger.getClass().getCanonicalName());
    }

    // Avoid anybody to instantiate this class
    private ShortcutBadger() {

    }
}
