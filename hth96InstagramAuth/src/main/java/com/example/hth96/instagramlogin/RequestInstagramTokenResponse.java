package com.example.hth96.instagramlogin;

public interface RequestInstagramTokenResponse {
    String ACCESS_TOKEN = "access_token";
    String USER_ID = "user_id";
    String SUCCESS = "success";
    String ERROR = "error";

    /**
     * @param result success | error
     */
    void processFinish(String result, String message);
}