package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Objects;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private static final long TASK_ID = 1;
    private static final long PROJECT_ID = 1;
    private static final Task TASK_DEMO= new Task(TASK_ID,PROJECT_ID,"test task",111L);
    private static final Project PROJECT_DEMO= new Project(PROJECT_ID,"project test",0);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb(){
        database.close();
    }


    @Test
    public void insertTask() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getId(), TASK_DEMO.getId());

    }

    @Test
    public void deleteTask() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 1);
        this.database.taskDao().deleteTask(TASK_DEMO);
        List<Task> updatedTasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(updatedTasks.size() == 0);
    }
}
