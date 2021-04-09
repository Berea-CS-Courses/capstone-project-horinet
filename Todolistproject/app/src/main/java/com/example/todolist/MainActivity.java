package com.example.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View.OnClickListener;



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
    //private ImageButton calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        todo = findViewById(R.id.todobutton);
        todo.setOnClickListener(this::onTodoScreen);
        //calendar = findViewById(R.id.calendarview);
        //calendar.setOnClickListener(this::);


    }

    public void onTodoScreen(View v){
        setContentView(R.layout.todo_screen);
        readItems(); // <---- Add this line
        lvItems = findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        //items.add("Second Item");
        //items.add("Third Item Item");
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

    public void onAddItem(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try { FileUtils.writeLines(todoFile, items); }
        catch (IOException e) { e.printStackTrace();}
    }
    
}