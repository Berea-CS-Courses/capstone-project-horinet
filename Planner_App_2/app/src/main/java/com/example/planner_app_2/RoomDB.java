package com.example.planner_app_2;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Created by enyason on 5/28/18.
 * Edited by horinet 3/31/21.
 */

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RoomDB extends RoomDatabase {

    public static final String LOG_TAG = RoomDB.class.getSimpleName();
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "todo_list";
    private static RoomDB sInstance;

    public static RoomDB getsInstance(Context context){
        if (sInstance== null) {
            synchronized (LOCK){
                Log.d(LOG_TAG,"creating new database");

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RoomDB.class,RoomDB.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }

        Log.d(LOG_TAG,"getting the database instance");

        return sInstance;

    }
    @Override
    protected void onResume() {
        super.onResume();


//        final List<Task> tasks = appDataBase.taskDao().loadAllTask();
//        toDoListAdapter.setTasks(tasks);


    }

    public abstract TaskDataAccessObject taskDao();

}