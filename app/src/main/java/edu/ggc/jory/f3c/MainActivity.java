package edu.ggc.jory.f3c;
/**
 * Created by Jory on 3/11/17.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.ggc.jory.f3c.model.ChargePoint;
import edu.ggc.jory.f3c.utils.HttpHelper;
import edu.ggc.jory.f3c.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity
{


    static final String TAG = "Look At Me!";

    ListView chargers;
    MyAdapter listAdapter;
    ArrayList<ChargePoint> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fetchData();
            }
        });

        chargers = (ListView) findViewById(R.id.displayList);
        listAdapter = new MyAdapter(this, R.layout.list,
                R.id.name, locations);

        chargers.setAdapter(listAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about) {
            Toast.makeText(this, "Created by Robert Alexander on 3/12/2017 for itec 4550", Toast.LENGTH_SHORT).show();
        }
            return true;
    }



    class MyTask extends AsyncTask<Void, Void, Void>
    {

        String[] stations = {"https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9820,\"ne_lon\":-84.0031,\"sw_lat\":33.9811,\"sw_lon\":-84.0048}}",
                "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9805,\"ne_lon\":-84.0063,\"sw_lat\":33.9795,\"sw_lon\":-84.0080}}",
                "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9819,\"ne_lon\":-83.9991,\"sw_lat\":33.9809,\"sw_lon\":-84.0008}}",
                "https://mc.chargepoint.com/map-prod/get?{\"station_list\":{\"ne_lat\":33.9779,\"ne_lon\":-84.0018,\"sw_lat\":33.9770,\"sw_lon\":-84.0035}}"
        };

        @Override
        protected Void doInBackground(Void... params) {

            locations.clear();
            getChargePoints();
            Collections.sort(locations);
            Collections.reverse(locations);


            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            listAdapter.notifyDataSetChanged();
        }

        public ArrayList<ChargePoint> getChargePoints()
        {
            try
            {
                Gson gson = new Gson();

                for(String x : stations)
                {
                    String response = HttpHelper.downloadUrl(x);
                    ChargePoint stationPoint = gson.fromJson(response, ChargePoint.class);
                    locations.add(stationPoint);
                }

                Collections.sort(locations);
                Collections.reverse(locations);
                return (locations);


            } catch (IOException e)
            {
                return null;
            }
        }
    }



    public void fetchData()
    {

        if( ! NetworkHelper.hasNetworkAccess(this))
        {
            Toast errorToast = Toast.makeText(
                    this,
                    "Check network connection and try again.",
                    Toast.LENGTH_SHORT);
            errorToast.show();
        }

        new MyTask().execute();
    }





}