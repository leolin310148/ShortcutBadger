package me.leolin.shortcutbadger.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;
import me.leolin.shortcutbadger.util.CloseHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @author Leo Lin
 * Deprecated, Samesung devices will use DefaultBadger
 */
@Deprecated
public class SamsungHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";
    private static final String[] CONTENT_PROJECTION = new String[]{"_id","class"};

    public SamsungHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(mUri, CONTENT_PROJECTION, "package=?", new String[]{getContextPackageName()}, null);
            if (cursor != null) {
                String entryActivityName = getEntryActivityName();
                boolean entryActivityExist = false;
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    ContentValues contentValues = getContentValues(badgeCount, false);
                    contentResolver.update(mUri, contentValues, "_id=?", new String[]{String.valueOf(id)});
                    if (entryActivityName.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                        entryActivityExist = true;
                    }
                }

                if (!entryActivityExist) {
                    ContentValues contentValues = getContentValues(badgeCount, true);
                    contentResolver.insert(mUri, contentValues);
                }
            }
        } finally {
            CloseHelper.close(cursor);
        }
    }

    private ContentValues getContentValues(int badgeCount, boolean isInsert) {
        ContentValues contentValues = new ContentValues();
        if (isInsert) {
            contentValues.put("package", getContextPackageName());
            contentValues.put("class", getEntryActivityName());
        }

        contentValues.put("badgecount", badgeCount);

        return contentValues;
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.sec.android.app.launcher",
                "com.sec.android.app.twlauncher"
        );
    }
}
