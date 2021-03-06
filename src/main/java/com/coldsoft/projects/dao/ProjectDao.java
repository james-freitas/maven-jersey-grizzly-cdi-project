package com.coldsoft.projects.dao;

import com.coldsoft.projects.model.Project;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class ProjectDao {

    private static Map<Long, Project> database = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    static {
        database.put(1L, new Project(1L, "Awesome Project", 2017));
    }

    public Project getProjectById(Long id) {
        return database.get(id);
    }

    public Project add(Project project){
        Long id = counter.incrementAndGet();
        project.setId(id);
        database.put(id, project);
        return database.get(id);
    }

    public void remove(Long id) {
        database.remove(id);
    }

    public void update(Project project) {
        database.remove(project.getId());
        database.put(project.getId(), project);
    }

    public void updateYear(Project projectWithModifiedYear) {
        Project project = database.get(projectWithModifiedYear.getId());
        project.setYear(projectWithModifiedYear.getYear());
        database.remove(project.getId());
        database.put(project.getId(), project);
    }
}
