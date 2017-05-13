package com.diarize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HelpActivity extends AppCompatActivity {


    EditText editText,editText3,editText2;

    Button b_send;

    //hier haben wir eine helpActivity gemacht, in der man eine Email direkt (über eine seiner Email Applikationen) schicken kann
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        editText=(EditText) findViewById(R.id.editText);
        editText2=(EditText) findViewById(R.id.editText2);
        editText3=(EditText) findViewById(R.id.editText3);

        b_send= (Button) findViewById(R.id.b_send);

        b_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String to= editText.getText().toString();
                String subject= editText3.getText().toString();
                String message= editText2.getText().toString();

                Intent intent= new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent,"Select Email Application"));

            }

        } );

    }
}
