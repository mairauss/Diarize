package com.diarize;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by y.baidiuk on 01/05/2017.
 * <p>
 * CalendarActivity : here we have CalendatView am Top and ItemList below.
 */
public class CalendarActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ArrayList<Item> itemList;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        itemList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        calendarView = (CalendarView) findViewById(R.id.calendarView3);
        getItemDataFromFb(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        setCalendarListener();
    }

    private void setCalendarListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("DDATA", year + " " + month + " " + dayOfMonth);

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                Date date = calendar.getTime();

                Log.d("DDATA", date.toString());

                getItemDataFromFb(new SimpleDateFormat("dd.MM.yyyy").format(date));
            }
        });
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
                toItemLayout.putExtra("itemText", itemList.get(position).getText());
                toItemLayout.putExtra("itemData", itemList.get(position).getData());
                startActivity(toItemLayout);
            }
        });
    }


    //get item for Firebase Databank
    private void getItemDataFromFb(final String data) {

        String userId = getIntent().getStringExtra("userId");
        Log.i("myLog", " userId in Calendar= " + userId);
        final List<String> list = new ArrayList<>();

        reference.child("users").child(userId).child("items").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("myLog ", "" + snapshot.getChildrenCount());


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    Log.i("myLog text", item.getText());
                    Log.i("myLog getData", item.getData());
                    Log.i("myLog data", data);

                    if(item.getData().equals(data)){
                        itemList.add(item);
                        list.add(item.getData() + " " + item.getText());
                    }

                }
//                if (list.size() != 0)
                    setListView(list); //callBack
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

    }
}
