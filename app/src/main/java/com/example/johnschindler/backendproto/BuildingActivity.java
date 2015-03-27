package com.example.johnschindler.backendproto;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BuildingActivity extends ActionBarActivity {

    private Building mBuilding;
    private static final String TAG = "*** BuildingActivity ***";
    private String mRequestString;
    private String mRequestBuilding;
    private String mRequestFloor;
    private String mCurrentDownloadFloor;
    private HashMap<String, String> mURLMap;
    private HashMap<String, Bitmap> mImages;
    ImageView mImageView;

    boolean firstImageDownloaded;
    private boolean[] completedImages;
    private boolean parseQuerySuccesfull;
    private boolean allFloorsDownloaded;

    private FloorPlanRepo mFloorPlanRepo;

    private HashMap<String, HashMap<String, String>> nested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        //Intent int = new Intent(Service)
        mImages = new HashMap<String, Bitmap>();
        mURLMap = new HashMap<String, String>();


        mImageView = (ImageView) findViewById(R.id.image);
        mRequestString = getIntent().getStringExtra("building");
        String[] splitRequest = mRequestString.split("_");

        //add defensive code in case bad request passed making null error possible?
        mRequestBuilding = splitRequest[0];
        mRequestFloor = splitRequest[1];

        mCurrentDownloadFloor = splitRequest[1];
       //mFirstRequest = splitRequest[1];
        Log.d(TAG, mRequestBuilding);

        //query Parse for urls
        ParseQuery<Building> query = ParseQuery.getQuery("Building");
        query = query.whereEqualTo("Building", mRequestBuilding);



        query.findInBackground(new FindCallback<Building>() {
            @Override
            public void done(List<Building> buildings, ParseException e) {
                if (buildings != null) {
                    Log.d(TAG, "Parse Query finished");
                    parseQuerySuccesfull = true;
                    mBuilding = buildings.get(0);
                    //Log.d(TAG, mBuilding.getURL());
                    //parseURLString(mBuilding.getURL());
                    List<String> urlList = new ArrayList<String>();
                    urlList = mBuilding.getList("urlList");
                    for(int i = 0; i < urlList.size(); i++) {
                        Log.d(TAG, urlList.get(i));
                        String[] floorAndURL = urlList.get(i).split("_");
                        mBuilding.getURLMap().put(floorAndURL[0], floorAndURL[1]);
                    }

                    mFloorPlanRepo = new FloorPlanRepo(mBuilding, mImageView);
                    mFloorPlanRepo.downloadAndShowImage(mBuilding.getURLMap().get(mRequestFloor), mRequestFloor, BuildingActivity.this);

                    //downloadImage(mURLMap.get(mRequestFloor), mRequestFloor);
                    if (buildings.size() > 1)
                        Log.d(TAG, "More than one match for Query!");
                } else {
                    Log.d(TAG, "ParseException => " + e);
                }
            }
        });*/
    }


    private void downloadImage(String urlString, String floor) {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadImageTask().execute(urlString, floor);
        } else {
            Log.d(TAG, "Network not connected");
        }
    }

    //INNER CLASS FOR ASYNC TASK:
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... params) {

            Bitmap returnImage = null;
            // params comes from the execute() call: params[0] is the urlString.
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }//end try catch block

            HttpURLConnection urlConnection = null;
            if (url != null) {
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }//end try catch block
            }//end if

            if (urlConnection != null) {
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    returnImage = BitmapFactory.decodeStream(in); //note, this is not a return statement…the variable
                    //is named ‘returnImage’
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }//end try catch finally block
            }//end if
            return returnImage;
        }//end doInBackground method

        protected void onPostExecute(Bitmap result) {

            if (result != null) {
                firstImageDownloaded = true;
                mImages.put(mCurrentDownloadFloor, result);
                completedImages[Integer.parseInt(mCurrentDownloadFloor)] = true;
                if (mCurrentDownloadFloor.equals(mRequestFloor))
                    mImageView.setImageBitmap(result);
            }
        }


    }

    @Override
    protected void onResume() {
        //repository.open();
        //datasource.open();
        super.onResume();
        if(mBuilding == null)
            Log.d(TAG, "mBuilding is null");
        //if(mBuilding.getImageMap().get(mRequestFloor) == null)
            //Log.d(TAG, "bitmap is null");
        //if(mBuilding != null)
            //mImageView.setImageBitmap(mBuilding.getImageMap().get(mRequestFloor));

    }

    @Override
    protected void onPause() {
        //repository.close();
        //datasource.close();
        super.onPause();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_building, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
