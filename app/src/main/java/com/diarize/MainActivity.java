package com.diarize;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Created by y.baidiuk on 21/04/2017.
 * <p>
 * hear user can create a new Item or simply go to CalendarActivity with the Button on the Top.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AudioRecordTest";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RES_IMAGE = 1;
    private static final int RES_VIDEO = 2;
    private StorageReference sr = null;
    private TextView text, currentDate;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    StorageManager sm;
    ImageButton photo;
//    ImageButton voice;
    ImageView uploadImage;
    VideoView uploadVideo;
    TextView recordText;
    String userId;
    private FloatingActionButton saveButton, calendarBtn;
    private FirebaseDatabase database;
    private String modifiedDate;
    private ProgressDialog pd;

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
                    userId = firebaseAuth.getCurrentUser().getUid();
            }
        };

        /*Speichert Audio in EXTERNAL STORAGE*/
        pd = new ProgressDialog(this);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        sr = FirebaseStorage.getInstance().getReference();

        text = (TextView) findViewById(R.id.addText);
        /*ImageButton fuer Foto, um die Fotos von EXTERNAL_STORAGE hochzuladen*/
        photo = (ImageButton) findViewById(R.id.addPhoto);
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RES_IMAGE);
            }
        });

        /*ImageButton fuer Audio, um die Audios in EXTERNAL_STORAGE zu speichern
        * mittels recordText sieht man, ob Audio Aufnahme durchgefuehrt wurde oder nicht
        * wenn ja, dann sieht man statt "Recording...", und "Stopped, wenn Aufnahme abgeschlossen ist"*/
//        recordText = (TextView) findViewById(R.id.recordText);
//        voice = (ImageButton) findViewById(R.id.addVoice);
//        voice.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    startRecording();
//                    recordText.setText("Recording...");
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    stopRecording();
//                    recordText.setText("Stopped");
//                }
//                return false;
//            }
//        });

        /*uploadImage ist fuer bereits hochgeladene Bilder*/
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        /*uploadImage ist fuer bereits hochgeladene Videos*/
        uploadVideo = (VideoView) findViewById(R.id.uploadVideo);
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        uploadVideo.setMediaController(null);


        saveButton = (FloatingActionButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText addText = (EditText) findViewById(R.id.addText);
                Log.i("myTag", addText.getText().toString());
                Log.i("myTag", userId);

                String text = addText.getText().toString();
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users").child(userId).child("items");
                myRef.push().setValue(new Item(text, modifiedDate));

                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();

            }
        });
    }


    /**
     * show current Data on the window Top.
     */
    private void setUpCurrentDate() {
        currentDate = (TextView) findViewById(R.id.current_data);
        modifiedDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        currentDate.setText(modifiedDate);
    }

    /*Die Fotos und Videos werden auf Diarize App hochgeladen
    * und noch die Fotos werden auf Firebase unter "Photos" Ordner gespeichert*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uploadImage.setImageURI(selectedImage);
            uploadImage.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            StorageReference path = sr.child("Photos").child(uri.getLastPathSegment());
            path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                }
            });
        } else if (requestCode == RES_VIDEO && resultCode == RESULT_OK && data != null) {
            uploadVideo.setVisibility(View.VISIBLE);
            Uri selectedVideo = data.getData();
            uploadVideo.setVideoURI(selectedVideo);
            uploadVideo.requestFocus();
            uploadVideo.start();

        }

    }

    /*Audio Aufnahme wird gestartet*/
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

    /*Audio Aufnahme wird gestopped*/
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        uploadAudio();
    }

    /*Audio Aufnahme wird auf Firebase gespeichert*/
    private void uploadAudio() {
        pd.setMessage("Uploading...");
        pd.show();
        StorageReference path = sr.child("Audio").child("recorded_audio.3gp");
        Uri uri = Uri.fromFile(new File(mFileName));
        path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                recordText.setText("Uploaded");
            }
        });
    }

    private void test() {
        Intent intent = new Intent(this, ItemView.class);
        startActivity(intent);
    }

    /**
     * setUping of Toolbar
     */
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

        if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}