package com.example.ingenio.Models;

public class Users {
    private String name;
    private String lastName;
    private String email;
    private int password;
    private String birthday;
    private String phoneNumber;
    private String profilePirctureUrl;

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

    public Users (String name, String lastName, String email, int password, String birthday, String phoneNumber, String profilePirctureUrl) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.profilePirctureUrl = profilePirctureUrl;
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

    public String getProfilePirctureUrl () {
        return profilePirctureUrl;
    }

    public void setProfilePirctureUrl (String profilePirctureUrl) {
        this.profilePirctureUrl = profilePirctureUrl;
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
                ", profilePirctureUrl='" + profilePirctureUrl + '\'' +
                '}';
    }
}
