package com.precog.dabbagul;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {
    public String description;
    public long time;
    public String name;
    public String ownerID;
    public String ownerName;
    public String ownerDp;
    public String foodPhoto;
    public List<String> tags;

    public Food(String name, String description, long time, String foodPhoto, List<String> tags) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.foodPhoto = foodPhoto;
        this.tags = tags;
        ownerID = BaseActivity.myProfileObj.email;
        ownerDp = BaseActivity.myProfileObj.dp;
        ownerName = BaseActivity.myProfileObj.name;
    }
}
