package com.coldsoft.projects.model;

import com.thoughtworks.xstream.XStream;

public class Project {

    private long id;
    private String name;
    private int year;

    Project(){

    }

    public Project(long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Project(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String toXML() {
        return new XStream().toXML(this);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
