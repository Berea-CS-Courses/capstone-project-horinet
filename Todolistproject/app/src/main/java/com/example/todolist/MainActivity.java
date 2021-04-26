package com.example.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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
    private Button login;
    private Button  create;
    //private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        //This is the function that controls what happens on create. The buttons have onclick listeners that take the user to the corresponding page.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen); //had to debug this because it was not working, however it shows now.
        
        
    }

    public void homescreen(View v){
        setContentView(R.layout.homescreen);
        todo = findViewById(R.id.todobutton);
        todo.setOnClickListener(this::onTodoScreen);
        calendar = findViewById(R.id.calendarb);
        calendar.setOnClickListener(this::calendarView);
        addtaskhs = findViewById(R.id.addtaskbtn);
        addtaskhs.setOnClickListener(this::newaddtaskscreen);
        
    }
    
    public void newaddtaskscreen(View v) {
        setContentView(R.layout.addtaskscreen);
        // need to figure out how to store this data
    }

    public void onTodoScreen(View v){
        //creates an array of the items on the todo list.
        //I will also update this with the addtaskscreen.xml file.
        setContentView(R.layout.todo_screen);
        readItems();
        lvItems = findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        // Setup remove listener method call
        additem = findViewById(R.id.btnAddItem);
        additem.setOnClickListener(this::onAddItem);
        setupListViewListener();
    }

    private void setupListViewListener() {
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