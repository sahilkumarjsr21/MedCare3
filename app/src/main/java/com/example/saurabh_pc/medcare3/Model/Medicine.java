package com.example.saurabh_pc.medcare3.Model;

public class Medicine {
    public int id;
    public String name, about, use, how, side, precaution, interaction, miss, storage;

    public Medicine(int id, String name, String about, String use, String how, String side, String precaution, String interaction, String miss, String storage) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.use = use;
        this.how = how;
        this.side = side;
        this.precaution = precaution;
        this.interaction = interaction;
        this.miss = miss;
        this.storage = storage;
    }

    public Medicine() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getMiss() {
        return miss;
    }

    public void setMiss(String miss) {
        this.miss = miss;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
