package com.example.hth96.instagramlogin;

public class InstagramData {

    private static InstagramData instance = null;
    public String userId = null;
    public String authorizationCode = null;
    public String accessToken = null;

    public static InstagramData getInstance() {
        if (instance == null) instance = new InstagramData();
        return instance;
    }
}
