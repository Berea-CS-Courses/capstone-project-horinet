package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class newtodoscreen extends addtasks {
    //This needs to display the task name of each task, then when clicked show the whole task details
    Button exittd;
    public static String taskname1 = tasknamesend;
    ListView tdlist;
    ArrayList<String> addtasknames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtodoscreen);
        tdlist = findViewById(R.id.todolist);
        System.out.println("Counter in addtasks"+updatedcounter);
        addtasknames.add(taskname1);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,addtasknames);
        tdlist.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();



        //I am not going to do it this way anymore. It's not working.
        //Intent intent = getIntent();
        //taskname1 = intent.getStringExtra(addtasks.tasknamesend);
        //taskname1 = intent.getStringExtra(addtasks.tasknamesend);
        System.out.println("seeing if this adds the correct task name"+ taskname1);


        exittd = findViewById(R.id.exittodo);
        exittd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}