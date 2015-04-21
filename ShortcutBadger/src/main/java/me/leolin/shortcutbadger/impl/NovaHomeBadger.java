package me.leolin.shortcutbadger.impl;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import java.util.Arrays;
import java.util.List;

/**
 * Shortcut Badger support for Nova Launcher.
 * TeslaUnread must be installed.
 * User: Gernot Pansy
 * Date: 2014/11/03
 * Time: 7:15
 */
public class NovaHomeBadger extends ShortcutBadger {

    private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    public NovaHomeBadger(final Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(final int badgeCount) throws ShortcutBadgeException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TAG, getContextPackageName() + "/" + getEntryActivityName());
            contentValues.put(COUNT, badgeCount);
            mContext.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
        } catch (IllegalArgumentException ex) {
            /* Fine, TeslaUnread is not installed. */
        } catch (Exception ex) {

            /* Some other error, possibly because the format
            of the ContentValues are incorrect. */

            throw new ShortcutBadgeException(ex.getMessage());
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.teslacoilsw.launcher");
    }
}
