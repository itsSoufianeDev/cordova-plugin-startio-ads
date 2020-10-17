var cordova = require('cordova');
var exec = require('cordova/exec');

var StartAppAds = {
	init: function(appid, options, testAds) {
		var DEFAULT_OPTIONS = { returnAd: true, splashAd: true };

		if (typeof options !== "object") { options = DEFAULT_OPTIONS; }
		options = Object.assign(DEFAULT_OPTIONS, options);

		exec(function(){}, function(){}, "StartAppAdsPlugin", "initStartApp", [ appid, !options.returnAd, !options.splashAd, testAds ]);
	},
	setConsent: function(consented) {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "setConsent", [ consented ]);
	},
	loadBanner: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "loadBanner", []);
	},
	showBanner: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showBanner", []);
	},
	hideBanner: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "hideBanner", []);
	},
	showInterstitial: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showInterstitial", []);
	},
	loadRewardVideo: function(autoShow) {
		autoShow = (typeof autoShow === "boolean") ? autoShow : true;
		exec(function(){}, function(){}, "StartAppAdsPlugin", "loadRewardVideo", [ autoShow ]);
	},
	showRewardVideo: function() {
		exec(function(){}, function(){}, "StartAppAdsPlugin", "showRewardVideo", []);
	}
}

module.exports = StartAppAds;
