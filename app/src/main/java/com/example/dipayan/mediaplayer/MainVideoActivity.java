package com.example.dipayan.mediaplayer;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainVideoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView t1,t2;
    Cursor cursor ;
    SimpleCursorAdapter mcurser;
    ListView listView;
    Layout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        //This is the colums to return from the central database.
        String[] proj = {MediaStore.Video.VideoColumns._ID,MediaStore.MediaColumns.TITLE,MediaStore.MediaColumns.DATA};
        //IS_MUSCI is true for non zero values.
        //bugubugi is passed as a where by arguments. It is replaced in place of ?.
        //String[] bugibugi = {"0"};
        //We store the returned stuff as a curser.
        cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,proj,null,null,null);
        //Table columns that will be maped to the ListView layput.
        String[] mWordListColumns= {MediaStore.MediaColumns.TITLE};
        //ListView layput mapping.
        int[] mWordItems = {R.id.textView3};
        //Curser adapter.
        mcurser = new SimpleCursorAdapter(this,R.layout.listview,cursor,mWordListColumns,mWordItems,0);
        //Standard listview stuff.
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mcurser);
        listView.setClickable(true);
        listView.setOnItemClickListener(this);








    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tt1= (TextView) view.findViewById(R.id.textView3);
        String title = tt1.getText().toString();
        Intent intent = new Intent(this,VideoActivity.class);
        intent.putExtra("key1",title);
        startActivity(intent);


    }
}