package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

//this doesn't work.

public class addtasks extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText taskname, taskdes, stdate, duedate, duetime, sttime, endtime, remdate, remtime;
    FirebaseFirestore tstore;
    String userIDt;
    FirebaseAuth tauth;
    Button savetaskbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtasks);
        savetaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskname = findViewById(R.id.tasknametxt);
                taskdes = findViewById(R.id.taskdesctext);
                stdate = findViewById(R.id.startdatetext);
                duedate = findViewById(R.id.duedatetext);
                duetime = findViewById(R.id.duetimetext2);
                sttime = findViewById(R.id.starttimetext);
                endtime = findViewById(R.id.endtimetext);
                remdate = findViewById(R.id.remdatetext);
                remtime = findViewById(R.id.remtimetext);
                savetaskbtn = findViewById(R.id.savetaskb);
                String staskname = taskname.getText().toString().trim();
                String staskdesc = taskdes.getText().toString().trim();
                String sstdate = stdate.getText().toString();
                String sduedate = duedate.getText().toString();
                String sduetime = duetime.getText().toString();
                String ssttime = sttime.getText().toString();
                String sendtime = endtime.getText().toString();
                String sremdate = remdate.getText().toString();
                String sremtime = remtime.getText().toString();
                DocumentReference documentreft = tstore.collection("users").document("users");
                Map<String,Object> tasks = new HashMap<>();
                tasks.put("Task Name", staskname);
                tasks.put("Task Description", staskdesc);
                tasks.put("Start Date", sstdate);
                tasks.put("Due Date", sduedate);
                tasks.put("Due Time", sduetime);
                tasks.put("Start Time", ssttime);
                tasks.put("End Time", sendtime);
                tasks.put("Reminder Date", sremdate);
                tasks.put("Reminder Time", sremtime);
                documentreft.set(tasks).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: task created for" + userIDt);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });

            }
        });
    }
}