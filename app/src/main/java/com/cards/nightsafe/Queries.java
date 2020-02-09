package com.cards.nightsafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Queries {

  public static boolean AreWeFriendsQuery(int username, int friend) {
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = String.format("SELECT * from friendships JOIN users on users.username = friendships.user2 where user1=\""+username+"\" AND user2=\""+friend+"\";");
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        String status = rs.getString("status");
        if (status.equals("Friends")) {
          return true;
        }
      }
      query = String.format("SELECT * from friendships JOIN users on users.username = friendships.user1 where user2=\""+username+"\" AND user2=\""+friend+"\";");
      rs = st.executeQuery(query);
      while (rs.next()) {
        String status = rs.getString("status");
        if (status.equals("Friends")) {
          return true;
        }
      }
      st.close();
    } catch (Exception e) {
      System.err.println("AreWeFriendsQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    return false;
  }

  public static ArrayList<String> FriendNamesQuery(int username) {
    ArrayList<String> friends = new ArrayList<>();
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = String.format("SELECT * from friendships JOIN users on users.username = friendships.user2 where user1=\""+username+"\";");
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String status = rs.getString("status");
        String name = firstName.concat(" ").concat(lastName);
        if (status.equals("Friends")) {
          friends.add(name);
        }
      }
      query = String.format("SELECT * from friendships JOIN users on users.username = friendships.user1 where user2=\""+username+"\";");
      rs = st.executeQuery(query);
      while (rs.next()) {
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String status = rs.getString("status");
        String name = firstName.concat(" ").concat(lastName);
        if (status.equals("Friends")) {
          friends.add(name);
        }
      }
      friends.stream().distinct().collect(Collectors.toList());
      st.close();
    } catch (Exception e) {
      System.err.println("FriendNamesQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return friends;
    }
  }

  public static void EventAddQuery(String name, String location, String date, String home, String timeHome, ArrayList<Integer> groupMembers) {
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String createEvent = "INSERT INTO event (name, location, date_time) VALUES (\""+name+"\", \""+location+"\", \""+date+"\");";
      st.executeUpdate(createEvent);
      String geteIDquery = "SELECT * FROM event ORDER BY event_id DESC LIMIT 1;";
      ResultSet ers = st.executeQuery(geteIDquery);
      int eID = 0;
      while (ers.next()) {
        eID = ers.getInt("event_id");
      }
      String createGroup = "INSERT INTO groups (event_id, dest, time_home) VALUES (\""+eID+"\", \""+home+"\", \""+timeHome+"\");";
      st.executeUpdate(createGroup);
      String getgIDquery = "SELECT * FROM groups ORDER BY group_id DESC LIMIT 1;";
      ResultSet grs = st.executeQuery(getgIDquery);
      int gID = 0;
      while (grs.next()) {
        gID = grs.getInt("group_id");
      }

      //BAT AND LAST SEEN ARE A PROBLEM
      //initialise with a default and then update??
      String addUser;
      for (int groupMember : groupMembers) {
        addUser = "INSERT INTO group_user (group_id, username, bat, last_seen) VALUES (\""+gID+"\", \""+groupMember+"\", \"100\", \"0\");";
        st.executeUpdate(addUser);
      }
    } catch (Exception e) {
      System.err.println("EventAddQuery error adding user data: ");
      System.err.println(e.getMessage());
    }
  }

  public static EventInfo EventInfoQuery(int eventID) {
    EventInfo eventInfo = null;
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT * from event where event_id = " + eventID;
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        String name = rs.getString("name");
        String location = rs.getString("location");
        String date = rs.getString("date_time");
        eventInfo = new EventInfo(name, location, date);
      }
      st.close();
    } catch (Exception e) {
      System.err.println("EventInfoQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return (eventInfo);
    }
  }

  public static ArrayList<GroupCall> GroupCallQuery(int username, int groupID) {
    ArrayList<GroupCall> userCallList = new ArrayList<>();
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT * from group_user JOIN users on group_user.username = users.username where group_user.group_id = " + groupID + ";";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        if (rs.getInt("username")!=username) {
          String firstName = rs.getString("firstName");
          String lastName = rs.getString("lastName");
          String phoneNumber = rs.getString("phoneNumber");
          String name = firstName.concat(" ").concat(lastName);
          userCallList.add(new GroupCall(name, phoneNumber));
        }
      }
      st.close();
    } catch (Exception e) {
      System.err.println("GroupCallQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return userCallList;
    }
  }

  public static GroupDestination GroupDestinationQuery(int groupID) {
    ArrayList<Integer> usernameList = new ArrayList<>();
    String destination = "";
    String eventLocation = "";
    try {
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
      Statement st = conn.createStatement();
      String query = "SELECT username, dest, location from group_user JOIN groups on group_user.group_id = groups.group_id JOIN event on groups.event_id = event.event_id where group_user.group_id = " + groupID + ";";
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        // check if column names correct!
        usernameList.add(rs.getInt("username"));
        destination = rs.getString("dest");
        eventLocation = rs.getString("location");
      }

      st.close();
    } catch (Exception e) {
      System.err.println("GroupDestinationQuery error retrieving user data: ");
      System.err.println(e.getMessage());
    }
    finally {
      return (new GroupDestination(usernameList, destination, eventLocation));
    }
  }

  public static ArrayList<GroupFind> GroupFindQuery(int username, int groupID) {
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
          float longitude = rs.getFloat("lng");
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
