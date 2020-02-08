package com.cards.nightsafe;

public class GroupFind {

  private String name;
  //??? check types
  private double latitude;
  private double longitude;
  private int batteryPercentage;
  private String lastSeenTime;

  public GroupFind(String name, double latitude, double longitude, int batteryPercentage, String lastSeenTime) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.batteryPercentage = batteryPercentage;
    this.lastSeenTime = lastSeenTime;
  }

}
