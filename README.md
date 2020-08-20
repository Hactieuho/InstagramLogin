This library created by Hactieuho96@gmail.com
Dang nhap instagram de lay access token bang 2 cach: Start activity moi hoac hien thi dialog:
1. Start activity WebViewActivity from MainActivity:
- Override onActivityResult method in MainActivity:
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
- startActivityForResult(new Intent(this, WebViewActivity.class), WebViewActivity.START_ACTIVITY_RESULT_ID);
- access token duoc luu tai InstagramData.instance.accessToken
2. Show login instagram dialog:
- Hien thi dialog:
        AuthenticationDialog authenticationDialog = new AuthenticationDialog(this);
            authenticationDialog.setCancelable(true);
            authenticationDialog.show();
- access token duoc luu tai InstagramData.instance.accessToken