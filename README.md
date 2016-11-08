ShortcutBadger: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.leolin/ShortcutBadger/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.leolin/ShortcutBadger)
===================================

The ShortcutBadger makes your Android App show the count of unread messages as a badge on your App shortcut!

# Supported launchers:<br/>

<table>
    <tr>
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
    </tr>
    <tr>
        <td width="130">
            <h3>Xiaomi</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_xiaomi.png"/>
        </td>
        <td width="130">
            <h3>ASUS</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_asus.png"/>
        </td>
        <td width="130">
            <h3>ADW</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_adw.png"/>
        </td>
        <td width="130">
            <h3>APEX</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_apex.png"/>
        </td>
    <tr>
        <td width="130">
            <h3>NOVA</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_nova.png"/>
        </td>
        <td width="130">
            <h3>Huawei</h3>
            <br>
            <img src="https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_huawei.png"/>
            <br>
            (1.1.7+)
        </td>
        <td width="130">
            <h3>ZUK</h3>
            <br>
            <img src="https://raw.github.com/linwoain/ShortcutBadger/master/screenshots/ss_zuk.png"/>
            <br>
            (1.1.10+)
        </td>
        <td width="130">
            <h3>OPPO</h3>
            <br>
            <img src="screenshots/ss_oppo.png?raw=true"/>
            <br>
            (1.1.10+)
        </td>
    </tr>
    
</table> 

* Nova launcher with TeslaUnread,Apex launcher,Adw Launcher provided by [notz](https://github.com/notz)
* Solid launcher provided by [MajeurAndroid](https://github.com/MajeurAndroid)

## IsBadgeWorking? 

A tool for to checking your device / launcher / android version and test/report if Shortcutbadger is working or not

You can download it from 
* google play [https://play.google.com/store/apps/details?id=me.leolin.isbadgeworking](https://play.google.com/store/apps/details?id=me.leolin.isbadgeworking)
* the github repo [https://github.com/leolin310148/IsBadgeWorking.Android/releases](https://github.com/leolin310148/IsBadgeWorking.Android/releases)


USAGE
===================================
<br/>1. Add mavenCentral to your build script.

        repositories {
            mavenCentral()
        }
    
<br/>2. Add dependencies for ShortcutBadger, it's available from maven now.
        
        dependencies {
            compile "me.leolin:ShortcutBadger:1.1.10@aar"
        }

<br/>3. Add the codes below:

        int badgeCount = 1;
        ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+
        ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
        
<br/>4. If you want to remove the badge
        
        ShortcutBadger.removeCount(context); //for 1.1.4+
        ShortcutBadger.with(getApplicationContext()).remove();  //for 1.1.3
or
        
        ShortcutBadger.applyCount(context, 0); //for 1.1.4+
        ShortcutBadger.with(getApplicationContext()).count(0); //for 1.1.3
<br/>
<br/>
<br/>
<br/>


DEVELOP BY
===================================
[Leo Lin](https://github.com/leolin310148) - leolin310148@gmail.com


ABOUT Google Play Developer Term Violation
===================================
If you receive mail from Google contains message like :<br/> 

        REASON FOR WARNING: Violation of section 4.4 of the Developer Distribution Agreement.
        
Please use version 1.1.0+



CHANGE LOG
===================================
1.1.10:<br/>
Add ZUK Launcher Support

1.1.9:<br/>
Add SamsungBadger back for more Samsung devices support.

1.1.8:<br/>
Remove SolidBadger, now solid launcher will use default badger.


1.1.7:<br/>
add huawei launcher support. <br/>
<br/>

1.1.6:<br/>
add support for new Sony Launchers. <br/>
<br/>

1.1.5:<br/>
`applyCount` will return if the Broadcast has been sent successfully. <br/>
<br/>

1.1.4:<br/>
Changed `ShortcutBadger.setBadge(context, badgeCount)` to `ShortcutBadger.applyCount(context, badgeCount);`<br/>
<br/>

1.1.3:<br/>
Deprecate SamsungBadger and LGBadger, those devices can use DefaultBadger<br/>
<br/>

1.1.2:<br/>
Add support for 'com.miui.mihome2'<br/>
<br/>

1.1.1:<br/>
Add DefaultBadger because some launchers use android.intent.action.BADGE_COUNT_UPDATE to update count.
<br/>
Since the ShortcutBadgerException is helpless. So change api to set badge and never have to handle the exception again. 
<br/>
<br/>

1.1.0:<br/>
Remove Android Launcher support due to  Google Play Developer Term Violation since 4.4 
<br/>
<br/>

1.0.10:<br/>
Add Asus launcher support.
<br/>
<br/>

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
