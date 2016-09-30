package com.example.laganovskis.jsondatafromurl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView albumName;
    private ImageView albumUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumName = (TextView) findViewById(R.id.albumName);
        albumUrl = (ImageView) findViewById(R.id.albumUrl);

        GetJsonData getJsonData = new GetJsonData(albumName,albumUrl);
        getJsonData.execute("https://api.myjson.com/bins/oaxq");

    }


}
