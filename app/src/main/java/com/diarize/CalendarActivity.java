package com.diarize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.ItemAdapter;
import entitys.Item;

public class CalendarActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout_);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ItemAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareitemData();

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


        adapter.notifyDataSetChanged();
    }
}
