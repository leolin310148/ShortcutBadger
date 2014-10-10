package com.shortcutBadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.shortcutBadger.ShortcutBadgeException;
import com.shortcutBadger.ShortcutBadger;

/**
 * @author Leolin
 */
public class NewHtcHomeBadger extends ShortcutBadger {

    public NewHtcHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        try {
            Intent localIntent1 = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
            localIntent1.putExtra("packagename", getContextPackageName());
            localIntent1.putExtra("count", badgeCount);
            mContext.sendBroadcast(localIntent1);
            Intent localIntent2 = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
            ComponentName localComponentName = new ComponentName(getContextPackageName(), getEntryActivityName());
            localIntent2.putExtra("com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
            localIntent2.putExtra("com.htc.launcher.extra.COUNT", badgeCount);
            mContext.sendBroadcast(localIntent2);
        } catch (Exception localException) {
            Log.e("CHECK", "HTC : " + localException.getLocalizedMessage());
        }
    }
}
