package com.coldsoft.projects.dao;

import com.coldsoft.projects.model.Project;

import java.util.HashMap;
import java.util.Map;


public class ProjectDao {

    private static Map<Long, Project> database = new HashMap<>();

    static {
        database.put(1L, new Project(1L, "Awesome Project", 2017));
        database.put(2L, new Project(2L, "Basic Project", 2015));
    }

    public Project getProjectById(Long id) {
        return database.get(id);
    }
}
