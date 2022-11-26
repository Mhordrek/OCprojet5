package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProjects(Project[] projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);


    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    @Delete
    int deleteProject(Project project);
}


