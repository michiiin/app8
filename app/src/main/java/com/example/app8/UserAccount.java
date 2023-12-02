package com.example.app8;

public class UserAccount {
    private String emailId;
    private String password;
    private String age;
    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String nickname; // New field for storing nickname

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }


    public String getIdToken() {
        return idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = String.valueOf(age);
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public UserAccount() {
    }
}
