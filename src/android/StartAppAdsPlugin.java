package com.startapp.cordova.ad;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;

public class StartAppAdsPlugin extends CordovaPlugin {

  private CallbackContext PUBLIC_CALLBACKS = null;
  private static final String TAG = "StartAppAdsPlugin";
  private StartAppAd startAppAd;
  private CordovaWebView cWebView;
  private ViewGroup parentView;
  private Banner startAppBanner = null;
  private StartAppAd rewardedVideo = null;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    cWebView = webView;
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    PUBLIC_CALLBACKS = callbackContext;

    if (action.equals("initStartApp")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          String appId = args.optString(0);

          boolean disableReturnAd = args.optBoolean(1);
          boolean disableSplashAd = args.optBoolean(2);
          boolean testAdsEnabled = args.optBoolean(3);

          initStartApp(appId, disableReturnAd, disableSplashAd, testAdsEnabled, PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("setConsent")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          boolean userConsent = args.optBoolean(0);
          setConsent(userConsent, PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("loadBanner")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          loadBanner(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("showBanner")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showBanner(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("hideBanner")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          hideBanner(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("showInterstitial")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showInterstitial(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if(action.equals("loadRewardVideo")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          boolean autoShow = args.optBoolean(0);
          loadRewardVideo(autoShow, PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if(action.equals("showRewardVideo")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showRewardVideo(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if(action.equals("setIABUSPrivacyString")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          String IABString = args.optString(0);
          setIABUSPrivacyString(IABString, PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    return false;
  }

  public void initStartApp(String appID, Boolean disableReturnAd, Boolean disableSplashAd, Boolean testAds, CallbackContext callbackContext) {
    Log.d(TAG, "Initializing StartApp SDK with ID: " +  appID + " ReturnAd: " + disableReturnAd + " SplashAd: " + disableSplashAd);
    startAppAd = new StartAppAd(cordova.getActivity());
    StartAppSDK.init(cordova.getActivity(), appID, disableReturnAd);

    if (disableSplashAd) {
      StartAppAd.disableSplash();
    }

    if(testAds){
      StartAppSDK.setTestAdsEnabled(true);
    }
  }

  public void setConsent(Boolean consented, CallbackContext callbackContext) {
    Log.d(TAG, "Setting Consent To: " + consented);
    StartAppSDK.setUserConsent(cordova.getActivity(), "pas", System.currentTimeMillis(), consented);
  }

  public void setIABUSPrivacyString(String IABString, CallbackContext callbackContext) {
    Log.d(TAG, "Setting IAB US Privacy To: " + IABString);
    StartAppSDK.getExtras(cordova.getActivity()).edit().putString("IABUSPrivacy_String", IABString).apply();
  }

  public void loadBanner(CallbackContext callbackContext){
    startAppBanner = new Banner(cordova.getActivity(), new BannerListener() {
      @Override
      public void onReceiveAd(View banner) {
        Log.d(TAG, "Banner has been loaded!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.load');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.load');");
      }

      @Override
      public void onFailedToReceiveAd(View banner) {
        Log.d(TAG, "Banner load failed!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.load_fail');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.load_fail');");
      }

      @Override
      public void onClick(View banner) {
        Log.d(TAG, "Banner clicked!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.clicked');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.clicked');");
      }

      @Override
      public void onImpression(View banner) {
        Log.d(TAG, "Banner impression!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.impression');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.impression');");
      }
    });
    cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.load.req');");
    cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.load.req');");

    // Forcing specefic banner dimensions to prevent other formats from loading
    startAppBanner.loadAd(320, 50); // Standard Banner
  }

  public void showBanner(CallbackContext callbackContext) {
    if(startAppBanner == null){
      cWebView.loadUrl("javascript:console.warn('The banner you tried to load is not yet loaded');");
      return;
    }

    View view = cWebView.getView();
    ViewGroup wvParentView = (ViewGroup) view.getParent();

    if (parentView == null) {
        parentView = new LinearLayout(cWebView.getContext());
    }

    if (wvParentView != null && wvParentView != parentView) {
        wvParentView.removeView(view);
        LinearLayout content = (LinearLayout) parentView;
        content.setOrientation(LinearLayout.VERTICAL);
        parentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.0F));
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));
        parentView.addView(view);
        wvParentView.addView(parentView);
        parentView.addView(startAppBanner);
    }

    //parentView.bringToFront();
    parentView.requestLayout();
    parentView.requestFocus();
  }

  public void hideBanner(CallbackContext callbackContext) {
    if (startAppBanner != null) {
        startAppBanner.hideBanner();
        startAppBanner.setVisibility(View.GONE);
        parentView = null;
        startAppBanner = null;
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.banner.hide');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.banner.hide');");
    }
  }

  public void showInterstitial(CallbackContext callbackContext) {
    startAppAd.loadAd(new AdEventListener() {
        @Override
        public void onReceiveAd(Ad ad) {
            startAppAd.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {
                  Log.d(TAG, "Interstitial has been closed!");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.interstitial.closed');");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.interstitial.closed');");
                }

                @Override
                public void adDisplayed(Ad ad) {
                  Log.d(TAG, "Interstitial displayed!");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.interstitial.displayed');");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.interstitial.displayed');");
                }

                @Override
                public void adClicked(Ad ad) {
                  Log.d(TAG, "Interstitial Ad clicked!");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.interstitial.clicked');");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.interstitial.clicked');");
                }

                @Override
                public void adNotDisplayed(Ad ad) {
                  Log.d(TAG, "Interstitial Ad not displayed!");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.interstitial.not_displayed');");
                  cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.interstitial.not_displayed');");
                }
            });
        }

        @Override
        public void onFailedToReceiveAd(Ad ad) {
          Log.d(TAG, "Failed to Receive Interstitial!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.interstitial.load_fail');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.interstitial.load_fail');");
        }
    });
  }

  public void loadRewardVideo(Boolean autoShow, CallbackContext callbackContext) {
    rewardedVideo = new StartAppAd(cordova.getActivity());

    rewardedVideo.setVideoListener(new VideoListener() {
      @Override
      public void onVideoCompleted() {
        Log.d(TAG, "Video Reward can be given now!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.reward');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.reward');");
      }
    });

    rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
      @Override
      public void onReceiveAd(Ad arg0) {
          Log.d(TAG, "Reward Video loaded!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.load');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.load');");

          if (autoShow) {
            Log.d(TAG, "Video Reward auto show!");
            rewardedVideo.showAd();
          }
      }

      @Override
      public void onFailedToReceiveAd(Ad arg0) {
        Log.d(TAG, "Failed to load Rewarded Video Ad!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.load_fail');");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.load_fail');");
      }
    });
  }

  public void showRewardVideo(CallbackContext callbackContext) {
    if (rewardedVideo != null) {
      Log.d(TAG, "Reward Video show now!");
      rewardedVideo.showAd(new AdDisplayListener() {
        @Override
        public void adHidden(Ad ad) {
          Log.d(TAG, "Rewarded Video closed!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.closed');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.closed');");
        }
        @Override
        public void adClicked(Ad ad) {
          Log.d(TAG, "Rewarded Video clicked!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.clicked');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.clicked');");
        }
        @Override
        public void adDisplayed(Ad ad) {
          Log.d(TAG, "Reward Video displayed!");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.displayed');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.displayed');");
        }
        @Override
        public void adNotDisplayed(Ad ad) {
          Log.d(TAG, "Rewarded Video not displayed");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startappads.reward_video.not_displayed');");
          cWebView.loadUrl("javascript:cordova.fireDocumentEvent('startio.reward_video.not_displayed');");
        }
      });
    } else {
      Log.d(TAG, "Video Reward need to load before call it!");
    }
  }
}
