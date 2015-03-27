package com.example.johnschindler.backendproto;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.HashMap;

/**
 * Created by johnschindler on 2/28/15.
 */

@ParseClassName("Building")
public class Building extends ParseObject {

   private HashMap<String, Bitmap> imageMap = new HashMap<String, Bitmap>();
   private HashMap<String, String> urlMap = new HashMap<String, String>();
   private String floorInFocus;

    public Building() {
    }

   /* public void setURL(String url) {
        put("URL", url);
    }

    public String getURL() {
        return getString("URL");
    }

    public void setBuilding(String building) {
        put("building", building);
    }*/

    public HashMap<String, String> getURLMap(){
        if(urlMap == null)
            urlMap = new HashMap<>();

        return urlMap;
    }

    public HashMap<String, Bitmap> getImageMap(){
        if(imageMap == null)
            imageMap = new HashMap<>();

        return imageMap;
    }

    public void setFloorInFocus(String floor){
        floorInFocus = floor;
    }

    public String getFloorInFocus(){
        return floorInFocus;
    }

    public String getBuilding() {
        return getString("building");
    }

}
