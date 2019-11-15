package com.precog.dabbagul;
import com.google.firebase.Timestamp;

import java.util.Date;

public class Request {
    public String id;
    public String sender_name;
    public String sender_email;
    public String receiver_email;
    public String sender_food;
    public String sender_dp;
    public String food_dp;
    public long date_generated;
    public double status;
    public double distance; //in meters
}