package com.cards.nightsafe;

import java.sql.Date;

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

}
