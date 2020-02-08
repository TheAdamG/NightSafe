package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupCallQuery {

    public static ArrayList<GroupCall> query(String username, int groupID) {
        ArrayList<GroupCall> userCallList = new ArrayList<>();
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
                    boolean emergencyContact = rs.getBoolean("emergencyContact");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String phoneNumber = rs.getString("phoneNumber");
                    String name = firstName.concat(" ").concat(lastName);
                    userCallList.add(new GroupCall(emergencyContact, name, phoneNumber));
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

}