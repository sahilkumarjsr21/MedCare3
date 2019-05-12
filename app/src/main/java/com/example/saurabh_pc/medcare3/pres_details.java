package com.example.saurabh_pc.medcare3;

public class pres_details {


    private int id;
    private String user_id, date, diagnosed_with, blood_pressure, things_to_follow,
            physician_name, registration_number, drug_and_dosage;

    public pres_details(int id, String user_id, String date, String diagnosed_with, String blood_pressure, String things_to_follow, String physician_name, String registration_number, String drug_and_dosage) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.diagnosed_with = diagnosed_with;
        this.blood_pressure = blood_pressure;
        this.things_to_follow = things_to_follow;
        this.physician_name = physician_name;
        this.registration_number = registration_number;
        this.drug_and_dosage = drug_and_dosage;
    }

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDate() {
        return date;
    }

    public String getDiagnosed_with() {
        return diagnosed_with;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public String getThings_to_follow() {
        return things_to_follow;
    }

    public String getPhysician_name() {
        return physician_name;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public String getDrug_and_dosage() {
        return drug_and_dosage;
    }
}
