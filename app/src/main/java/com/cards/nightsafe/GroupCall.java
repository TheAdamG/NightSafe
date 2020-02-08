package com.cards.nightsafe;

import java.util.ArrayList;

public class GroupCall {

    private boolean emergencyContact;
    private String name;
    private String phoneNumber;


    public GroupCall(boolean emergencyContact, String name, String phoneNumber) {
        this.emergencyContact = emergencyContact;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private ArrayList<GroupCall> makeGroupCall() {
        ArrayList<GroupCall> list = new ArrayList<>();

        list.add(new GroupCall(false, "False", "07738016783"));
        list.add(new GroupCall(true, "mum", "07000000000"));
        return list;
    }

}
