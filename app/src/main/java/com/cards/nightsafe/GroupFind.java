package com.cards.nightsafe;

public class GroupFind {

    private String name;
    private double latitude;
    private double longitude;
    private int batteryPercentage;
    private String lastSeenTime;

    public GroupFind(String name, float latitude, float longitude, int batteryPercentage, String lastSeenTime) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryPercentage = batteryPercentage;
        this.lastSeenTime = lastSeenTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(String lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }
}
