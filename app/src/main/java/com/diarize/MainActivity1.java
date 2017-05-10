package com.diarize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity1 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RES_IMAGE = 1;
    private static final int RES_VIDEO = 2;
    private StorageReference sr = null;
    private TextView text, currentDate;
    ImageButton photo;
    ImageButton video;
    ImageButton voice;
    ImageView uploadImage;
    VideoView uploadVideo;
    String userId;
    private FloatingActionButton saveButton, calendarBtn;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setUpToolbar();
        setUpCurrentDate();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                    startActivity(new Intent(MainActivity1.this, LoginActivity.class));
                else
                   userId =  firebaseAuth.getCurrentUser().getUid();

            }
        };

        text = (TextView) findViewById(R.id.addText);
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        photo = (ImageButton) findViewById(R.id.addPhoto);
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RES_IMAGE);
            }
        });
        video = (ImageButton) findViewById(R.id.addVideo);
        video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RES_VIDEO);
            }
        });
        voice = (ImageButton) findViewById(R.id.addVoice);
        voice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        uploadVideo = (VideoView) findViewById(R.id.uploadVideo);
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        saveButton = (FloatingActionButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText addText = (EditText) findViewById(R.id.addText);
                Log.i("myTag", addText.getText().toString());
                Log.i("myTag", userId);


                String text = addText.getText().toString();
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users").child(userId).child("items");
                myRef.push().setValue(text);


                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpCurrentDate() {
        currentDate = (TextView) findViewById(R.id.current_data);
        String modifiedDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        currentDate.setText(modifiedDate);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uploadImage.setImageURI(selectedImage);
        } else if (requestCode == RES_VIDEO && resultCode == RESULT_OK && data != null) {
            Uri selectedVideo = data.getData();
            uploadVideo.setVideoURI(selectedVideo);
            uploadVideo.requestFocus();
            uploadVideo.start();
        }

    }

    private void test() {
        Intent intent = new Intent(this, ItemView.class);
        startActivity(intent);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarBtn = (FloatingActionButton) findViewById(R.id.calendarButton);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_log_out) {
            mAuth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
