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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//this doesn't work.

public class addtasks extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText taskname, taskdes, stdate, duedate, duetime, sttime, endtime, remdate, remtime;
    FirebaseFirestore tstore;
    FirebaseAuth tauth;
    Button savetaskbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtasks);
        tauth = FirebaseAuth.getInstance();
        tstore = FirebaseFirestore.getInstance();
        savetaskbtn = findViewById(R.id.savetaskb);
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
        String userID = Objects.requireNonNull(tauth.getCurrentUser()).getUid();

        savetaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference taskdoc = tstore.collection("users").document(userID).collection("Tasks").document("Tasksdoc");
                System.out.println(taskdoc); //this doesn't work either
                Map<String,Object> stask = new HashMap<>();
                stask.put("Task Name", staskname);
                stask.put("Task Description", staskdesc);
                stask.put("Start Date", sstdate);
                stask.put("Due Date", sduedate);
                stask.put("Due Time", sduetime);
                stask.put("Start Time", ssttime);
                stask.put("End Time", sendtime);
                stask.put("Reminder Date", sremdate);
                stask.put("Reminder Time", sremtime);

                taskdoc.set(stask).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: task created for" + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}