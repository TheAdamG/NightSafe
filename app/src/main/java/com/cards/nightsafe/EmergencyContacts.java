package com.cards.nightsafe;

public class EmergencyContacts {

    private String emergencyNameOne;
    private String emergencyNameTwo;
    private String emergencyPhoneOne;
    private String emergencyPhoneTwo;

    public EmergencyContacts(String emergencyNameOne, String emergencyNameTwo, String emergencyPhoneOne, String emergencyPhoneTwo) {
        this.emergencyNameOne = emergencyNameOne;
        this.emergencyNameTwo = emergencyNameTwo;
        this.emergencyPhoneOne = emergencyPhoneOne;
        this.emergencyPhoneTwo = emergencyPhoneTwo;
    }

    public String getEmergencyNameOne() {
        return emergencyNameOne;
    }

    public String getEmergencyNameTwo() {
        return emergencyNameTwo;
    }

    public String getEmergencyPhoneOne() {
        return emergencyPhoneOne;
    }

    public String getEmergencyPhoneTwo() {
        return emergencyPhoneTwo;
    }
}
