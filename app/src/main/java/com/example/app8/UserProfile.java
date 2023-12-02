package com.example.app8;

import java.util.HashMap;
import java.util.Map;

// UserProfile.java
public class UserProfile {
    private String uid;
    private String nickname;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserProfile(String uid, String nickname) {
        this.uid = uid;
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
