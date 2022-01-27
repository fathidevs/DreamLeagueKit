package com.gmail.dreamleaguekit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

import androidx.annotation.NonNull;

public class AdMobClass extends AdRequest {

    private static final String TEST_AD_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";
    private static final String MY_AD_INTERSTITIAL = "ca-app-pub-6999642325846534/7508103517";
    private static final String REWARDED_VIDEO_TEST_AD = "ca-app-pub-3940256099942544/5224354917";
    private static final String REWARDED_VIDEO_MY_AD = "ca-app-pub-6999642325846534/7387142024";

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private RewardedAd rewardedAd;

    private FullScreenContentCallback fullScreenContentCallback;

    private Context context;

    public AdMobClass(Builder builder, Context context) {
        super(builder);
        this.context = context;
        adRequest = new AdRequest.Builder().build();
    }

    public void loadRewardedAd() {
        RewardedAd.load(Objects.requireNonNull(context), REWARDED_VIDEO_MY_AD,
                new Builder().build(),
                new RewardedAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad)
                    {
                        //super.onAdLoaded(rewardedAd);
                        Log.d("rewardedAd", ad.getAdUnitId());
                        rewardedAd = ad;
                        rewardedAd.setFullScreenContentCallback(fullScreenContentCallback);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        //super.onAdFailedToLoad(loadAdError);
                        /*Toast.makeText(context,
                                loadAdError.getMessage()
                                , Toast.LENGTH_SHORT).show();*/
                        Log.e("rewardedAd", loadAdError.getMessage());
                    }
                });
    }

    public void RewardedAdFullBackScreen() {
        fullScreenContentCallback =
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Code to be invoked when the ad showed full screen content.
                        /*Toast.makeText(context,
                                "ad showed full screen"
                                , Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        /*Toast.makeText(context,
                                "ad dismissed"
                                , Toast.LENGTH_SHORT).show();*/

                        rewardedAd = null;
                        // Code to be invoked when the ad dismissed full screen content.
                    }
                };
    }

    public void showRewardedAd() {
        if (rewardedAd != null){
            rewardedAd.show((Activity)context, rewardItem ->{
                        //Toast.makeText(context, "Kit saved", Toast.LENGTH_LONG).show();
                    });
        }else {
            //Toast.makeText(context, "Kit saved", Toast.LENGTH_LONG).show();
        }

    }

    public void initInterstitialAd() {
        MobileAds.initialize(context, initializationStatus -> {
            //Toast.makeText(KitCreatorActivity.this, "init: "+initializationStatus, Toast.LENGTH_SHORT).show();
        });
    }

    public void loadInterstitialAd() {
        InterstitialAd.load(Objects.requireNonNull(context),
                MY_AD_INTERSTITIAL, adRequest, new InterstitialAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        super.onAdLoaded(interstitialAd);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        //Toast.makeText(getContext(), ""+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("onAdFailedToLoad", loadAdError.getMessage());
                    }
                });
    }
    public void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Objects.requireNonNull((Activity)context));
        }
    }
}