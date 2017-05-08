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
        setListView();
    }

    private void setListView() {
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
        itemArr[0] = "07.05.17  Heute habe ich den ersten Prüfung hintermir. Ich bin sehr froh!!!"; // neu text
        itemArr[1] = "08.05.17  Heute war ich mit mine Mutti shoppen. Ich habe sehr coole Tasche gekauft";
        itemArr[2] = "09.05.17  So ein kompliezierter Tag heute. Meine Studium ist do schwierig....";
        itemArr[3] = "10.05.17  Wir haben eine tolle Team, aber sind noch trotzdem nicht fertig mit dem Projekt. " +
                "Ich hoffe wir schaffen es in nächste 3 Tage. Wir machen sehr viel zusammen";
        itemArr[4] = "12.05.17  Ich bereite mich auf meine Reise nach Paris. Wir machen mit Steffi heute ein Mädchen Party";
        itemArr[5] = "14.05.17  Endlich ist unsere Projekt fertig, jetzt kann ich in Ruhe reisen und schönen Urlaub haben. " +
                "Es war so toll in die Gruppe arbeiten";
        itemArr[6] = "15.05.17  8:00 bin ich im Flughafen. Meine reise startet!!!! Ja, ic bin sehr froh, obwohl ich Angst habe.  " +
                "15:00 ich bin endlich in Paris. Hier ist so schön, ich bin schon in der Stadt verliebt!!! " +
                "18:00 ich war heute essen in einem tollen Local <Fengi>, ist teuer aber sehr gut!!!";

        return itemArr;
    }
}
