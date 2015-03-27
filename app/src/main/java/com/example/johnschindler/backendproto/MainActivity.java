package com.example.johnschindler.backendproto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

    //private ParseImageView floorView;

    private static final String TAG = "*** MainActivity ***";
    private Button mGDCButton;
    private Button mRLMButton;

    public enum BuildingId {RLM, GDC}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mock for search, two buttons
        mGDCButton = (Button) findViewById(R.id.GDC);
        mRLMButton = (Button) findViewById(R.id.RLM);
        mRLMButton.setEnabled(true);
        mGDCButton.setEnabled(true);
        Intent intent = new Intent(this, BuildingActivity.class);
        //Using String not enum rn
        //mRLMButton.setOnClickListener(new ButtonClickListener(BuildingId.RLM, intent));
        //mGDCButton.setOnClickListener(new ButtonClickListener(BuildingId.GDC, intent));
        mRLMButton.setOnClickListener(new ButtonClickListener("WRW_01", intent));
        mGDCButton.setOnClickListener(new ButtonClickListener("WRW_01", intent));


        //ParseObject.registerSubclass(Building.class);
        //ParseObject.registerSubclass(BuildingJSON.class);
        Parse.initialize(this, "xTzPEGb9UXNKHH6lEphikPyDpfXeSinJ9HoIqODU", "tmEVaWNvPic1VQd2c69Zn0u6gieingOJcMIF6zrD");

        //privacy settings
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        BuildingData bd = new BuildingData();

    }

    private class ButtonClickListener implements View.OnClickListener {


        //BuildingId building;
        String building;
        Intent intent;

        //Using string for now
        public ButtonClickListener(String building, Intent intent) {
            //public ButtonClickListener(String, Intent intent){
            this.building = building;
            this.intent = intent;
        }

        public void onClick(View view) {
            this.intent.putExtra("building", this.building);
            startActivity(this.intent);
        }

    }
}

    /*private void prepDownloadImageActivity(int winner, String message){

        Intent intent = new Intent(this, BuildingActivity.class);
        intent.putExtra("Building", this.chosenBuilding);
        intent.putExtra("message", message);
        startActivity(intent);

    }*/








