package com.example.hth96.instagramlogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWebViewClient extends WebViewClient {

    public final Context context;
    public final RequestInstagramTokenResponse listener;
    private String clientId, clientSecret;

    public MyWebViewClient(Context context, RequestInstagramTokenResponse listener, String clientId, String clientSecret) {
        this.context = context;
        this.listener = listener;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        // Decode url
        url = URLDecoder.decode(url);
        if (url.contains("code=")) {
            Pattern pattern = Pattern.compile("code=.*?#_");
            Matcher matcher = pattern.matcher(url);
            String authorizationCode = "";
            if (matcher.find()) {
                authorizationCode = url.substring(matcher.start() + 5, matcher.end() - 2);
            }
            if (!Objects.equals(authorizationCode, "")) {
                Log.e("authorization code: ", authorizationCode);
                // Lay access token
                // Luu lai authorization code
                InstagramData.getInstance().accessToken = authorizationCode;
                // Lay access token bang authorization code
                new RequestInstagramToken(context, listener, clientId, clientSecret).execute();
            }
        } else if (url.contains("?error")) {
            Log.e(RequestInstagramTokenResponse.ACCESS_TOKEN, "getting error fetching access token");
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.e("Error", description);
    }
}
