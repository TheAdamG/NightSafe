package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupDestinationQuery {

  public static GroupDestination query(int groupID) {
    ArrayList<Integer> usernameList = new ArrayList<>();
    String destination = "";
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT username, destination from group_user JOIN groups on group_user.group_id = groups.id where group_id = " + groupID + ";";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        usernameList.add(rs.getInt("username"));
        destination = rs.getString("dest");
      }

      st.close();
    } catch (Exception e) {
      System.err.println("GroupDestinationQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return (new GroupDestination(usernameList, destination));
    }
  }

}
