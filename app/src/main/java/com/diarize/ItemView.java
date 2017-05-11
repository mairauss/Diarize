package com.diarize;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ItemView extends AppCompatActivity {


    Intent shareIntent;
    String itemText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);
        itemText = getIntent().getStringExtra("itemText");

        setItemText();
        setShareButton();
    }

    private void setShareButton() {
        FloatingActionButton shareButton = (FloatingActionButton) findViewById(R.id.share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, itemText);
                startActivity(Intent.createChooser(shareIntent, "Share Via"));

            }
        });
    }

    private void setItemText() {
        TextView itemTextVIew = (TextView) findViewById(R.id.item_text);
        itemTextVIew.setText(itemText);
    }
}
