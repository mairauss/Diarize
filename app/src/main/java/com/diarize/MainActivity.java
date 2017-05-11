package com.diarize;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AudioRecordTest";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RES_IMAGE =1;
    private static final int RES_VIDEO =2;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION =200;
    private StorageReference sr = null;
    private TextView text, currentDate;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    StorageManager sm;
    ImageButton photo;
    ImageButton video;
    ImageButton voice;
    ImageView uploadImage;
    VideoView uploadVideo;
    TextView recordText;
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
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                else
                    userId =  firebaseAuth.getCurrentUser().getUid();
            }
        };

        mFileName= Environment.getExternalStorageDirectory() + "recorded_audio.3gp";


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
        voice.setOnTouchListener(new View.OnTouchListener() {
                           public boolean onTouch(View v, MotionEvent event) {
                               Log.d(TAG,"I am here");
                    if(event.getAction()==MotionEvent.ACTION_DOWN) {
                        Log.d(TAG,"!!!record!!!");
                        startRecording();
                        recordText.setText("Recording..");
                        Log.d(TAG,"!!!record 2!!!");
                    }
                    else if(event.getAction()==MotionEvent.ACTION_UP){
                        Log.d(TAG,"!!!record stopped!!!");
                            stopRecording();
                            recordText.setText("Stopped");
                        Log.d(TAG,"!!!record stopped 2!!!");
                    }
                    return false;
                }
        });

        recordText = (TextView) findViewById(R.id.recordText);
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {}
        });
        uploadVideo = (VideoView) findViewById(R.id.uploadVideo);
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {}
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
                intent.putExtra("userId", userId);
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

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Audio prepare failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
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
                intent.putExtra("userId", userId);
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
