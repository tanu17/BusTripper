package com.bustripper.bustripper.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wayne on 25/3/2018.
 */

public class BusService {
    private String busStopName;
    private String busStopCode;
    private ArrayList<String> arrivalTimes;

    public BusService(String busStopName, String busStopCode, ArrayList<String> arrivalTimes){
        this.busStopName=busStopName;
        this.busStopCode=busStopCode;
        this.arrivalTimes=arrivalTimes;
    }

    public String getBusStopName() {
        return busStopName;
    }
    public String getBusStopCode() {
        return busStopCode;
    }
    public ArrayList<String> getArrivalTimes() {
        return arrivalTimes;
    }
}
