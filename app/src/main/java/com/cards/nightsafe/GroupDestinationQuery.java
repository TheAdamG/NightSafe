package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupDestinationQuery {

  public static GroupDestination query(int groupID) {
    ArrayList<String> usernameList = new ArrayList<>();
    String destination = "";
    try {
      // CHANGE THIS TO OUR URL and database??
      String myUrl = "jdbc:mysql://localhost/test";
      Connection conn = DriverManager.getConnection(myUrl, "imperial", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT username, destination from users JOIN groups on users.group = groups.group where group = " + groupID;
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        usernameList.add(rs.getString("username"));
        destination = rs.getString("destination");
      }

      st.close();
    } catch (Exception e) {
      System.err.println("GroupCallQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return (new GroupDestination(usernameList, destination));
    }
  }

}
