package com.coldsoft.projects.model;

import com.thoughtworks.xstream.XStream;

public class Project {

    private Long id;
    private String name;
    private int year;

    Project(){

    }

    public Project(Long id, String name, int year) {
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

    public Long getId() {
        return id;
    }
}
