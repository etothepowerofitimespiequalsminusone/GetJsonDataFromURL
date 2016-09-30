package com.example.laganovskis.jsondatafromurl;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Laganovskis on 9/30/2016.
 */
public class GetJsonData extends AsyncTask<String,String,String> {

    private final static String LOG_TAG = GetJsonData.class.getSimpleName();
    private TextView albumName;
    private ImageView albumUrl;
    private String albumurl;
    private String albumname;
    ProgressDialog pd;

    public GetJsonData(TextView albumName, ImageView albumUrl) {
        this.albumName = albumName;
        this.albumUrl = albumUrl;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    public ImageView getAlbumUrl() {
        return albumUrl;
    }

    public TextView getAlbumName() {
        return albumName;
    }

    @Override
    protected String doInBackground(String... strings) {
        //this is a basic structure to get connection to url and then download data in buffer
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String data = "";

            while ((data = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(data + "\n");
            }

            return stringBuffer.toString();


        }catch (MalformedURLException e)
        {
            Log.e(LOG_TAG,"url exeception");
            e.printStackTrace();
        }catch (IOException e)
        {
            Log.e(LOG_TAG,"ui");
            e.printStackTrace();
        }    finally {
            if(httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
            try{
                if(bufferedReader != null)
                {
                    bufferedReader.close();
                }
            }catch (IOException e)
            {
                Log.e(LOG_TAG,"error closing the bufferedReader");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //code to parse JSON goes here
        try {
            JSONObject jsonObject = new JSONObject(s);
//            JSONObject albums = jsonObject.getJSONObject("albums");
            JSONArray albums = jsonObject.optJSONArray("albums");
            for(int i=0;i<albums.length();i++)
            {
                JSONObject album = albums.getJSONObject(i);

                albumname = album.getString("albumname");
                albumurl = album.getString("albumurl");
            }
        }catch (JSONException e)
        {
            Log.e(LOG_TAG,"json object error");
        }


        /**********************************/
        albumName.setText(albumname);
        Picasso.with(albumUrl.getContext()).load(albumurl).placeholder(R.drawable.placeholder).into(albumUrl);
//       albumName.setText(s);
    }
}
