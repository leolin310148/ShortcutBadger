package me.leolin.shortcutbadger.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;
import me.leolin.shortcutbadger.util.ImageUtil;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午7:15
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class hTCHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.htc.launcher.settings/favorites?notify=true";

    public hTCHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri mUri = Uri.parse(CONTENT_URI);
        String appName = mContext.getResources().getText(mContext.getResources().getIdentifier("app_name",
                "string", getContextPackageName())).toString();

        boolean supportNotifyCount = true;
        try {
            Cursor cursor = contentResolver.query(mUri, new String[]{"notifyCount"}, "title=?", new String[]{appName}, null);
        } catch (Throwable e) {
            supportNotifyCount = false;
        }

        if (supportNotifyCount) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("notifyCount", badgeCount);
            contentResolver.update(mUri, contentValues, "title=?", new String[]{appName});
        } else {
            byte[] bytes = ImageUtil.drawBadgeOnAppIcon(mContext, badgeCount);


            ContentValues contentValues = new ContentValues();
            contentValues.put("iconType", 1);
            contentValues.put("itemType", 1);
            contentValues.put("icon", bytes);
            contentResolver.update(mUri, contentValues, "title=?", new String[]{appName});
        }


    }
}
