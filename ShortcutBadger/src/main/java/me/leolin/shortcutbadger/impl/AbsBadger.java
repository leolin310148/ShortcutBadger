package me.leolin.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

import me.leolin.shortcutbadger.Badger;

/**
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2017/2/22.
 */
public abstract class AbsBadger implements Badger {
    public boolean canResolveBroadcast(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
        return receivers != null && receivers.size() > 0;
    }
}
