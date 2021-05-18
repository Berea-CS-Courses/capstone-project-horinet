package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class newtodoscreen extends addtasks {
    //This needs to display the task name of each task, then when clicked show the whole task details
    Button exittd;
    public static String taskname1 = tasknamesend;
    ListView tdlist;
    ArrayList<String> addtasknames = new ArrayList<>();
    String docname = "Tasksdoc";
    FirebaseFirestore tdstore;
    FirebaseAuth tdauth;
    DocumentReference tdref;
    TextView twtd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtodoscreen);
        tdlist = findViewById(R.id.todolist);
        twtd = findViewById(R.id.textviewtd);
        System.out.println("Counter in addtasks"+updatedcounter);
        addtasknames.add(taskname1);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,addtasknames);
        tdlist.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        tdlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                docname = docname+ position;
                DocumentReference tdref = tstore.collection("users").document(userID).collection("Tasks").document("Tasksdoc0");
                tdref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            Map<String, Object> docinfo = documentSnapshot.getData();
                            //twtd.setText(docinfo);
                            System.out.println(docinfo); // print's it but now IDK how to show it

                        }
                        else {
                            Toast.makeText(newtodoscreen.this,"This doc doesn't exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });



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