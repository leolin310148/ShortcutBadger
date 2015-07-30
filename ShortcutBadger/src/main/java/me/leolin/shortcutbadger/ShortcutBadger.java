package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import me.leolin.shortcutbadger.impl.*;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Leo Lin
 */
public abstract class ShortcutBadger {

    private static final String LOG_TAG = ShortcutBadger.class.getSimpleName();

    private static final List<Class<? extends ShortcutBadger>> BADGERS = new LinkedList<Class<? extends ShortcutBadger>>();

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

    private static ShortcutBadger mShortcutBadger;

    public static ShortcutBadger with(Context context) {
        return getShortcutBadger(context);
    }


    public static void setBadge(Context context, int badgeCount) throws ShortcutBadgeException {
        try {
            getShortcutBadger(context).executeBadge(badgeCount);
        } catch (Throwable e) {
            throw new ShortcutBadgeException("Unable to execute badge:" + e.getMessage());
        }

    }

    private static ShortcutBadger getShortcutBadger(Context context) {
        if (mShortcutBadger != null) {
            return mShortcutBadger;
        }
        Log.d(LOG_TAG, "Finding badger");

        //find the home launcher Package
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String currentHomePackage = resolveInfo.activityInfo.packageName;

            if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                mShortcutBadger = new XiaomiHomeBadger(context);
                return mShortcutBadger;
            }

            for (Class<? extends ShortcutBadger> badger : BADGERS) {
                Constructor<? extends ShortcutBadger> constructor = badger.getConstructor(Context.class);
                ShortcutBadger shortcutBadger = constructor.newInstance(context);
                if (shortcutBadger.getSupportLaunchers().contains(currentHomePackage)) {
                    mShortcutBadger = shortcutBadger;
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        if (mShortcutBadger == null) {
            mShortcutBadger = new DefaultBadger(context);
        }

        Log.d(LOG_TAG, "Returning badger:" + mShortcutBadger.getClass().getCanonicalName());
        return mShortcutBadger;
    }


    private ShortcutBadger() {
    }

    protected Context mContext;

    protected ShortcutBadger(Context context) {
        this.mContext = context;
    }

    protected abstract void executeBadge(int badgeCount) throws ShortcutBadgeException;

    protected abstract List<String> getSupportLaunchers();

    protected String getEntryActivityName() {
        ComponentName componentName = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        return componentName.getClassName();
    }

    protected String getContextPackageName() {
        return mContext.getPackageName();
    }

    public void count(int count) {
        try {
            executeBadge(count);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    public void remove() {
        count(0);
    }
}
