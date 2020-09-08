/********* StartAppAds.h Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <StartApp/StartApp.h>

@interface StartAppAds : CDVPlugin

STAStartAppAd* startAppInterstitialAd;

//- (void)coolMethod:(CDVInvokedUrlCommand*)command;

- (void)initStartApp:(CDVInvokedUrlCommand*)command;

- (void)setConsent:(CDVInvokedUrlCommand*)command;

- (void)showBanner:(CDVInvokedUrlCommand*)command;
- (void)hideBanner:(CDVInvokedUrlCommand*)command;

- (void)showInterstitial:(CDVInvokedUrlCommand*)command;

- (void)loadRewardVideo:(CDVInvokedUrlCommand*)command;
- (void)showRewardVideo:(CDVInvokedUrlCommand*)command;

@end