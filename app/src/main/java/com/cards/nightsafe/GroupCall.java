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

        list.add(new GroupCall("False", "07738016783"));
        list.add(new GroupCall("mum", "07000000000"));
        return list;
    }

    @Override
    public String toString() {
        return (name + " " + phoneNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
