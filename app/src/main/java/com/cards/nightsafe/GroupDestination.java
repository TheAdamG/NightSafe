package com.cards.nightsafe;

import java.util.ArrayList;

public class GroupDestination {

    private ArrayList<Integer> usernames;
    private String destination;
    private String eventLocation;

    public GroupDestination(ArrayList<Integer> usernames, String destination, String eventLocation) {
        this.usernames = usernames;
        this.destination = destination;
        this.eventLocation = eventLocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getEventLocation() {
        return eventLocation;
    }




}
