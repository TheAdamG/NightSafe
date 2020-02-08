package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupFindQuery {

  public static ArrayList<GroupFind> query(int username, int groupID) {
    ArrayList<GroupFind> userFindList = new ArrayList<>();
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT * from group_user JOIN users on group_user.username = users.username where group_id = " + groupID + ";";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        if (rs.getInt("username")!=username) {
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          float latitude = rs.getFloat("lat");
          float longitude = rs.getFloat("long");
          int batteryPercentage = rs.getInt("bat");
          String lastSeenTime = rs.getString("last_seen");
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