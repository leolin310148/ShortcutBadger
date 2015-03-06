package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import me.leolin.shortcutbadger.impl.*;


/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午5:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShortcutBadger {
    private static final String HOME_PACKAGE_SONY1 = "com.sonyericsson.home";
    private static final String HOME_PACKAGE_SONY2 = "com.anddoes.launcher";
    private static final String HOME_PACKAGE_SAMSUNG1 = "com.sec.android.app.launcher";
    private static final String HOME_PACKAGE_SAMSUNG2 = "com.sec.android.app.twlauncher";
    private static final String HOME_PACKAGE_LG1 = "com.lge.launcher";
    private static final String HOME_PACKAGE_LG2 = "com.lge.launcher2";
    private static final String HOME_PACKAGE_HTC = "com.htc.launcher";
    private static final String HOME_PACKAGE_APEX = "com.anddoes.launcher";
    private static final String HOME_PACKAGE_ADW = "org.adw.launcher";
    private static final String HOME_PACKAGE_ADW_EX = "org.adwfreak.launcher";
    private static final String HOME_PACKAGE_NOVA = "com.teslacoilsw.launcher";
    private static final String HOME_PACKAGE_ANDROID1 = "com.android.launcher";
    private static final String HOME_PACKAGE_ANDROID2 = "com.android.launcher2";
    private static final String HOME_PACKAGE_ANDROID3 = "com.google.android.googlequicksearchbox";
    private static final String HOME_PACKAGE_SOLID = "com.majeur.launcher";


    private static final String MESSAGE_NOT_SUPPORT_BADGE_COUNT = "ShortBadger is currently not support the badgeCount \"%d\"";
    private static final String MESSAGE_NOT_SUPPORT_THIS_HOME = "ShortcutBadger is currently not support the home launcher package \"%s\"";

    private static final int MIN_BADGE_COUNT = 0;
    private static final int MAX_BADGE_COUNT = 99;

    private static ShortcutBadger sShortcutBadger;

    private ShortcutBadger() {
    }

    protected Context mContext;

    protected ShortcutBadger(Context context) {
        this.mContext = context;
    }

    protected abstract void executeBadge(int badgeCount) throws ShortcutBadgeException;

    public static void setBadge(Context context, int badgeCount) throws ShortcutBadgeException {
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


        ShortcutBadger shortcutBadger = getShortcutBadger(currentHomePackage, context);

        //not support this home launcher package
        if (shortcutBadger == null) {
            String exceptionMessage = String.format(MESSAGE_NOT_SUPPORT_THIS_HOME, currentHomePackage);
            throw new ShortcutBadgeException(exceptionMessage);
        }
        try {
            shortcutBadger.executeBadge(badgeCount);
        } catch (Throwable e) {
            throw new ShortcutBadgeException("Unable to execute badge:" + e.getMessage());
        }
        shortcutBadger.executeBadge(badgeCount);

    }

    private static ShortcutBadger getShortcutBadger(String currentHomePackage, Context context) {
        if (sShortcutBadger != null) {
            return sShortcutBadger;
        }

        // Workaround for Meizu:
        // Meizu declare 'com.android.launcher', but hold something else
        // Icons get duplicated on restart after badge change
        if (Build.MANUFACTURER.toLowerCase().contains("meizu")) {
            return null;
        }


        if (HOME_PACKAGE_SONY1.equals(currentHomePackage) ||
                HOME_PACKAGE_SONY2.equals(currentHomePackage)) {
            sShortcutBadger = new SonyHomeBadger(context);
        } else if (HOME_PACKAGE_SAMSUNG1.equals(currentHomePackage) ||
                HOME_PACKAGE_SAMSUNG2.equals(currentHomePackage)) {
            sShortcutBadger = new SamsungHomeBadger(context);
        } else if (HOME_PACKAGE_LG1.equals(currentHomePackage) ||
                HOME_PACKAGE_LG2.equals(currentHomePackage)) {
            sShortcutBadger = new LGHomeBadger(context);
        } else if (HOME_PACKAGE_HTC.equals(currentHomePackage)) {
            sShortcutBadger = new NewHtcHomeBadger(context);
        } else if (HOME_PACKAGE_ANDROID1.equals(currentHomePackage) ||
                HOME_PACKAGE_ANDROID2.equals(currentHomePackage) ||
                HOME_PACKAGE_ANDROID3.equals(currentHomePackage)) {
            sShortcutBadger = new AndroidHomeBadger(context);
        } else if (HOME_PACKAGE_APEX.equals(currentHomePackage)) {
            sShortcutBadger = new ApexHomeBadger(context);
        } else if (HOME_PACKAGE_ADW.equals(currentHomePackage)
                || HOME_PACKAGE_ADW_EX.equals(currentHomePackage)) {
            sShortcutBadger = new AdwHomeBadger(context);
        } else if (HOME_PACKAGE_NOVA.equals(currentHomePackage)) {
            sShortcutBadger = new NovaHomeBadger(context);
        } else if (HOME_PACKAGE_SOLID.equals(currentHomePackage)) {
            sShortcutBadger = new SolidHomeBadger(context);
        }


        return sShortcutBadger;
    }

    protected String getEntryActivityName() {
        ComponentName componentName = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        return componentName.getClassName();
    }

    protected String getContextPackageName() {
        return mContext.getPackageName();
    }
}
