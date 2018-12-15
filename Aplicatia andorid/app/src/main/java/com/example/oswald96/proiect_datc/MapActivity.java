package com.example.oswald96.proiect_datc;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    private  static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    static int count = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermission();
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(60000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                moveCamera(new LatLng(45.733516, 21.241824), 16, count);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void getDeviceLocation()
    {
        Log.d(TAG, "getDeviceLocation: getting the current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "OnComplete: found location");
                            Location currentLocation= (Location)task.getResult();
                            mMap.setMinZoomPreference(15.0f);
                            mMap.setMaxZoomPreference(17.0f);
                            //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15);
                            moveCamera(new LatLng(45.733516, 21.241824), 16, 0);
                        }
                        else{
                            Log.d(TAG, "OnComplete: couldn't find location");
                            Toast.makeText(MapActivity.this, "unable to find location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation:" + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, int numar)
    {
        mMap.clear();
        Log.d(TAG, "moveCamera: moving the camera to lat: " +latLng.latitude+"and longitude: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        String snippet = new String();

        numar = count;
        if (numar%2 == 0)
        {
             snippet = "Tmp: 40°C  "  +
                       "  Hum: 60%";
        }
        else{
             snippet = "Tmp: 10°C  " +
                       "  Hum: 20%";
        }

        MarkerOptions optins = new MarkerOptions()
                .position(new LatLng(45.733516, 21.241824))
                .title("Senzor1")
                .snippet(snippet);
        mMap.addMarker(optins);

        MarkerOptions optins2 = new MarkerOptions()
                .position(new LatLng(45.733516, 21.243090))
                .title("Senzor2");
        mMap.addMarker(optins2);

        MarkerOptions optins3 = new MarkerOptions()
                .position(new LatLng(45.733538, 21.244506))
                .title("Senzor3");
        mMap.addMarker(optins3);

        MarkerOptions optins4 = new MarkerOptions()
                .position(new LatLng(45.732060, 21.241684))
                .title("Senzor4");
        mMap.addMarker(optins4);

        MarkerOptions optins5 = new MarkerOptions()
                .position(new LatLng(45.732037, 21.243143))
                .title("Senzor5");
        mMap.addMarker(optins5);

        MarkerOptions optins6 = new MarkerOptions()
                .position(new LatLng(45.731962, 21.244463))
                .title("Senzor6");
        mMap.addMarker(optins6);
/*
        PolygonOptions rectOptions1 = new PolygonOptions()
                .add(new LatLng(45.734146, 21.241184),
                        new LatLng(45.734146, 21.241184),
                        new LatLng(45.734146, 21.242464),
                        new LatLng(45.732886, 21.242464),
                        new LatLng(45.732886, 21.241184))
                .strokeColor(Color.RED)
                .fillColor(Color.RED);
        Polygon polygon1 = mMap.addPolygon(rectOptions1);

        PolygonOptions rectOptions2 = new PolygonOptions()
                .add(new LatLng(45.733912, 21.242574),
                        new LatLng(45.733912, 21.242574),
                        new LatLng(45.733912, 21.243764),
                        new LatLng(45.732886, 21.243764),
                        new LatLng(45.732886, 21.242574))
                .strokeColor(Color.TRANSPARENT)
                .fillColor(Color.TRANSPARENT);
        Polygon polygon2 = mMap.addPolygon(rectOptions2);*/

        List<WeightedLatLng> wDat = new ArrayList<>();
        List<LatLng> list = new ArrayList<LatLng>();

        //list.add(new LatLng(45.7341, 21.2411));
        //list.add(new LatLng(45.7341, 21.2411));
        //list.add(new LatLng(45.7341, 21.2424));
        //list.add(new LatLng(45.7328, 21.2424));
        list.add(new LatLng(45.733516, 21.241824));
        list.add(new LatLng(45.733516, 21.243090));

        wDat.add(new WeightedLatLng(new LatLng(45.733516, 21.241824), 100));
        wDat.add(new WeightedLatLng(new LatLng(45.733516, 21.243090), 20));
        wDat.add(new WeightedLatLng(new LatLng(45.733538, 21.244506), 25));
        wDat.add(new WeightedLatLng(new LatLng(45.732060, 21.241684), 7));
        wDat.add(new WeightedLatLng(new LatLng(45.732037, 21.243143), 19));
        wDat.add(new WeightedLatLng(new LatLng(45.731962, 21.244463), 34));

        int[] colors = {
                Color.YELLOW,    // green(0-50)
                Color.rgb(255,165,0),    // yellow(51-100)
                Color.rgb(244, 131, 66), //Orange(101-150)
                Color.RED,              //red(151-200)
                Color.rgb(153,50,204), //dark orchid(201-300)
                Color.rgb(165,42,42) //brown(301-500)
        };

        float[] startPoints = {
                0.1F, 0.15F, 0.2F, 0.25F, 0.3F, 1.0F
        };

        Gradient gradient1 = new Gradient(colors, startPoints);


        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .weightedData(wDat)
                .gradient(gradient1)
                .build();


        mProvider.setRadius(100);
        TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 ){
                    for(int i = 0; i<grantResults.length;i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission accepted");
                    mLocationPermissionsGranted = true;
                    // init map
                    initMap();
                }
            }
        }
    }
}
