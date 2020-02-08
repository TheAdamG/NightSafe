package com.cards.nightsafe;

import java.sql.*;
import java.util.ArrayList;

public class GroupCallQuery {

    public static ArrayList<GroupCall> query(int username, int groupID) {
        ArrayList<GroupCall> userCallList = new ArrayList<>();
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://raspberrypi:3306/nightsafe";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "imperial");
            Statement st = conn.createStatement();
            String query = "SELECT * from group_user JOIN users on group_user.username = users.username where group_id = " + groupID + ";";
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
            System.out.println(userCallList.toString());
            return userCallList;
        }
    }

}