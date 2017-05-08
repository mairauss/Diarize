package com.diarize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity1 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RES_IMAGE =1;
    private static final int RES_VIDEO =1;
    TextView text;
    ImageButton photo;
    ImageButton video;
    ImageButton voice;
    ImageView uploadImage;
    VideoView uploadVideo;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setUpToolbar();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                    startActivity(new Intent(MainActivity1.this, LoginActivity.class));
            }
        };

        text = (TextView) findViewById(R.id.addText);
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        photo = (ImageButton) findViewById(R.id.addPhoto);
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RES_IMAGE);
            }
        });
        video = (ImageButton) findViewById(R.id.addVideo);
        video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RES_VIDEO);
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
               /* Intent galleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RES_IMAGE);*/
            }
        });
        uploadVideo = (VideoView) findViewById(R.id.uploadVideo);
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent galleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RES_IMAGE);*/
            }
        });
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            uploadImage.setImageURI(selectedImage);
        }
       else if (requestCode == RES_VIDEO && resultCode == RESULT_OK && data != null)
        {
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

        FloatingActionButton calendarBtn = (FloatingActionButton) findViewById(R.id.calendarButton);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_log_out) {
            mAuth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
