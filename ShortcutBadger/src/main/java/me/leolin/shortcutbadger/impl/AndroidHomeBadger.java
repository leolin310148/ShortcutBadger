package me.leolin.shortcutbadger.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import me.leolin.shortcutbadger.util.ImageUtil;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午7:15
 * To change this template use File | Settings | File Templates.
 */
public class AndroidHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.android.launcher2.settings/favorites?notify=true";

    public AndroidHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        byte[] bytes = ImageUtil.drawBadgeOnAppIcon(mContext, badgeCount);
        String appName = mContext.getResources().getText(mContext.getResources().getIdentifier("app_name",
                "string", getContextPackageName())).toString();

        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = mContext.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iconType", 1);
        contentValues.put("itemType", 1);
        contentValues.put("icon", bytes);
        contentResolver.update(mUri, contentValues, "title=?", new String[]{appName});
    }
}
