package com.example.hth96.instagramlogin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AuthenticationDialog extends Dialog {
    private final String request_url;
    private RequestInstagramTokenResponse listener;

    public AuthenticationDialog(@NonNull Context context, RequestInstagramTokenResponse listener) {
        super(context);
        this.listener = listener;
        String redirect_url = context.getResources().getString(R.string.redirect_url);
        this.request_url = context.getResources().getString(R.string.base_url) +
                "oauth/authorize?client_id=" + context.getResources().getString(R.string.client_id) +
                "&redirect_uri=" + redirect_url +
                "&response_type=code&scope=user_profile,user_media";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.95f);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.9f);
        Objects.requireNonNull(getWindow()).setLayout(width, height);
        initializeWebView();
    }

    private void initializeWebView() {
        WebView webView = findViewById(R.id.webView);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.requestFocusFromTouch();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyWebViewClient(getContext(), listener));
        webView.loadUrl(request_url);
    }
}
