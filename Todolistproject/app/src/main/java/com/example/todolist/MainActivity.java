package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.protobuf.StringValue;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private Button todo;
    private Button additem;
    private ImageButton calendar;
    private Button addtaskhs;
    private Button logout;
    private Button upcoming;
    private Button exittdscreen;
    public static int updatedcounter;
    public static int counter = updatedcounter;

    //private Button savetask;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        Intent intent = getIntent();
        updatedcounter = intent.getIntExtra(String.valueOf(updatedcounter), counter);
        System.out.println("This should be the updated counter" + updatedcounter);
        System.out.println("counter variable from addtask" + counter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this::logout);
        //TESTING counter to see if it works this way.
        //Test complete, counter works, just not correctly with my addtaskscreen
        //upcoming = findViewById(R.id.upcomingbtn);
        //upcoming.setOnClickListener(this::countertest);

        //Configuring all of my buttons with onclick listeners to go to the corresponding page :)
        todo = findViewById(R.id.todobutton);
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),newtodoscreen.class));
            }
        });
        //calendar = findViewById(R.id.calendarb); //not currently using the calendar view, on back back burner
        //calendar.setOnClickListener(this::calendarView);
        addtaskhs = findViewById(R.id.addtaskbtn);
        addtaskhs.setOnClickListener(new View.OnClickListener() {
            //If add task is clicked, it goes to the add task activity class.
            @Override
            public void onClick(View v) {
                //counter ++;
                startActivity(new Intent(getApplication(),addtasks.class));
            }
        });
    }

    /*private void countertest(View view) {
        //Only added to test the counter function to see if it was something else in my addtask.class, it was.
        System.out.println(counter);
        counter ++;
    }*/

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplication(),Login.class));
        finish();
    }

    
    /*public void newaddtaskscreen(View v) {
        setContentView(R.layout.addtaskscreen);
        // need to figure out how to store this data
        
    }*/

    public void onTodoScreen(View v){
        //creates an array of the items on the todo list.
        //What I NEED to happen: The text from "tasknametxt" in the addtaskscreen.xml file, to show up on the to-do screen in the list. 
        //What is CURRENTLY happening, It adds a task based off of "etNewItem" in the todoscreen
        setContentView(R.layout.todo_screen);
        readItems();
        lvItems = findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        // Setup remove listener method call
        additem = findViewById(R.id.btnAddItem);
        additem.setOnClickListener(this::onAddItem);
        setupListViewListener();
    }

    private void setupListViewListener() {
        //sets up the list view listener enabling it to add a task to the list
        lvItems.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    // Remove the item within array at position
                    items.remove(pos);
                    // Refresh the adapter
                    itemsAdapter.notifyDataSetChanged();
                    // Return true consumes the long click event (marks it handled)
                    writeItems();
                    return true;
                });
    }
    public void calendarView(View v){
        //just the calendar view that's being put on the back burner right now.
        //Might not be able to implement this fully at this point, but keeping it on the back burner
        setContentView(R.layout.calendarview);
    }

    public void onAddItem(View v) {
        //adds the item to the list from the todoscreen, probably not using this in the end I will be making changes to implement it with the addtaskscreen xml file
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
    private void readItems() {
        //also used this for the prototype but will either need to change it or delete it. Reads the items in the todo list
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        //also used this for the prototype but will either need to change it or delete it. writes the items in the todo list
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try { FileUtils.writeLines(todoFile, items); }
        catch (IOException e) { e.printStackTrace();}
    }
    
}