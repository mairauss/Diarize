package com.diarize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import item_list.Item;

public class CalendarActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout_);


    }


    private void prepareitemData() {
        Item Item1 = new Item(0, new Date(), "text  bla bla ", false, false, false);
        itemList.add(Item1);

        Item Item2 = new Item(0, new Date(), "text  bla bla ", false, false, false);
        itemList.add(Item2);

        Item Item3 = new Item(0, new Date(), "text  bla bla ", false, false, false);
        itemList.add(Item3);

        Item Item4 = new Item(0, new Date(), "text  bla bla ", false, false, false);
        itemList.add(Item4);
    }
}
