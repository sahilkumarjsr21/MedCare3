package com.example.saurabh_pc.medcare3;

public class FirstAid
{
    int id ;
    String name,file;

    public FirstAid() {
    }

    public FirstAid(int id, String name, String file) {

        this.id = id;
        this.name = name;
        this.file = file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
