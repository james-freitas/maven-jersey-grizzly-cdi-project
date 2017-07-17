package com.coldsoft.projects.service;

import com.coldsoft.projects.dao.ProjectDao;
import com.coldsoft.projects.model.Project;
import com.thoughtworks.xstream.XStream;

import javax.inject.Inject;

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

    public Project addProjectFromXML(String xmlContent) {
        Project project = (Project) new XStream().fromXML(xmlContent);
        return projectDao.add(project);
    }

    public void update(Project project) {
        projectDao.update(project);
    }

    public void delete(Long id) {
        projectDao.remove(id);
    }

    public void updateYear(Project projectWithModifiedYear) {
        projectDao.updateYear(projectWithModifiedYear);
    }
}
