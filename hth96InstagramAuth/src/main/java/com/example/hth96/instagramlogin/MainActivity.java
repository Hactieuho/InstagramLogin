package com.example.hth96.instagramlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements RequestInstagramTokenResponse {

    private String authorizationCode = null;
    private Button button = null;
    private View info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_login);
        info = findViewById(R.id.info);
    }

    public void logout() {
        button.setText("INSTAGRAM LOGIN");
        authorizationCode = null;
        info.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        if (authorizationCode != null) {
            logout();
        } else {
            if (authenticationDialog == null) {
                authenticationDialog = new AuthenticationDialog(this, this, "16*******", "52******");
            }
            authenticationDialog.setCancelable(true);
            authenticationDialog.show();
        }
    }
    private AuthenticationDialog authenticationDialog;

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
            // Tra ve thanh cong
            Toast.makeText(this, "Access token: $message", Toast.LENGTH_LONG).show();
        } else if (result.equals(RequestInstagramTokenResponse.ERROR)) {
            // Tra ve loi
        }
        authenticationDialog.dismiss();
        authenticationDialog = null;
    }
}
