package com.example.johnschindler.backendproto;

import android.app.Activity;
import android.content.Context;
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
import java.util.Map;

/**
 * Created by johnschindler on 3/14/15.
 */
public class FloorPlanRepo {

    private static final String TAG = "*** FloorPlanRepo ***";
    private Building mBuilding;
    private String mCurrentFloor;
    private ImageView mView;
    private boolean showImage;
    private boolean firstRequest;

    public FloorPlanRepo(Building building, ImageView view){
        mBuilding = building;
        mView = view;
        firstRequest = true;
    }

    private void startDownload(String urlString, String floor, Context context){
        Log.d(TAG, "In downloadImage");
        mCurrentFloor = floor;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadImageTask().execute(urlString, floor);
        } else {
            Log.d(TAG, "Network not connected");
        }
    }

    public void downloadAndShowImage(String urlString, String floor, Context context) {
        showImage = true;
        startDownload(urlString, floor, context);
    }

    public void downloadImage(String urlString, String floor, Context context) {
        startDownload(urlString, floor, context);
    }

    public void getRemainingFloors(Context context){
        for(HashMap.Entry<String, String> entry : mBuilding.getURLMap().entrySet()){
            if(mBuilding.getImageMap().get(entry.getKey()) == null){
                downloadImage(entry.getValue(), entry.getKey(), context);
                while(mBuilding.getImageMap().get(entry.getKey()) == null){
                    //wait
                }
            }

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
                Log.d(TAG, "Got image");
                mBuilding.getImageMap().put(mCurrentFloor, result);
                if(showImage) {
                    mView.setImageBitmap(result);
                    showImage = false;
                }
            }
        }

    }

}
