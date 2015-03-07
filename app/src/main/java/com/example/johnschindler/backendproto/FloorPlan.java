package com.example.johnschindler.backendproto;

/**
 * Created by johnschindler on 2/22/15.
 */



//Building object will have arraylist of FloorPlan Objects

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("FloorPlan")
public class FloorPlan extends ParseObject {

    public FloorPlan(){
        //Default constructor needed
    }

    public String getBuilding() {
        return getString("title");
    }

    public void setBuilding(String title) {
        put("title", title);
    }

    public int getFloor(){
        return getInt("floor");
    }

    public void setFloor(int floor){
        put("floor", floor);
    }

    public ParseFile getPhoto() {
        return getParseFile("picture");
    }

    public void setPhoto(ParseFile file) {
        put("picture", file);
    }




}
