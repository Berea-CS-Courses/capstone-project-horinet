package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class newtodoscreen extends addtasks {
    //This needs to display the task name of each task, then when clicked show the whole task details
    Button exittd,exittinfo;
    public static String taskname1 = tasknamesend;
    ListView tdlist;
    ArrayList<String> addtasknames = new ArrayList<>();
    String docname = "Tasksdoc";
    String taskname2;
    DocumentReference tdref;
    TextView tname, tdes, std, dued, duet, sttime, etime, rdate, rtime;

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
        tdlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                docname = docname+position;
                System.out.println("The position clicked is" +position);
                tdref = tstore.collection("users").document(userID).collection("Tasks").document(docname);
                tdref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        assert documentSnapshot != null;
                        if (documentSnapshot.exists()){
                            /*taskname2 = documentSnapshot.getString("Task Name");
                            addtasknames.add(taskname2);
                            tdlist.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();*/
                            setContentView(R.layout.taskinfo);
                            tname = findViewById(R.id.tn);
                            tdes = findViewById(R.id.td);
                            std = findViewById(R.id.sd);
                            dued = findViewById(R.id.dd);
                            duet = findViewById(R.id.dt);
                            sttime = findViewById(R.id.st);
                            etime = findViewById(R.id.et);
                            rdate = findViewById(R.id.rd);
                            rtime = findViewById(R.id.rt);
                            exittinfo = findViewById(R.id.exittaskinfo);
                            tname.setText(documentSnapshot.getString("Task Name"));
                            tdes.setText(documentSnapshot.getString("Task Description"));
                            std.setText(documentSnapshot.getString("Start Date"));
                            dued.setText(documentSnapshot.getString("Due Date"));
                            duet.setText(documentSnapshot.getString("Due Time"));
                            sttime.setText(documentSnapshot.getString("Start Time"));
                            etime.setText(documentSnapshot.getString("End Time"));
                            rdate.setText(documentSnapshot.getString("Reminder Date"));
                            rtime.setText(documentSnapshot.getString("Reminder Time"));
                            System.out.println("This is the name from fb"+documentSnapshot.getString("Task Name"));
                            //let's see if this works
                            // print's it but now IDK how to show it
                            exittinfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(), newtodoscreen.class));
                                }
                            });
                        }
                        else {
                            Toast.makeText(newtodoscreen.this,"This doc doesn't exist",Toast.LENGTH_SHORT).show(); //Unit test that makes sure the document exists if it doesn't exist it shows the error
                        }
                    }
                });
            }
        });
        //System.out.println("seeing if this adds the correct task name"+ taskname1); //this was a part of unit testing that I don't need anymore
        exittd = findViewById(R.id.exittodo);
        exittd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}