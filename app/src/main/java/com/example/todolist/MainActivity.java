package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonSubmit;
    EditText editText;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    editText = findViewById(R.id.editText);
    buttonSubmit = findViewById(R.id.button);
    listView = findViewById(R.id.ListView);

    itemList = FileHelper.readData(this);
    arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_listview,itemList);
    listView.setAdapter(arrayAdapter);

    buttonSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String itemName = editText.getText().toString();
            itemList.add(itemName);
            editText.setText("");
            arrayAdapter.notifyDataSetChanged();

        }
    });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item from the list?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    itemList.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    });


    }

    @Override
    protected void onStop() {
        super.onStop();
        FileHelper.writeData(itemList,getApplicationContext());
    }
}