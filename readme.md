# Start.io (StartApp Ads) Cordova Plugin

An *UNOFFICIAL* free to use [Start.io](https://www.start.io/) Cordova plugin that allows you to display ads on your cordova/ionic app.

---

## Platforms support:
| OS | Integrated | SDK version | Supported ad types | 
|----|------------| ------------------ |-------------|
| **Android** | **YES** :white_check_mark: | 4.10.2 | Banner, Rewarded Video, Interstitial |
| **iOS** | **NO*** :x: | N/A | N/A |

\* **Only Android is supported for the moment, I will do my best to add iOS soon.**

<br/>

## Prerequisites

* cordova 7 or higher
* cordova-android 6 or higher

<br/>

## Installation

To add Start.io plugin use:
```bash
cordova plugin add https://github.com/itsSoufianeDev/cordova-plugin-startio-ads
```

To remove the plugin use:
```bash
cordova plugin remove cordova-plugin-startio-ads
```
<br/>

## Plugin Methods

* **init**(appid: string, options: object, testAds: boolean): void 
* **setConsent**(consent: boolean): void
* **setIABUSPrivacy**(IABString: string): void
* **loadBanner**(): void
* **showBanner**(): void
* **hideBanner**(): void
* **showInterstitial**(): void
* **loadRewardVideo**(autoShow: boolean): void
* **showRewardVideo()**: void
<br/>

## Usage

The plugin exports a JS variable named `StartIO`, Typescript (if you're using Angular) won't let you access this variable that's why you will need to declare it on each page you are willing to use the plugin on.

```js
import { Component, OnInit } from '@angular/core'
/// any other imports

declare var StartIO: any
```
<br/>

### 1. Initialisation
```js
const options = { returnAd: true, splashAd: false } // use your own preferences
const appID = 123456789 // your own app id
const testAds = true // enable test ads, must be set to false on production app

StartIO.init(appID, options, testAds)
```
<br/>

### 2. GDPR Consent / CCPA Compliance

The plugin allows you to pass user consent to the SDK, refer to StartIO docs for more details on [GDPR](https://support.start.io/hc/en-us/articles/360014774799-Integration-via-Maven#consent-from-european-users-gdpr--0-10) and [CCPA Compliane](https://support.start.io/hc/en-us/articles/360014774799-Integration-via-Maven#ccpa-compliance-0-11)
```js

StartIO.setConsent(true) // GDPR
StartIO.setIABUSPrivacy("1YNN") // CCPA
```
<br/>

### 3. Banner Ads

To show a Banner ad you need to call loadBanner() and listen to the `startio.banner.load` event then call showBanner().<br/>
**IMPORTANT: Do not call loadBanner() from within `startio.banner.load_fail`. The SDK will automatically try to reload an ad upon a failure.**

```js

document.addEventListener('startio.banner.load', () => {
  // Banner loaded succesfully, call showBanner()

  StartIO.showBanner()
});
```
To hide the banner
```js
StartIO.hideBanner()
```

#### Events triggered 

| Event name | description |
|------------|-------------|
| startio.banner.load | triggered when the banner is loaded and ready to be shown |
| startio.banner.load_fail | triggered when the SDK fails to load a Banner |
| startio.banner.clicked | triggered when a Banner is clicked |
| startio.banner.impression | triggered on Banner impression |
| startio.banner.hide | triggered when the Banner is hidden |

<br/>

### 4. Interstitial Ads

```js
StartIO.showInterstitial()
```

#### Events triggered 

| Event name | description |
|------------|-------------|
| startio.interstitial.load_fail | triggered when the SDK fails to load an Interstitial |
| startio.interstitial.displayed | triggered when the Interstitial is displayed |
| startio.interstitial.not_displayed | triggered when the Interstitial fails to be displayed |
| startio.interstitial.clicked | triggered when an Interstitial is clicked |
| startio.interstitial.closed | triggered when Interstitial is closed |

<br/>

### 5. Rewarded Videos Ads

```js
/// Listen for rewarded video load
document.addEventListener('startio.reward_video.load', () => {
  // Rewarded video loaded succesfully, you can now call showRewardVideo()
  StartIO.showRewardVideo()
});

/// Listen for rewarded video reward
document.addEventListener('startio.reward_video.reward', () => {
  // The user watched the video, GIVE HIM A REWARD!
  // load a new video if needed
  StartIO.loadRewardVideo(autoshow)
});

/// Load the rewarded video
const autoshow = false // autoshow rewarded video when loaded
StartIO.loadRewardVideo(autoshow)
```

#### Events triggered 

| Event name | description |
|------------|-------------|
| startio.reward_video.reward | triggered when the user watches the video and deserves a reward |
| startio.reward_video.load | triggered when the rewarded video is loaded |
| startio.reward_video.load_fail | triggered when the rewarded video loading has failed |
| startio.reward_video.clicked | triggered when the rewarded video is clicked |
| startio.reward_video.closed | triggered when the rewarded video is closed (doesn't mean the user fully watched the video) |
| startio.reward_video.displayed | triggered when the rewarded video is displayed |
| startio.reward_video.not_displayed | triggered when the rewarded video fails to be displayed |

<br/>

## Credits

This project was cloned from @lreiner github, but since it was not updated and maintained I decided to do so, You can visit the [original repository](https://github.com/lreiner/cordova-plugin-startapp-ads) and send donations to @lreiner.
<br/>

## Donations
I hope this plugin has saved you a lot of time, keeping the SDKs updated and adding more functionalities takes time and effort, you can help me keep this project by donating bellow.
<br/>

[![Support via PayPal](https://cdn.rawgit.com/twolfson/paypal-github-button/1.0.0/dist/button.svg)](https://paypal.me/mganisoufiane) 
