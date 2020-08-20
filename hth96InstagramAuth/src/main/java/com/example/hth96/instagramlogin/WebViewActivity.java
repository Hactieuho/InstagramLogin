package com.example.hth96.instagramlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hth96.instagramlogin.databinding.ActivityWebviewBinding;

public class WebViewActivity extends AppCompatActivity implements RequestInstagramTokenResponse {

    public static final int START_ACTIVITY_RESULT_ID = 1234;
    private ActivityWebviewBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        initWebview();
    }

    private void initWebview() {
        String redirect_url = getResources().getString(R.string.redirect_url);
        String request_url = getResources().getString(R.string.base_url) +
                "oauth/authorize?client_id=" + getResources().getString(R.string.client_id) +
                "&redirect_uri=" + redirect_url +
                "&response_type=code&scope=user_profile,user_media";

        viewBinding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        viewBinding.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        viewBinding.webView.getSettings().setLoadsImagesAutomatically(true);
        viewBinding.webView.getSettings().setJavaScriptEnabled(true);
        viewBinding.webView.getSettings().setDomStorageEnabled(true);
        viewBinding.webView.requestFocusFromTouch();
        viewBinding.webView.setWebChromeClient(new WebChromeClient());
        viewBinding.webView.setWebViewClient(new MyWebViewClient(this, this));
        viewBinding.webView.loadUrl(request_url);
    }

    @Override
    public void processFinish(String result, String message) {
        if (result.equals(RequestInstagramTokenResponse.SUCCESS)) {
            // Tra ve thanh cong
            Intent returnIntent = new Intent();
            returnIntent.putExtra(RequestInstagramTokenResponse.ACCESS_TOKEN, message);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else if (result.equals(RequestInstagramTokenResponse.ERROR)) {
            // Tra ve loi
            Intent returnIntent = new Intent();
            returnIntent.putExtra("error", result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}