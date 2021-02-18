package com.comrade.comrade.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.SessionManager.SessionManager;
import com.comrade.comrade.databinding.ActivityLocationBinding;
import com.comrade.comrade.utils.LocationService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends AppCompatActivity {


    private static final int REQUEST_PERMISSIONS = 100;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ActivityLocationBinding binding;
    public static Location loc;

    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude, longitude;
    Geocoder geocoder;
    QueryPreferences queryPreferences;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        queryPreferences = new QueryPreferences(this);

        sessionManager=new SessionManager(this);


        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();


        binding.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (canGetLocation()) {
                    //DO SOMETHING USEFUL HERE. ALL GPS PROVIDERS ARE CURRENTLY ENABLED

                    if (sessionManager.isLogin()){

                        startService();
                    }else {

                        Intent intent=new Intent(LocationActivity.this,SignUpChooserActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } else {
                    //SHOW OUR SETTINGS ALERT, AND LET THE USE TURN ON ALL THE GPS PROVIDERS
                    showSettingsAlert();
                }
            }
        });

        checkLocationPermission();
    }


    private void checkLocationPermission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((!ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        REQUEST_PERMISSIONS);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (requestCode == REQUEST_PERMISSIONS) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);


                Log.e("LoginActivity", "Address" + addresses.get(0).getAdminArea());
                Log.e("LoginActivity", "stateName" + stateName);
                Log.e("LoginActivity", "cityName" + cityName);


                Log.e("LoginActivity", "latitude" + latitude + "");
                Log.e("LoginActivity", "longitude" + longitude + "");

                queryPreferences.setLatLong(latitude, latitude);
                if (sessionManager.isLogin()){


                    startService();
                }else {

                    Intent intentx=new Intent(LocationActivity.this,SignUpChooserActivity.class);
                    startActivity(intentx);
                    finish();

                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intentService = new Intent(getApplicationContext(), LocationService.class);
            startService(intentService);
            registerReceiver(broadcastReceiver, new IntentFilter(LocationService.str_receiver));

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            unregisterReceiver(broadcastReceiver);
        }
    }

    private void startService() {
        Intent intent = new Intent(LocationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    public boolean canGetLocation() {
        boolean result = true;
        LocationManager lm;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();

            Log.e("locationActivity", "Location error : " + ex.getMessage());

        }

        try {
            networkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

            ex.printStackTrace();

            Log.e("locationActivity", "Location error : " + ex.getMessage());
        }

        return gpsEnabled && networkEnabled;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Location permission!");

        // Setting Dialog Message
        alertDialog.setMessage("Please grant location permission to find people near you. ");

        // On pressing Settings button
        alertDialog.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        alertDialog.show();
    }

}