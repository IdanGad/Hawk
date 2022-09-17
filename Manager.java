package com.example.intromapsvideo;

public class Manager extends Person
{
    // This class represent a manager in my organization

    // Gets att from Person class
    // Attributes
    private String managerId;
    private String managerPassword;
    private boolean isFirst; // This variable will determine if the manager logged in for the first time.
    private boolean looged = false;


    public Manager(String id,
                   String firstName,
                   String lastName,
                   String phoneNumber,
                   String email,
                   String managerId,
                   String managerPassword,
                   boolean isFirst )
    {
        super( id, firstName, lastName, phoneNumber,  email);
        this.managerId = managerId;
        this.managerPassword = managerPassword;
        this.isFirst = isFirst;
    }

    // Getters
    public String getManagerId()
    {
        return this.managerId;
    }
    public String getManagerPassword()
    {
        return this.managerPassword;
    }
    public boolean getIsFirst(){return this.isFirst;}
    public boolean getIsLogged(){return this.looged;}

    // Setters
    public void setIsFirst(boolean notFirstAnyMore){this.isFirst = notFirstAnyMore;}
    public void setIsLogged(boolean logged){this.looged=logged;}
    public void setManagerId(String managerIdToSet)
    {
        this.managerId = managerIdToSet;
    }
    public void setManagerPassword(String managerPasswordToSet) {this.managerPassword = managerPasswordToSet;}

}
