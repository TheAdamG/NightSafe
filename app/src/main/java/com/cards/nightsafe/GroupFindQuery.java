package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupFindQuery {

  public static ArrayList<GroupFind> query(String username, String groupID) {
    ArrayList<GroupFind> userFindList = new ArrayList<>();
    try {
      // CHANGE THIS TO OUR URL and database??
      String myUrl = "jdbc:mysql://localhost/test";
      Connection conn = DriverManager.getConnection(myUrl, "imperial", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT * from users where group = " + groupID;
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        if (rs.getString("userName")!=username) {
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          double latitude = rs.getDouble("latitude");
          double longitude = rs.getDouble("longitude");
          int batteryPercentage = rs.getInt("batteryPercentage");
          String lastSeenTime = rs.getString("lastSeenTime");
          String name = firstName.concat(" ").concat(lastName);
          userFindList.add(new GroupFind(name, latitude, longitude, batteryPercentage, lastSeenTime));
        }
      }
      st.close();
    } catch (Exception e) {
      System.err.println("GroupFindQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return userFindList;
    }
  }

}