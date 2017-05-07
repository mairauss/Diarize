package com.diarize;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);


        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArray());
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent toItemLayout = new Intent(v.getContext(), ItemView.class);
                startActivity(toItemLayout);
            }
        });
    }


    private String[] getArray() {
        String[] itemArr = new String[9];
        itemArr[0] = "07.05.17  Dicko + Video";
        itemArr[1] = "07.05.17  Dicko + Video";
        itemArr[2] = "07.05.17  Dicko + Video";
        itemArr[3] = "07.05.17  Dicko + Video";
        itemArr[4] = "07.05.17  Dicko + Video";
        itemArr[5] = "07.05.17  Dicko + Video";
        itemArr[6] = "07.05.17  Dicko + Video";
        itemArr[7] = "07.05.17  Dicko + Video";
        itemArr[8] = "07.05.17  Dicko + Video";
        return itemArr;
    }
}
