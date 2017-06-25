
package edu.ggc.jory.f3c.model;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;

public class ChargePoint implements Comparable<ChargePoint>{

    @SerializedName("station_list")
    @Expose
    private StationList stationList;

    public StationList getStationList() {
        return stationList;
    }

    public void setStationList(StationList stationList) {
        this.stationList = stationList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(@NonNull ChargePoint point) {

        return Integer.compare(this.getAvailability(),point.getAvailability());
    }

    public int getAvailability()
    {
        return (int) (this.getStationList().getSummaries().get(0).getPortCount().getAvailable());
    }
}
