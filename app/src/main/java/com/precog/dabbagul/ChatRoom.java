package com.precog.dabbagul;

import android.util.Log;

public class ChatRoom {

    private String id1;
    private String id;
    private String id2;
    private String myID;
    private String myName;
    private String myDP;
    private String name1;
    private String name2;
    private String dp1;
    private String dp2;
    private int oneortwo;

//    public ChatRoom(String id, String id1, String id2) {
//        this.id1 = id1;
//        this.id2 = id2;
//        this.id = id;
//        myID = BaseActivity.myProfileObj.email;
//    }

    public ChatRoom(String id, String id1, String id2, String name1, String name2, String dp1, String dp2) {
        this.id1 = id1;
        this.id = id;
        this.id2 = id2;
        this.name1 = name1;
        this.name2 = name2;
        this.dp1 = dp1;
        this.dp2 = dp2;
        Log.e("CHECK", "ChatRoom: "+BaseActivity.myProfileObj);
        myID = BaseActivity.myProfileObj.email;
        myName = BaseActivity.myProfileObj.name;
        myDP = BaseActivity.myProfileObj.dp;
        if(myID.equals(id1)) {
            oneortwo = 1;
        } else if(myID.equals(id2)) {
            oneortwo = 2;
        } else {
            oneortwo = -1;
        }
    }

    public String getOtherName() {
        if(oneortwo==1) {
            return name2;
        } else {
            return name1;
        }
    }

    public String getOtherID() {
        if(oneortwo==1) {
            return id2;
        } else {
            return id1;
        }
    }

    public String getOtherDP() {
        if(oneortwo==1) {
            return dp2;
        } else {
            return dp1;
        }
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setDp1(String dp1) {
        this.dp1 = dp1;
    }

    public void setDp2(String dp2) {
        this.dp2 = dp2;
    }

    public String getId1() {
        return id1;
    }

    public String getId() {
        return id;
    }

    public String getId2() {
        return id2;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getDp1() {
        return dp1;
    }

    public String getDp2() {
        return dp2;
    }
}