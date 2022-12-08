package com.example.ingenio.Models;

public class Users {
    private String name;
    private String lastName;
    private String email;
    private int password;
    private String birthday;
    private String phoneNumber;
    private String profilePictureUrl;

    public Users () {

    }

    public Users (String name, String lastName, String email, int password, String birthday, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public Users (String birthday, String email, String lastName, String name, int password, String phoneNumber, String profilePictureUrl) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
    }

    public Users (String birthday, String email, String lastName, String name, String phoneNumber, String profilePictureUrl) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getPassword () {
        return password;
    }

    public void setPassword (int password) {
        this.password = password;
    }

    public String getBirthday () {
        return birthday;
    }

    public void setBirthday (String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl () {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl (String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    @Override
    public String toString () {
        return "Users{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", birthday='" + birthday + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
