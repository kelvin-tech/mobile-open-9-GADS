package com.hst.gads.electronicvotingapp.models;

public class user {
    String  email, phone;

    public user(String email, String phone) {

        this.email = email;
        this.phone = phone;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
