package com.cards.nightsafe;

import java.sql.*;

public class EventInfoQuery {

    public static EventInfo query(int eventID) {
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
            System.out.println(eventInfo.toString());
            return (eventInfo);
        }
    }

}
