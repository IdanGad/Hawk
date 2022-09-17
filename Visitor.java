package com.example.intromapsvideo;

import java.util.HashSet;
import java.util.Set;

public class Visitor extends Person
{
    // This class represent a visitor that is suppose to enter an organization

    //attributes
    private String visitorId;
    private boolean terms = false; // in the future the visitor must accept my term of use
    private boolean loggedIn=false;
    private Double latitude = 0.0; // This attribute will be initialized later from the visitor location
    private Double longitude = 0.0; // This attribute will be initialized later from the visitor location
    private Set <String> polygonsAllowed; // This attribute represent the polygons that the visitor is allowed to be in

    public Visitor(String id,String firstName, String lastName, String phoneNumber, String email, String visitorId, Double latitude, Double longitude)
    {
        super(id, firstName, lastName, phoneNumber,  email);
        this.visitorId = visitorId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.polygonsAllowed = new HashSet<>();//***
    }

    // Getters
    public String getVisitorId() {
        return this.visitorId;
    }
    public boolean getTerms(){
        return this.terms;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public boolean getLoggedIn() {
        return this.loggedIn;
    }
    public Set <String> getPolygonsAllowed(){ return this.polygonsAllowed; }

    // Setters
    public void setVisitorId(String visitorIdToSet) {
        this.visitorId = visitorIdToSet;
    }
    public void setTerms(boolean termsToSet) {
        this.terms = termsToSet;
    }
    public void setLatitude(Double latit) {
        this.latitude = latit;
    }
    public void setLongitude(Double longi) {
        this.longitude = longi;
    }
    public void setLoggedIn(boolean logged) {
        this.loggedIn=logged;
    }

    public void addPolygonToVisitor(String polygonId)
    {
        this.polygonsAllowed.add(polygonId);
    }
}
