package com.cleanup.todoc.repositories;

import com.cleanup.todoc.database.dao.ProjectDao;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {this.projectDao = projectDao;}

    public void getProjects(){projectDao.getProjects();}
}
