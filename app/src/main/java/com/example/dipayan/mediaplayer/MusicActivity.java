package com.example.dipayan.mediaplayer;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    //TextView tx1;
    Uri uri;
    ImageView edi4;
    MediaPlayer mediaPlayer;
    Cursor cursor,cursor1;
    TextView edi1,edi2,duration,edi3,edi5,edi6,edi7,edi8;
    SeekBar seek_bar;
    Button play,paused,restart;
    Handler handler = new Handler();
    int mprogress=0;
    double oneTimeOnly=0;
    private double finalTime=0;
    private double startTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //tx1= (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("key1");
        String[] proj = {MediaStore.Audio.Media._ID,MediaStore.MediaColumns.TITLE,MediaStore.Audio.AudioColumns.IS_MUSIC,MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.ARTIST,MediaStore.Audio.AudioColumns.ALBUM,MediaStore.Audio.AudioColumns.DURATION,MediaStore.Audio.AudioColumns.YEAR};
        String[] bugibugi = {title};
        cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj,MediaStore.Audio.AudioColumns.TITLE+" =?",bugibugi,null);
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
        String d      = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
        String year   = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR));
        String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
        uri=Uri.fromFile(new File(path));
        String[] testa={album};
        //tx1.setText(title);
        /*
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"The URI Does not work", Toast.LENGTH_LONG).show();
        }
        */
        String[] proj1 = {MediaStore.Audio.Media._ID,MediaStore.Audio.AlbumColumns.ALBUM,MediaStore.Audio.AlbumColumns.ALBUM_ART};
        cursor1 = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,proj1,MediaStore.Audio.AlbumColumns.ALBUM+"=?",testa,null);
        cursor1.moveToFirst();
        String al_art = cursor1.getString(cursor1.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART));
        edi4= (ImageView) findViewById(R.id.imageView);
        if(al_art!=null) {
            Uri picuri = Uri.fromFile(new File(al_art));
            edi4.setImageURI(picuri);
        }
        edi5 = (TextView) findViewById(R.id.textView5);
        if(artist!=null) {
            edi5.setText("ARTIST :" + artist);
        }else {
            edi5.setText("ARTIST : NOT AVAILABLE");
        }
        edi6 = (TextView) findViewById(R.id.textView6);
        if(d!=null) {
            double time = Double.parseDouble(d);
            double times= time/1000;
            int min =(int) times/60;
            int sec =(int) times%60;
            String k=String.valueOf(min)+" min"+String.valueOf(sec)+" sec";
            edi6.setText("DURATION :"+k);
        }else {
            edi6.setText("DURATION : NOT AVAILABLE");
        }
        edi7 = (TextView) findViewById(R.id.textView7);
        if(year!=null) {
            edi7.setText("YEAR :" + year);
        }else {
            edi7.setText("YEAR : NOT AVAILABLE");
        }
        edi8= (TextView) findViewById(R.id.textView8);
        if(album!=null) {
            edi8.setText("album :" + album);
        }else{
            edi8.setText("ALBUM : NOT AVAILABLE");
        }
        edi1=(TextView) findViewById(R.id.textView1);
        edi2=(TextView) findViewById(R.id.textView2);
        duration=(TextView) findViewById(R.id.textView3);
        edi3=(TextView) findViewById(R.id.textView4);
        seek_bar=(SeekBar) findViewById(R.id.seekBar);
        play=(Button) findViewById(R.id.button);
        paused=(Button) findViewById(R.id.button2);
        restart=(Button) findViewById(R.id.button3);
        play.setOnClickListener(this);
        paused.setOnClickListener(this);
        restart.setOnClickListener(this);
        seek_bar.setOnSeekBarChangeListener(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        edi3.setText("Now Playing :"+title);


        try {
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"The URI Does not work", Toast.LENGTH_LONG).show();
        }
        if(savedInstanceState!=null){
            int position = savedInstanceState.getInt("Position");
            mediaPlayer.seekTo(position);
            starta();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.button){
            starta();
        }else if(view.getId()==R.id.button2){
            mediaPlayer.pause();
            duration.setText("Paused.....");
            edi2.setText("NO Action");

        }else if(view.getId()==R.id.button3){
            mediaPlayer.seekTo(0);
            starta();
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b){
            mediaPlayer.seekTo(i);
            starta();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void starta(){

        mediaPlayer.start();
        edi2.setText("Playing...");
        duration.setText("No Action");
        finalTime=mediaPlayer.getDuration();
        startTime=mediaPlayer.getCurrentPosition();
        if(oneTimeOnly==0){
            seek_bar.setMax((int) finalTime);
            oneTimeOnly=1;
        }
        seek_bar.setProgress((int) startTime);
        handler.postDelayed(UpdateSongTime,100);

    }
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime= mediaPlayer.getCurrentPosition();
            double timeremaining = finalTime-startTime;
            double seconds = timeremaining/1000;
            int minutes =(int) seconds/60;
            int sec =(int) seconds%60;
            edi1.setText(String.valueOf(minutes)+" min"+" "+String.valueOf(sec)+" sec left");
            seek_bar.setProgress((int) startTime);
            handler.postDelayed(this,100);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",mediaPlayer.getCurrentPosition());
        //mediaPlayer.pause();
    }
}