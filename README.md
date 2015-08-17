# XWalk Hardware back button issue

----
## Overview
Under certain circumstances the hardware back button of android devices doesn't seems to work.
A [custom plugin](https://github.com/Mitko-Kerezov/Cordova-Testing-Plugin) is used here in order to reproduce this issue. The custom plugin's logic consists of a simple call of `webView.stopLoading()`, along with a redirect to a new page, whenever the starting page has finished loading. After the redirect a page containing an anchor tag is shown. Taping on the anchor tag leads to a page from which there is no return, because **the hardware back button stops working**.


## Partial view redirecting
This application makes use of Telerik's widget library [Kendo UI](https://github.com/telerik/kendo-ui-core), specifically Kendo's TabStrip. The problem may be related to the redirects to partial views, which this library performs, which would mean that this issue may be reproducible with AngularJS as well.


## Devices
This issue seems to be somewhat sporadic, however I have successfully managed to reproduce it on
 
+ HTC One X, Android version 4.4.2
+ LG G3, Android version 5.0
+ Samsung GT-I9082, Android version 4.2.2