package com.diarize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ItemView extends AppCompatActivity {


    Button shareButton;
    Intent shareIntent;
    String shareBody="Diarize is a really cool APP!! :)";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        shareButton =(Button) findViewById(R.id.share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("Text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(shareIntent,"Share Via"));
            }
        });
    }
}
