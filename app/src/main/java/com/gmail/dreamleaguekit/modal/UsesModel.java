package com.gmail.dreamleaguekit.modal;

public class UsesModel {

    private String username;
    private String user_comment;
    private String user_photo;
    private String user_email;
    private String date;

    public UsesModel(){}
    public UsesModel(String username, String user_comment, String user_photo, String user_email, String date)
    {
        this.username = username;
        this.user_comment = user_comment;
        this.user_photo = user_photo;
        this.user_email = user_email;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
