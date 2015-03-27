package com.example.johnschindler.backendproto;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by johnschindler on 3/17/15.
 */
@ParseClassName("BuildingJSON")
public class BuildingJSON extends ParseObject {

    public BuildingJSON() {
    }

    public JSONObject getBuildingJSON() {return getJSONObject("Buildings");}


}
