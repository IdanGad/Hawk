package com.example.intromapsvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient; // Sec Test
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class visitorPage extends AppCompatActivity {

    final int UNIQUE_REQUEST_CODE = 1;
    Button btnShareLocation;
    TextView tvLatitude, tvLongitude; // Sec Test
    FusedLocationProviderClient fusedLocationProviderClient; //Sec Test


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_page);

        btnShareLocation = (Button) findViewById(R.id.btnShareLocation);

        // Sec Test
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        // init FusedLocationPS
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check permissions
                if (ActivityCompat.checkSelfPermission(visitorPage.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {// when permission granted
                    getLocation();
                } else // when denied
                {
                    ActivityCompat.requestPermissions(visitorPage.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            44);

                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // init location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        // init geocoder
                        Geocoder geocoder = new Geocoder(visitorPage.this,
                                Locale.getDefault());
                        // init address
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        // Set latitude
                        tvLatitude.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b></font>" +
                                addresses.get(0).getLatitude()));
                        // Set longitude
                        tvLongitude.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude:</b></font>" +
                                addresses.get(0).getLongitude()));
                        Double IGotLatitude = addresses.get(0).getLatitude(); // inserting lat into a var
                        Double IGotLongitude = addresses.get(0).getLongitude(); // inserting lon into a var
                        int counter  = 0;
                        for (Visitor v : Database.visitors) // searching the visitor that logged in
                        { // In the Login page i set his variable "loggedIn" to be true
                            if(v!=null && v.getLoggedIn()) // i he just logged in and he is not null
                            {
                                /*
                               v.setLatitude(IGotLatitude); // set his latitude
                               v.setLongitude(IGotLongitude); // and longitude
                               v.setLoggedIn(false); // make his "loggedIn" variable false again
                                Toast.makeText(visitorPage.this,
                                        "lat: "+v.getLatitude()+"\n"+"lon: "+v.getLongitude(), Toast.LENGTH_SHORT).show();
                               break; // get out of the loop
                                */

                                    v.setLatitude(IGotLatitude); // set his latitude
                                    v.setLongitude(IGotLongitude); // and longitude
                                    // need to wait 5 seconds

                                    Toast.makeText(visitorPage.this,
                                            "lat: "+v.getLatitude()+"\n"+"lon: "+v.getLongitude()+" "+counter, Toast.LENGTH_SHORT).show();
                                    counter++;




                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_page);

        btnShareLocation = (Button) findViewById(R.id.btnShareLocation);

        btnShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Before adding this I made sure I got permission in the manifest - user permission
                if(ContextCompat.checkSelfPermission(visitorPage.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED)
                {// send a request to access the user fine location
                    ActivityCompat.requestPermissions(visitorPage.this,
                            new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                            UNIQUE_REQUEST_CODE);
                }
                else
                {// this message will show if the user clicked twice on the button
                    Toast.makeText(visitorPage.this, "Permission granted! Thank you", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //need to delete this
        if (requestCode == UNIQUE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thank you! Permission granted", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(visitorPage.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage(" this permission is important").setTitle("Important:");

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(visitorPage.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    UNIQUE_REQUEST_CODE);
                        }
                    });

                    dialog.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(visitorPage.this, "Cannot be done", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
                else
                {
                    Toast.makeText(this, "Will never show this again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
     */



}