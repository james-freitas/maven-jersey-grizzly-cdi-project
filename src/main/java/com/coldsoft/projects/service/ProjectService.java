package com.coldsoft.projects.service;

import com.coldsoft.projects.dao.ProjectDao;
import com.coldsoft.projects.model.Project;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

//@Named
//@Singleton
public class ProjectService {

    @Override
    public String toString() {
    return "ProjectServiceInstance";
  }

    @Inject
    private ProjectDao projectDao;


    public Project getProjectById(Long id) {
        return projectDao.getProjectById(id);
    }
}
