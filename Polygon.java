package com.example.intromapsvideo;

public class Polygon
{
    private String polygonId;
    private int radius;
    private Double longitude;
    private Double latitude;
    private int strokeWidth = 2;

    // Color definition
    private int alfa;
    private int red;
    private int green;
    private int blue;

    public Polygon(String polygonId,
                   int radius, Double latitude, Double longitude,
                   int alfa, int red, int green, int blue)
    {
        this.polygonId = polygonId;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
        this.alfa = 25; // transparency is auto 25
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String getPolygonId() {
        return polygonId;
    }

    public void setPolygonId(String polygonId) {
        this.polygonId = polygonId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getStrokeWidth()
    {
        return this.strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }

    public int getAlfa() {
        return alfa;
    }

    public void setAlfa(int alfa) {
        this.alfa = alfa;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
