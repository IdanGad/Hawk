package com.example.intromapsvideo;

public class Admin extends Person
{
    private String adminId;
    private String AdminPassword;
    private boolean isFirst; // This variable will determine if the admin logged in for the first time.
    private boolean isLogged;

    // Constructor
    public Admin( String id, String privateNameToSet, String lastNameToSet, String phoneNumberToSet, String emailToSet, String adminId, String adminPassword,boolean isFirst)
    {
        super(id, privateNameToSet, lastNameToSet, phoneNumberToSet, emailToSet);
        this.adminId = adminId;
        this.AdminPassword = adminPassword;
        this.isFirst = isFirst;
    }

    // Getters and Setters
    public String getAdminId() {return adminId;}
    public void setAdminId(String adminId) {this.adminId = adminId;}
    public String getAdminPassword() {return AdminPassword;}
    public void setAdminPassword(String adminPassword) {AdminPassword = adminPassword;}
    public boolean getIsFirst() {return this.isFirst;}
    public void setIsFirst(boolean isFirst){ this.isFirst = isFirst;}

    public boolean getIsLogged() {return this.isLogged;}
    public void setIsLogged(boolean isLogged){ this.isLogged = isLogged;}

}
