ShortcutBadger: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.leolin/ShortdutBadger/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.leolin/ShortcutBadger)
===================================

The ShortcutBadger makes your Android App showing the count of unread messages as a badge on your App shortcut!

# Support launchers:<br/>

<table>
<tr>
<td width="130">
<h3>Android(before 4.4)</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_android.png"/>
</td>
<td width="130">
<h3>Sony</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_sony.png"/>
</td>
<td width="130">
<h3>Samsung</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_samsung.png"/>
</td>
</tr>
<tr>
<td width="130">
<h3>LG</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_lg.png"/>
</td>
<td width="130">
<h3>HTC</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_htc.png"/>
</td>
<td width="130">
<h3>Xiaomi</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_xiaomi.png"/>
</td>
</tr>
<tr>
<td width="130">
<h3>ASUS</h3>
<br>
<img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_asus.png"/>
</td>
<td width="130">
</td>
<td width="130">
</td>
</tr>
</table> 

Nova launcher with TeslaUnread,Apex launcher,Adw Launcher provided by [notz](https://github.com/notz)</br/>
Solid launcher provided by [MajeurAndroid](https://github.com/MajeurAndroid)


USAGE
===================================
<br/>1. Add mavenCentral to your build script.

        repositories {
            mavenCentral()
        }
    
<br/>2. Add dependencies for ShortcutBadger, it's available from maven now.
        
        dependencies {
            compile 'me.leolin:ShortcutBadger:1.0.10@aar'
        }

<br/>3. Add the codes below:

        int badgeCount = 1;
        try {
            ShortcutBadger.setBadge(getApplicationContext(), badgeCount);
        } catch (ShortcutBadgeException e) {
            //handle the Exception
        }
<br/>4. If you want to remove the badge, just set the badgeCount as 0.
<br/>
<br/>
<br/>
<br/>


DEVELOP BY
===================================
[Leo Lin](https://github.com/leolin310148)


ABOUT Google Play Developer Term Violation
===================================
If you receive mail from Google contains message like :<br/> 

        REASON FOR WARNING: Violation of section 4.4 of the Developer Distribution Agreement.
        
        
Please add permissions to your manifest.

        <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/>
        <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS"/>
        <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
        <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />


CHANGE LOG
===================================
1.0.10:<br/>
Add Asus launcher support.
<br/><br/>
1.0.9:<br/>
Add xiaomi launcher support.


LICENSE
===================================
<br/>
        
        Copyright 2014 Leo Lin
        
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        
            http://www.apache.org/licenses/LICENSE-2.0
        
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
<br/>       
