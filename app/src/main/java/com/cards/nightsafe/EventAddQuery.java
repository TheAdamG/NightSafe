package com.cards.nightsafe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EventAddQuery {

    public static void add(String name, String location, String date, String home, String timeHome, ArrayList<Integer> groupMembers) {
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

}
