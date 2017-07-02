package com.coldsoft.projects.model;

import com.thoughtworks.xstream.XStream;

public class Project {

    private final Long id;
    private final String name;
    private final int year;


    public Project(Long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public String toXML() {
        return new XStream().toXML(this);
    }
}
