package com.diarize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button help_button;
    Button profil_button;
    Button account_button;

    //settingsAcitvity schickt uns an 3 weiter seiten weiter, über dementspechend 3 Buttons -> help_button, profil_button, Account_button,

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);


        help_button = (Button) findViewById(R.id.help_button);

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SettingsActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        profil_button= (Button) findViewById(R.id.profil_button);

        profil_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,ProfilActivity.class);
                startActivity(intent);
            }
        });


        account_button= (Button) findViewById(R.id.account_button);

        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

    }

}
