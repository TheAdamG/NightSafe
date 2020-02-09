package com.cards.nightsafe;


public class EventInfo {

    private String name;
    private String location;
    private String date;

    public EventInfo(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName () {return name;}
    public String getLocation () {return location;}
    public String getDate () {return date;}

    @Override
    public String toString() {
        return (name +" "+ location +" "+ date);
    }

}
