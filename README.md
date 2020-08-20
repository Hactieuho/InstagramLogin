# This library created by Hactieuho96@gmail.com
**Login instagram to get access token using 2 ways: Start new activity or show dialog**

1. Project gradle:
    ```
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    ```

2. App gradle:
    ```
    dependencies {
	        implementation 'com.github.Hactieuho:InstagramLogin:1.2'
	}
    ```

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
  
    ```
    // Pass client id and client secret through intent
    startActivityForResult(new Intent(this, WebViewActivity.class)
                .putExtra(WebViewActivity.CLIENT_ID, "16*******")
                .putExtra(WebViewActivity.CLIENT_SECRET, "52******")
                , WebViewActivity.START_ACTIVITY_RESULT_ID);
    ```

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
            // Replace with your client id and client secret
            authenticationDialog = new AuthenticationDialog(this, this, "16*******", "52******");
        }
        authenticationDialog.setCancelable(true);
        authenticationDialog.show();
    ```
  
- access token is returned at processFinish method

- access token is saved at InstagramData.instance.accessToken
