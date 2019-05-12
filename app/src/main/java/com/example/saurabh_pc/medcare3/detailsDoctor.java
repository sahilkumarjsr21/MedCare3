package com.example.saurabh_pc.medcare3;

public class detailsDoctor {

    private int id;
    private String name, degree, specialist_in, experience, rating,
            consultation_fee, location, timing, days, ph_no, image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getSpecialist_in() {
        return specialist_in;
    }

    public String getExperience() {
        return experience;
    }

    public String getRating() {
        return rating;
    }

    public String getConsultation_fee() {
        return consultation_fee;
    }

    public String getLocation() {
        return location;
    }

    public String getTiming() {
        return timing;
    }

    public String getDays() {
        return days;
    }

    public String getPh_no() {
        return ph_no;
    }

    public detailsDoctor(int id, String name, String degree, String specialist_in, String experience,
                         String rating, String consultation_fee, String location, String timing, String days, String ph_no) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.specialist_in = specialist_in;
        this.experience = experience;
        this.rating = rating;
        this.consultation_fee = consultation_fee;
        this.location = location;
        this.timing = timing;
        this.days = days;
        this.ph_no = ph_no;

    }
}

