package com.diarize;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        reference = FirebaseDatabase.getInstance().getReference();
        getItemDataFromFb();
    }

    private void setListView(final List<String> list) {
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent toItemLayout = new Intent(v.getContext(), ItemView.class);
                toItemLayout.putExtra("itemText", list.get(position));
                startActivity(toItemLayout);
            }
        });
    }


    private void getItemDataFromFb() {

        String userId = getIntent().getStringExtra("userId");
        Log.i("myLog", " userId in Calendar= " + userId);
        final List<String> list = new ArrayList<>();

        reference.child("users").child(userId).child("items").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("myLog ", "" + snapshot.getChildrenCount());


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String s = postSnapshot.getValue(String.class);
                    Log.i("myLog", s);
                    list.add(s);
                }
                if (list.size() != 0)
                    setListView(list); //callBack
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

    }
}
