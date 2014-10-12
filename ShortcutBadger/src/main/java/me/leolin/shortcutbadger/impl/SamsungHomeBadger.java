package me.leolin.shortcutbadger.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午7:15
 * To change this template use File | Settings | File Templates.
 */
public class SamsungHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";

    public SamsungHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(mUri, new String[]{"_id",}, "package=?", new String[]{getContextPackageName()}, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            ContentValues contentValues = new ContentValues();
            contentValues.put("badgecount", badgeCount);
            contentResolver.update(mUri, contentValues, "_id=?", new String[]{String.valueOf(id)});
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("package", getContextPackageName());
            contentValues.put("class", getEntryActivityName());
            contentValues.put("badgecount", badgeCount);
            contentResolver.insert(mUri, contentValues);
        }


    }
}
