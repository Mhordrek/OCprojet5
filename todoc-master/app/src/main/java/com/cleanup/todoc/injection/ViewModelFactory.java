package com.cleanup.todoc.injection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TodocViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context){
        if(factory == null){
            synchronized (ViewModelFactory.class){
                if(factory == null){
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    public ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        this.projectDataSource = new ProjectDataRepository(database.projectDao());
        this.taskDataSource = new TaskDataRepository(database.taskDao());
        this.executor = Executors.newSingleThreadExecutor();

    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TodocViewModel.class)){
            return (T) new TodocViewModel(projectDataSource,taskDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
