package edu.ggc.jory.f3c;

/**
 * Created by Jory on 3/11/17.
 */

import android.app.Activity;

import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.ggc.jory.f3c.model.ChargePoint;
import edu.ggc.jory.f3c.model.StationList;
import edu.ggc.jory.f3c.model.Summary;

public class MyAdapter extends ArrayAdapter<ChargePoint>
{
    TextToSpeech t1;

    private final Activity context;
    private List<ChargePoint> objects;

    public MyAdapter(Activity context, int resource, int viewID, List<ChargePoint> objects) {

        super(context, resource, viewID, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View view, ViewGroup parent)
    {



        LayoutInflater inflater = context.getLayoutInflater();
        final View layoutView = inflater.inflate(R.layout.list, null, true);
        t1=new TextToSpeech(layoutView.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });


        TextView nameLabel = (TextView) layoutView.findViewById(R.id.name);
        TextView availLabel = (TextView) layoutView.findViewById(R.id.available);
        TextView locLabel = (TextView) layoutView.findViewById(R.id.location);
        TextView timeLabel = (TextView) layoutView.findViewById(R.id.time);
        ChargePoint point = objects.get(position);


        nameLabel.setText(getName(point));
        locLabel.setText(getLocation(point));
        timeLabel.setText(point.getStationList().getTime());
        availLabel.setText(getAvailability(point));

        nameLabel.setContentDescription(getAvailability(point) + " chargers " + getDescription(point));
        availLabel.setContentDescription(getAvailability(point) + " chargers " + getDescription(point));
        locLabel.setContentDescription(getAvailability(point) + " chargers " + getDescription(point));
        timeLabel.setContentDescription(getAvailability(point) + " chargers " + getDescription(point));

        setColor(nameLabel,availLabel,point);

        setTextToSpeech(nameLabel,layoutView);
        setTextToSpeech(availLabel,layoutView);
        setTextToSpeech(locLabel,layoutView);
        setTextToSpeech(timeLabel,layoutView);

        return layoutView;
    }

    public void setColor(TextView name, TextView available,ChargePoint point)
    {
        name.setTextColor(Color.BLACK);

        if(getAvailabilityInt(point) == 0) {
            available.setTextColor(Color.BLACK);
        }
        else{
            available.setTextColor(Color.GREEN);

        }
    }

    public void setTextToSpeech(TextView v, final View f)
    {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(v.getContentDescription().toString(),TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(f.getContext(), v.getContentDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public String getName(ChargePoint point)
    {
        int  deviceNumber = (int) point.getStationList().getSummaries().get(0).getDeviceId();

        switch (deviceNumber) {
            case 122167: {
                return "B Building";
            }
            case 122169: {
                return "Res Life 1000";
            }
            case 122165: {
                return "Parking Deck";
            }
            case 122219: {
                return "I Building";
            }
            default: {
                return "Invalid Lot";
            }
        }

    }

    public String getLocation(ChargePoint point)
    {
        List<String> names = point.getStationList().getSummaries().get(0).getStationName();

        return names.get(0) + " / " + names.get(1);
    }


    public String getAvailability(ChargePoint point)
    {
        List<Summary> list = point.getStationList().getSummaries();

        return (list.get(0).getPortCount().getAvailable()) + " Available";
    }

    public int getAvailabilityInt(ChargePoint point)
    {
        List<Summary> list = point.getStationList().getSummaries();

        return (int) (list.get(0).getPortCount().getAvailable());
    }

    public String getDescription(ChargePoint point)
    {
        String description = point.getStationList().getSummaries().get(0).getDescription();
        return description;
    }

}


