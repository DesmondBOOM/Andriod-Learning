package com.example.hello.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("profile-name")
    private String profileImage;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("nick")
    private String nick;
    @SerializedName("username")
    private String userName;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
