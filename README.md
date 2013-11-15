ShortcutBadger:
===================================
The ShortcutBadger makes your Android App showing the count of unread messages as a badge on your App shortcut!
-----------------------------------
Currently support these launchers:

# Android native Launcher
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_android.png)

# Sony Home Launcher
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_sony.png)

# Samsung Touchwiz
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_samsung.png)

# LG Launcher
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_lg.png)



----HOW TO USE----
1. Download the jar file named ShortcutBadger-X.X.jar in the target folder.
2. Add the jar file into the folder "lib" in your Android project.
3. Do not forget to add these permissions to your Androidmanifest.xml.

        <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/>
        <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE"/>
        <uses-permission android:name="com.htc.launcher.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="com.htc.launcher.permission.READ_SETTINGS"/>
        <uses-permission android:name="com.sec.android.provider.badge.permission.READ"/>
        <uses-permission android:name="com.sec.android.provider.badge.permission.WRITE"/>
