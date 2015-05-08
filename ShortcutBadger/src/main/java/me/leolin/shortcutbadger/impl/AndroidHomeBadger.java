package me.leolin.shortcutbadger.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;
import me.leolin.shortcutbadger.util.ImageUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Leo Lin
 */
@Deprecated
public class AndroidHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.android.launcher2.settings/favorites?notify=true";

    public AndroidHomeBadger(Context context, Class<? extends Activity> activityToBadge) {
        super(context, activityToBadge);
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

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.android.launcher"
        );
    }
}
