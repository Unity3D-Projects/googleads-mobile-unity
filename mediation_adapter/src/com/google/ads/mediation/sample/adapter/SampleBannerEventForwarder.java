package com.google.ads.mediation.sample.adapter;

import com.google.ads.mediation.sample.sdk.SampleAdListener;
import com.google.ads.mediation.sample.sdk.SampleErrorCode;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationBannerListener;

/**
 * A {@link SampleAdListener} that forwards events to AdMob Mediation's
 * {@link MediationBannerListener}.
 */
public class SampleBannerEventForwarder extends SampleAdListener {
  private MediationBannerListener mediationListener;
  private SampleAdapter adapter;

  /**
   * Creates a new {@code SampleBannerEventForwarder}.
   * @param listener An AdMob Mediation {@link MediationBannerListener} that should receive
   *     forwarded events.
   * @param adapter A {@link SampleAdapter} mediation adapter.
   */
  public SampleBannerEventForwarder(MediationBannerListener listener, SampleAdapter adapter) {
    this.mediationListener = listener;
    this.adapter = adapter;
  }

  @Override
  public void onAdFetchSucceeded() {
    mediationListener.onAdLoaded(adapter);
  }

  @Override
  public void onAdFetchFailed(SampleErrorCode errorCode) {
    switch(errorCode) {
      case UNKNOWN:
        mediationListener.onAdFailedToLoad(adapter, AdRequest.ERROR_CODE_INTERNAL_ERROR);
        break;
      case BAD_REQUEST:
        mediationListener.onAdFailedToLoad(adapter, AdRequest.ERROR_CODE_INVALID_REQUEST);
        break;
      case NETWORK_ERROR:
        mediationListener.onAdFailedToLoad(adapter, AdRequest.ERROR_CODE_NETWORK_ERROR);
        break;
      case NO_INVENTORY:
        mediationListener.onAdFailedToLoad(adapter, AdRequest.ERROR_CODE_NO_FILL);
        break;
    }
  }

  @Override
  public void onAdFullScreen() {
    mediationListener.onAdClicked(adapter);
    mediationListener.onAdOpened(adapter);
    // Only call onAdLeftApplication if your ad network actually exits the developer's app.
    mediationListener.onAdLeftApplication(adapter);
  }

  @Override
  public void onAdClosed() {
    mediationListener.onAdClosed(adapter);
  }
}
