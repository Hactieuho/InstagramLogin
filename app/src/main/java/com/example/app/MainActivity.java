package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hth96.instagramlogin.AuthenticationDialog;
import com.example.hth96.instagramlogin.RequestInstagramTokenResponse;
import com.example.hth96.instagramlogin.WebViewActivity;

public class MainActivity extends AppCompatActivity implements RequestInstagramTokenResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialog(View view) {
        AuthenticationDialog authenticationDialog = new AuthenticationDialog(this, this, "16*******", "52******");
        authenticationDialog.setCancelable(true);
        authenticationDialog.show();
    }

    public void webview(View view) {
        startActivityForResult(new Intent(this, WebViewActivity.class)
                .putExtra(WebViewActivity.CLIENT_ID, "16*******")
                .putExtra(WebViewActivity.CLIENT_SECRET, "52******")
                , WebViewActivity.START_ACTIVITY_RESULT_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WebViewActivity.START_ACTIVITY_RESULT_ID) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String accessToken = data.getStringExtra(RequestInstagramTokenResponse.ACCESS_TOKEN);
                    Toast.makeText(this, "Access token: " + accessToken, Toast.LENGTH_LONG).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    @Override
    public void processFinish(String result, String message) {
        if (result.equals(RequestInstagramTokenResponse.SUCCESS)) {
            Toast.makeText(this, "Access token: " + message, Toast.LENGTH_LONG).show();
        } else if (result.equals(RequestInstagramTokenResponse.ERROR)) {
        }
    }
}
