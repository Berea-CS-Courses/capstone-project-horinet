package com.example.todolist;

import androidx.annotation.NonNull;

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
import java.util.Objects;

public class addtasks extends MainActivity {
    public static final String TAG = "TAG";
    EditText taskname, taskdes, stdate, duedate, duetime, sttime, endtime, remdate, remtime;
    FirebaseFirestore tstore;
    FirebaseAuth tauth;
    Button savetaskbtn,backtohomebtn;
    String tasksdoc = "Tasksdoc"+counter;
    public static String tasknamesend;
    public static String userID;
    //String counter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtasks);
        tauth = FirebaseAuth.getInstance();
        tstore = FirebaseFirestore.getInstance();
        savetaskbtn = findViewById(R.id.savetaskb);
        taskname = findViewById(R.id.tasknametxt);
        //tasknamesend = taskname.getText().toString().trim();
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
                //counter1 = String.valueOf(counter);
                System.out.println(tasksdoc);
                System.out.println(counter);
            }
            @Override
            public void onClick(View v) {
                tasknamesend = taskname.getText().toString().trim(); //adding this to send it to todoscreen
                counttdname();
                //sendtaskname();
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
                userID = Objects.requireNonNull(tauth.getCurrentUser()).getUid();
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
                Intent i1 = new Intent(addtasks.this,newtodoscreen.class);
                i1.putExtra(String.valueOf(newtodoscreen.taskname1),tasknamesend);
                startActivity(i1);
                System.out.println("this should be the task name!!!!!"+staskname);
                taskdoc.set(stask).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: task created for" + userID);
                        Log.d(TAG, "onSuccess: task document name is" + tasksdoc);
                        Log.d(TAG, "onSuccess: counter is" + counter);
                        //Sets the edit texts fields to be empty :)
                        /*taskname.setText("");
                        taskdes.setText("");
                        stdate.setText("");
                        duedate.setText("");
                        duetime.setText("");
                        sttime.setText("");
                        endtime.setText("");
                        remdate.setText("");
                        remtime.setText("");*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });
            }
        });
        //This was also  part of the unit testing to see if the counter would be sent this way as well, but
        //we found out that I had to create a method, just use counter, not counter1, and send an int value
        //in order to do that. And in the main activity I had to update counter to counter=updated counter.
        /*backtohomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sends counter to maintasks
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                System.out.println("send updated count from addtasks" +counter1);
            }
        });*/
        backtohomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcountback();
            }
        });
    }
    public void sendcountback(){
        //Emely helped me with this part of the code as well as https://www.youtube.com/watch?v=tNmxq4OVq7E this video.
        //This sends the counter back to the mainactivity so that counter does not go back to 0 after the button click
        //The print statements are here for unit testing to make sure that the correct value is being sent.
        Intent i = new Intent(addtasks.this,MainActivity.class);
        i.putExtra(String.valueOf(MainActivity.updatedcounter),counter);
        System.out.println("send updated count from addtasks from method" +counter);
        startActivity(i);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void sendtaskname(){
        //I don't call this anymore, because I just had my newtodoscreen.java class extend this class so that I could use the tasknamesend value directly
        //Since I am not updating that each time I don't have to send it to the other class and back in this way
        Intent i1 = new Intent(addtasks.this,newtodoscreen.class);
        i1.putExtra(String.valueOf(newtodoscreen.taskname1),tasknamesend);
        startActivity(i1);
        System.out.println("this should be the task name!!!!!"+tasknamesend);
    }
}