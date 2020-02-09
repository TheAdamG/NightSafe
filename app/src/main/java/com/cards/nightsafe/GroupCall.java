package com.cards.nightsafe;

import java.util.ArrayList;

public class GroupCall {

    private String name;
    private String phoneNumber;


    public GroupCall(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static ArrayList<GroupCall> makeGroupCall() {
        ArrayList<GroupCall> list = new ArrayList<>();

        list.add(new GroupCall( "Matthew", "07738016783"));
        list.add(new GroupCall( "Mum", "07000000000"));
        list.add(new GroupCall( "Dad", "07000000000"));
        list.add(new GroupCall( "Test", "07000000000"));
        list.add(new GroupCall( "AGGGHHH", "07000000000"));
        return list;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
