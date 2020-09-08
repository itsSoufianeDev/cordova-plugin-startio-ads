/********* StartAppAds.m Cordova Plugin Implementation *******/

#import <StartAppAds.h>

@implementation StartAppAds {
	// Decalre private stuff
}

/*- (void)coolMethod:(CDVInvokedUrlCommand*)command {

    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}*/

- (void)initStartApp:(CDVInvokedUrlCommand*)command {

	CDVPluginResult* pluginResult = nil;

    NSString* appID = [command.arguments objectAtIndex:0];
    bool disableReturnAd = [[command.arguments objectAtIndex:1] boolValue];
    bool disableSplashAd = [[command.arguments objectAtIndex:2] boolValue];

    // initialize the SDK with your appID and devID
    STAStartAppSDK* sdk = [STAStartAppSDK sharedInstance];
    sdk.testAdsEnabled = YES;
    
    if (sdk.appID != nil) {
        // The sdk has already been initialized
        return;
    }
    sdk.appID = appID;

    NSLog(@"SDK initialized with appID %@", appID)

    if (disableReturnAd){
    	sdk.returnAdEnabled = NO;
    }
}

- (void)setConsent:(CDVInvokedUrlCommand*)command {
	bool userConsent = [[command.arguments objectAtIndex:0] boolValue];

	if(userConsent){
		[[STAStartAppSDK sharedInstance] setUserConsent:YES forConsentType:@"pas" withTimestamp:[[NSDate date] timeIntervalSince1970]];
	}else{
		[[STAStartAppSDK sharedInstance] setUserConsent:NO forConsentType:@"pas" withTimestamp:[[NSDate date] timeIntervalSince1970]];
	}
}

- (void)showBanner:(CDVInvokedUrlCommand*)command {
	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
- (void)hideBanner:(CDVInvokedUrlCommand*)command {
	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)showInterstitial:(CDVInvokedUrlCommand*)command {
	//[super viewDidLoad];
    startAppInterstitialAd = [[STAStartAppAd alloc] init];
    [startAppInterstitialAd loadAdWithDelegate:self];

	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)loadRewardVideo:(CDVInvokedUrlCommand*)command {
	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)showRewardVideo:(CDVInvokedUrlCommand*)command {
	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
