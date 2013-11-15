ShortcutBadger:
===================================

The ShortcutBadger makes your Android App showing the count of unread messages as a badge on your App shortcut!

# Currently support launchers:<br/>

Android native Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_android.png)

Sony Home Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_sony.png)

Samsung Touchwiz<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_samsung.png)

LG Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_lg.png)



HOW TO USE
===================================
<br/>1. Download the jar file named ShortcutBadger-X.X.jar in the target folder.
<br/>2. Add the jar file into the folder "lib" in your Android project.
<br/>3. Do not forget to add these permissions to your Androidmanifest.xml.

        <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/>
        <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE"/>
        <uses-permission android:name="com.htc.launcher.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="com.htc.launcher.permission.READ_SETTINGS"/>
        <uses-permission android:name="com.sec.android.provider.badge.permission.READ"/>
        <uses-permission android:name="com.sec.android.provider.badge.permission.WRITE"/>
<br/>4. Add the codes below:

        int badgeCount = 1;
        try {
            ShortcutBadger.setBadge(getApplicationContext(), badgeCount);
        } catch (ShortcutBadgeException e) {
            //handle the Exception
        }
<br/>5. If you want to remove the badge, just set the badgeCount as 0.
<br/>