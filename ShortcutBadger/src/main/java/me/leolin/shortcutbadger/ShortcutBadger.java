package me.leolin.shortcutbadger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import me.leolin.shortcutbadger.impl.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Leo Lin
 */
public abstract class ShortcutBadger {

    private static final List<Class<? extends ShortcutBadger>> BADGERS = new LinkedList<Class<? extends ShortcutBadger>>();

    static {
        BADGERS.add(AdwHomeBadger.class);
//        BADGERS.add(AndroidHomeBadger.class);
//        BADGERS.add(Android2HomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(LGHomeBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SamsungHomeBadger.class);
        BADGERS.add(SolidHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(AsusHomeLauncher.class);
    }

    private static final String MESSAGE_NOT_SUPPORT_BADGE_COUNT = "ShortBadger is currently not support the badgeCount \"%d\"";
    private static final String MESSAGE_NOT_SUPPORT_THIS_HOME = "ShortcutBadger is currently not support the home launcher package \"%s\"";

    private static final int MIN_BADGE_COUNT = 0;
    private static final int MAX_BADGE_COUNT = 99;

    private static ShortcutBadger mShortcutBadger;

    private ShortcutBadger() {
    }

    protected Context mContext;
    private Class<? extends Activity> activityToBadge;

    protected ShortcutBadger(Context context, Class<? extends Activity> activityToBadge) {
        this.mContext = context;
        this.activityToBadge = activityToBadge;
    }

    protected abstract void executeBadge(int badgeCount) throws ShortcutBadgeException;

    public static void setBadge(Context context, int badgeCount) throws ShortcutBadgeException {
        setBadge(context, badgeCount, null);
    }

    public static void setBadge(Context context, int badgeCount, Class<? extends Activity> activityToBadge) throws ShortcutBadgeException {
        //badgeCount should between 0 to 99
        if (badgeCount < MIN_BADGE_COUNT || badgeCount > MAX_BADGE_COUNT) {
            String exceptionMessage = String.format(MESSAGE_NOT_SUPPORT_BADGE_COUNT, badgeCount);
            throw new ShortcutBadgeException(exceptionMessage);
        }

        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;


        try {
            ShortcutBadger shortcutBadger = getShortcutBadger(currentHomePackage, context, activityToBadge);

            //not support this home launcher package
            if (shortcutBadger == null) {
                String exceptionMessage = String.format(MESSAGE_NOT_SUPPORT_THIS_HOME, currentHomePackage);
                throw new ShortcutBadgeException(exceptionMessage);
            }

            shortcutBadger.executeBadge(badgeCount);
        } catch (Throwable e) {
            throw new ShortcutBadgeException("Unable to execute badge:" + e.getMessage());
        }

    }

    private static ShortcutBadger getShortcutBadger(String currentHomePackage, Context context, Class<? extends Activity> activityToBadge) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (mShortcutBadger != null) {
            return mShortcutBadger;
        }
        // Workaround for Meizu:
        // Meizu declare 'com.android.launcher', but hold something else
        // Icons get duplicated on restart after badge change
        if (Build.MANUFACTURER.toLowerCase().contains("meizu")) {
            return null;
        }

        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            mShortcutBadger = new XiaomiHomeBadger(context, activityToBadge);
            return mShortcutBadger;
        }

        for (Class<? extends ShortcutBadger> badger : BADGERS) {
            Constructor<? extends ShortcutBadger> constructor = badger.getConstructor(Context.class, activityToBadge.getClass());
            ShortcutBadger shortcutBadger = constructor.newInstance(context, activityToBadge);
            if (shortcutBadger.getSupportLaunchers().contains(currentHomePackage)) {
                mShortcutBadger = shortcutBadger;
                break;
            }
        }


        return mShortcutBadger;
    }

    public abstract List<String> getSupportLaunchers();

    protected String getEntryActivityName() {
        if (activityToBadge == null) {
            ComponentName componentName = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
            return componentName.getClassName();
        } else {
            return activityToBadge.getName();
        }
    }

    protected String getContextPackageName() {
        return mContext.getPackageName();
    }
}
