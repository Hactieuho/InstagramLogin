package com.example.hth96.instagramlogin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RequestInstagramToken extends AsyncTask<Void, String, String> {

    public final RequestInstagramTokenResponse listener;
    public Context context;

    public RequestInstagramToken(Context context, RequestInstagramTokenResponse listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (context == null) {
            Log.e("RequestInstagramToken", "RequestInstagramToken context is null");
            return null;
        }
        String requestUrl = context.getResources().getString(R.string.base_url) + "oauth/access_token";
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost();

            URI uri = new URI(requestUrl);
            httpPost.setURI(uri);

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("client_id", context.getResources().getString(R.string.client_id)));
            nameValuePairs.add(new BasicNameValuePair("client_secret", context.getResources().getString(R.string.client_secret)));
            nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
            nameValuePairs.add(new BasicNameValuePair("redirect_uri", context.getResources().getString(R.string.redirect_url)));
            nameValuePairs.add(new BasicNameValuePair("code", InstagramData.getInstance().accessToken));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("Access token json", stringBuffer.toString());
        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if (response != null) {
            try {
                JSONObject jsonData = new JSONObject(response);
                Log.e("response", jsonData.toString());
                // Luu lai access token va user id
                InstagramData.getInstance().accessToken = jsonData.getString(RequestInstagramTokenResponse.ACCESS_TOKEN);
                InstagramData.getInstance().userId = jsonData.getString(RequestInstagramTokenResponse.USER_ID);
                // Goi ve listener
                listener.processFinish(RequestInstagramTokenResponse.SUCCESS, InstagramData.getInstance().accessToken);
            } catch (JSONException e) {
                e.printStackTrace();
                // Goi ve listener
                listener.processFinish(RequestInstagramTokenResponse.ERROR, e.getLocalizedMessage());
            }
        } else {
            Log.e("RequestInstagramToken", "response = null");
            // Goi ve listener
            listener.processFinish(RequestInstagramTokenResponse.ERROR, "RequestInstagramToken response = null");
        }
    }
}