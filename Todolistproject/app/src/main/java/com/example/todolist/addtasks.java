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

public class addtasks extends MainActivity {
    public static final String TAG = "TAG";
    EditText taskname, taskdes, stdate, duedate, duetime, sttime, endtime, remdate, remtime;
    FirebaseFirestore tstore;
    FirebaseAuth tauth;
    Button savetaskbtn,backtohomebtn;
    String tasksdoc = "Tasksdoc"+counter;
    String counter1;

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
        backtohomebtn = findViewById(R.id.Backtohome);


        savetaskbtn.setOnClickListener(new View.OnClickListener() {
            private void counttdname() {
                //This is buggggy. But it deletes the old counter, adds the new one and increments the docs.
                //Doesn't work once the user clicks out of the screen, and I've had a hard time figuring out how to fix that
                tasksdoc = tasksdoc.substring(0, tasksdoc.length() - 1);
                tasksdoc = tasksdoc + counter;
                counter++;
                counter1 = String.valueOf(counter);
                System.out.println(tasksdoc);
                System.out.println(counter);
            }
            @Override
            public void onClick(View v) {
                counttdname();
                /*taskname.setText("");
                taskdes.setText("");
                stdate.setText("");
                duedate.setText("");
                duetime.setText("");
                sttime.setText("");
                endtime.setText("");
                remdate.setText("");
                remtime.setText("");*/
                String staskname = taskname.getText().toString().trim();
                //Log.d(TAG, "onCreate: test print task name" + staskname);
                String staskdesc = taskdes.getText().toString().trim();
                String sstdate = stdate.getText().toString();
                String sduedate = duedate.getText().toString();
                String sduetime = duetime.getText().toString();
                String ssttime = sttime.getText().toString();
                String sendtime = endtime.getText().toString();
                String sremdate = remdate.getText().toString();
                String sremtime = remtime.getText().toString();
                // grab the edit text info and convert them to strings (Most of these are times or dates and i'm not entirely sure how to import those to firestore atm
                String userID = Objects.requireNonNull(tauth.getCurrentUser()).getUid();
                DocumentReference taskdoc = tstore.collection("users").document(userID).collection("Tasks").document(tasksdoc);
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
                        Log.d(TAG, "onSuccess: task name is" + tasksdoc);
                        Log.d(TAG, "onSuccess: counter is" + counter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });
            }
        });

        /*backtohomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sends counter to maintasks
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                System.out.println("send updated count from addtasks" +counter1);
            }
        });*/
        backtohomebtn.setOnClickListener(this::sendcountback);
    }
    public void sendcountback(View v){
        Intent i = new Intent(getApplicationContext(),addtasks.class);
        i.putExtra("counter",counter1);
        System.out.println("send updated count from addtasks from method" +counter1);
        startActivity(i);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}