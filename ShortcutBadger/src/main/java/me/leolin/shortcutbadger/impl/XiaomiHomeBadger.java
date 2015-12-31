package me.leolin.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author leolin
 */
public class XiaomiHomeBadger extends ShortcutBadger {

    public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";
    public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
    public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";

    public XiaomiHomeBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        try {
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification, String.valueOf(badgeCount == 0 ? "" : badgeCount));
        } catch (Exception e) {
            Intent localIntent = new Intent(
                    INTENT_ACTION);
            localIntent.putExtra(EXTRA_UPDATE_APP_COMPONENT_NAME, getContextPackageName() + "/" + getEntryActivityName());
            localIntent.putExtra(EXTRA_UPDATE_APP_MSG_TEXT, String.valueOf(badgeCount == 0 ? "" : badgeCount));
            mContext.sendBroadcast(localIntent);
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.miui.miuilite",
                "com.miui.home",
                "com.miui.miuihome",
                "com.miui.miuihome2",
                "com.miui.mihome",
                "com.miui.mihome2"
        );
    }
}
