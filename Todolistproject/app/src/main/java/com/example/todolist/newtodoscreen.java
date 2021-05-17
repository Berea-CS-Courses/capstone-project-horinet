package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newtodoscreen extends addtasks {
    //This needs to display the task name of each task, then when clicked show the whole task details
    Button exittd;
    public static String taskname1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtodoscreen);
        Intent intent = getIntent();
        taskname = intent.getStringExtra(tasknamesend);

        /*savetaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        exittd = findViewById(R.id.exittodo);
        exittd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}