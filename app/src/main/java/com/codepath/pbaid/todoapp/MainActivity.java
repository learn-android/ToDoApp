package com.codepath.pbaid.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.ListViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.pbaid.todoapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView lvItems;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItemm();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        editText = (EditText) findViewById(R.id.etTextItem);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void populateArrayItemm()
    {
        todoItems = new ArrayList<>();
        readItems();
        aTodoAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todoItems);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        writeItems();
        return true;
    }

    public void onAddItems(View view) {
        if(!editText.getText().toString().replaceAll(" ","").isEmpty())
        {aTodoAdapter.add(editText.getText().toString());}
        editText.setText("");
        writeItems();

    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try
        {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e)
        {

        }
    }

    private void writeItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try
        {
            FileUtils.writeLines(file,todoItems);
        }
        catch (IOException e)
        {

        }

    }
}
