# This library created by Hactieuho96@gmail.com
**Login instagram to get access token using 2 ways: Start new activity or show dialog**
1. Change these library string resources:
    ```
    <string name="client_id">163****</string>
    <string name="client_secret">522****</string>
    <string name="redirect_url">https://instagram.com/</string>
    <string name="base_url">https://api.instagram.com/</string>
   ```
2. App gradle:
`implementation project(path: ':hth96InstagramAuth')`
    
3. Start activity WebViewActivity from MainActivity:

- Override onActivityResult method in MainActivity:
    ```
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
    ```
  
` startActivityForResult(new Intent(this, WebViewActivity.class), WebViewActivity.START_ACTIVITY_RESULT_ID); `

- access token is saved at InstagramData.instance.accessToken

4. Show login instagram dialog:

- MainActivity implements RequestInstagramTokenResponse:
    ```
    @Override
    public void processFinish(String result, String message) {
        if (result.equals(RequestInstagramTokenResponse.SUCCESS)) {
            // Tra ve thanh cong
            Toast.makeText(getContext(), "Access token: " + message, Toast.LENGTH_LONG).show();
        } else if (result.equals(RequestInstagramTokenResponse.ERROR)) {
            // Tra ve loi
        }
        authenticationDialog.dismiss();
        authenticationDialog = null;
    }
    ```
- Init and show dialog:
    ```
    if (authenticationDialog == null) {
            authenticationDialog = new AuthenticationDialog(this, this);
        }
        authenticationDialog.setCancelable(true);
        authenticationDialog.show();
    ```
  
- access token is returned at processFinish method

- access token is saved at InstagramData.instance.accessToken
