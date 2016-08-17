package com.example.dipayan.mediaplayer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {


    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    String filePath;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //set the media controller buttons

        if (mediaControls == null) {
            mediaControls = new MediaController(this);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("key1");

        String[] proj = {MediaStore.Video.VideoColumns._ID,MediaStore.MediaColumns.TITLE,MediaStore.MediaColumns.DATA};
        //IS_MUSCI is true for non zero values.
        //bugubugi is passed as a where by arguments. It is replaced in place of ?.
        String[] bugibugi = {title};
        //We store the returned stuff as a curser.
        cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,proj,MediaStore.MediaColumns.TITLE+"=?",bugibugi,null);
        cursor.moveToFirst();
        String path= cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.videoView);
        myVideoView.setOnPreparedListener(this);
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(this);
        // set a title for the progress bar
        progressDialog.setTitle("Android Video View Example");
        // set a message for the progress bar
        progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);
        // show the progress bar
        progressDialog.show();
        //set the media controller in VideoView
        myVideoView.setMediaController(mediaControls);
        //filePath = Environment.getExternalStorageDirectory()+ "/kitkat.3gp";
        //filePath =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)+"/bugibugi.mp4";
        myVideoView.setVideoPath(path);
        myVideoView.requestFocus();
        myVideoView.start();
        //myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.wildlife));
        //myVideoView.requestFocus();


        // filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)+ "/bugubugi.mp4";
        //TextView t = (TextView) findViewById(R.id.textView);
        //t.setText(filePath);



    }

    @Override
    public void onPrepared(MediaPlayer mediaplayer) {
        // TODO Auto-generated method stub
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        // close the progress bar and play the video
        progressDialog.dismiss();
        //if we have a position on savedInstanceState, the video playback should start from here
        myVideoView.seekTo(position);
        if (position == 0) {
            myVideoView.start();
        } else {
            //if we come from a resumed activity, video playback will be paused
            myVideoView.pause();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }


}