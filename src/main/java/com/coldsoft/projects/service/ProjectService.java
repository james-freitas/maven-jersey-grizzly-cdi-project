package com.coldsoft.projects.service;

import com.coldsoft.projects.dao.ProjectDao;
import com.coldsoft.projects.model.Project;
import com.thoughtworks.xstream.XStream;

import javax.inject.Inject;

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

    public Project addProjectFromXML(String xmlContent) {
        Project project = (Project) new XStream().fromXML(xmlContent);
        return projectDao.add(project);
    }

}
