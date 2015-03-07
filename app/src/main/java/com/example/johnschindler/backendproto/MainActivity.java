package com.example.johnschindler.backendproto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseImageView;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ParseImageView floorView;

    private static final String TAG = "*** DEBUG ***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(FloorPlan.class);
        Parse.initialize(this, "xTzPEGb9UXNKHH6lEphikPyDpfXeSinJ9HoIqODU", "tmEVaWNvPic1VQd2c69Zn0u6gieingOJcMIF6zrD");

        //privacy settings
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        floorView = (ParseImageView) findViewById(R.id.floor_view);

        ParseQuery<FloorPlan> query = ParseQuery.getQuery("FloorPlan");
        //query = query.whereEqualTo("floor", )

        //query.getInBackground("xBMhHgRa7v", new GetCallback<FloorPlan>() {
        query.getInBackground("xBMhHgRa7v", new GetCallback<FloorPlan>() {
            @Override
            public void done(FloorPlan parseObject, ParseException e) {
            @Override
            public void done(FloorPlan parseObject, ParseException e) {

            Log.d(TAG, "IN done");


                if(parseObject == null)
                    Log.d(TAG, "parseObjectNull");
                if(e != null)
                    Log.d(TAG, "parseException => " + e);


                ParseFile photo = parseObject.getParseFile("picture");

                if(photo == null)
                    Log.d(TAG, "photo is null");


                //ParseFile photo2 = parseObject.getPhoto();
                //if(photo2 == null)
                    //Log.d(TAG, "photo2 is null");


                if(photo != null) {
                    floorView.setParseFile(photo);
                    floorView.loadInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            //Log.d(TAG, "in Done for loadInBackground");
                            floorView.setVisibility(View.VISIBLE);


                            if(data != null)
                                Log.d(TAG, "Length => " + data.length);

                            if(e != null) {
                                Log.d(TAG,
                                        "exception: " + e.getMessage());

                                Log.d(TAG,
                                        "2 exception: " + e);
                                        //e.printStackTrace();
                            }

                            Log.d(TAG, "After");
                        }
                    });
                }

            }
        });
        }



/*ParseImageView imageView = (ParseImageView) findViewById(android.R.id.icon);
 // The placeholder will be used before and during the fetch, to be replaced by the fetched image
 // data.
 imageView.setPlaceholder(getResources().getDrawable(R.drawable.placeholder));
 imageView.setParseFile(file);
 imageView.loadInBackground(new GetDataCallback() {
   @Override
   public void done(byte[] data, ParseException e) {
     Log.i("ParseImageView",
         "Fetched! Data length: " + data.length + ", or exception: " + e.getMessage());
   }
 });*/






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
