package com.example.johnschindler.backendproto;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by johnschindler on 3/17/15.
 */

public class BuildingData {

    private static final String TAG = "*** BuildingData ***";
    private JSONObject mBuildingJSON;
    private boolean mImageMapsPopulated;
    private HashMap<String, HashMap<String, String>> mImageMaps;

    public BuildingData() {

        //query Parse for urls and load mImageMaps
        ParseQuery<ParseObject> query = ParseQuery.getQuery("BuildingJSON");
        query.getInBackground("9QZJnmZvsy", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject buildingJSON, ParseException parseException) {
                mImageMaps = new HashMap<>();

                if (parseException == null) {

                    mBuildingJSON = buildingJSON.getJSONObject("Buildings");
                    Iterator<String> iter = mBuildingJSON.keys();
                    while(iter.hasNext()){
                        String building = iter.next();
                        JSONObject buildingInfo = null;
                        try {
                            buildingInfo = mBuildingJSON.getJSONObject(building);
                            if(buildingInfo == null)
                                Log.d(TAG, "buildingInfo is null");
                            HashMap<String, String> buildingInfoMap = new HashMap<>();
                            Iterator<String> innerIter = buildingInfo.keys();
                            while(innerIter.hasNext()){
                                String key = innerIter.next();
                                buildingInfoMap.put(key, buildingInfo.getString(key));
                            }
                            mImageMaps.put(building, buildingInfoMap);

                        } catch (JSONException jsonException) {

                            jsonException.printStackTrace();
                        }
                    }
                    Log.d(TAG, "loaded map " + mImageMaps.toString());
                    mImageMapsPopulated = true;
                } else {
                    Log.d(TAG, "ParseException => " + parseException);
                }
            }
        });
    }

    public boolean imageMapsPopulated(){
        return mImageMapsPopulated;
    }

    public HashMap<String, HashMap<String, String>> getImageMap(){
        return mImageMaps != null ? mImageMaps : null;
    }

}
