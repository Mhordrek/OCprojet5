package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TodocViewModel extends ViewModel {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    @Nullable
    private LiveData<Project> currentProject;

    public TodocViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init(){
        if(this.currentProject != null){
            return;
        }
        projectDataSource.getProjects();
    }

    public LiveData<List<Project>> getProjects(){return projectDataSource.getProjects();}

    public void deleteProject(Project project){executor.execute(()-> projectDataSource.deleteProject(project));}

    public LiveData<List<Task>> getTasks(){return  taskDataSource.getTasks();}

    public void createTask(Task task){
        executor.execute(()-> {
            taskDataSource.insertTask(task);
        });
    }

    public void deleteTask(Task taskId){
        executor.execute(()-> taskDataSource.deleteTask(taskId));
    }

}
