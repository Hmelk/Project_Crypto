package com.grove.project_crypto.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.grove.project_crypto.CryptoHelper.Encryptor;
import com.grove.project_crypto.Manifest;
import com.grove.project_crypto.MyLocation;
import com.grove.project_crypto.R;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

public class TestedActivity extends AppCompatActivity {

    Button b1, b2;
    TextView tv1;
    EditText ed1;
    int i= 0;
//    private LocationManager locationManager;
//    StringBuilder sbGPS = new StringBuilder();
//    StringBuilder sbNet = new StringBuilder();

    ImageView im1, im2;

    MyLocation myLocation;
    public static final int RequestPermissionCode = 1;

//
//    private static SecureRandom random = new SecureRandom();
//    private String latitude;
//    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tested);
        b1 = findViewById(R.id.enc);
        b2 = findViewById(R.id.dec);
        tv1 = findViewById(R.id.tv1);
        ed1 = findViewById(R.id.ed1);
//        latitude = "";
//        longitude = "";

        im1 = findViewById(R.id.imageView5);
        im2 = findViewById(R.id.imageView6);

        myLocation = new MyLocation(this);

//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Objects.requireNonNull(locationManager).requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 3, this);
//        }
//
////        MyLocation.SetUpLocationListener(TestedActivity.this);
//
//        final Encryptor encryptor = new Encryptor();
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                public void EnableRuntimePermission(){
                EnableRuntimePermission();
                tv1.setText("Begin");
//                int i = 0;

                    myThread.run();


//                while(myLocation.getLocation() == null) {
//                    i++;
//                    tv1.setText("next"+ i);
//                    if (myLocation.getLocation() != null)
//                    tv1.setText(myLocation.getLocation().getLatitude() + "");
//
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                MyLocation location = new MyLocation(TestedActivity.this);
//                showLocation(location.getLocation());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    Thread myThread = new Thread(
            new Runnable() {
                public void run() {
                    while(myLocation.getLocation() == null) {
                        i++;
                        tv1.setText("next" + i);
                        if (myLocation.getLocation() != null) {
                            tv1.setText(myLocation.getLocation().getLatitude() + "");
                            isClose();
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    void isClose(){
//        if (myThread != null) {
//            Thread dummy = myThread;
//            myThread = null;
//            dummy.interrupt();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(TestedActivity.this, "Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(TestedActivity.this, "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
        public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(TestedActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {

            Toast.makeText(TestedActivity.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(TestedActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

        }
    }
}


/*    public byte[] getByteArrayfromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public Bitmap getBitmapfromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }


    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tv1.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tv1.setText(formatLocation(location));
        }
    }*/

//    @SuppressLint("DefaultLocale")
//    private String formatLocation(Location location) {
//        if (location == null)
//            return "";
//        return String.format(
//                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
//                location.getLatitude(), location.getLongitude(), new Date(
//                        location.getTime()));
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        latitude = String.valueOf(location.getLatitude());
//        longitude = String.valueOf(location.getLongitude());
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//}



/*    public  static final int RequestPermissionCode  = 1 ;
    Button buttonEnable, buttonGet ;
    TextView textViewLongitude, textViewLatitude ;
    Context context;
    Intent intent1 ;
    Location location;
    LocationManager locationManager ;
    boolean GpsStatus = false ;
    Criteria criteria ;
    String Holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tested);

        EnableRuntimePermission();

        buttonEnable = (Button)findViewById(R.id.buttonEd);
        buttonGet = (Button)findViewById(R.id.button2Ed);

        textViewLongitude = (TextView)findViewById(R.id.textView);
        textViewLatitude = (TextView)findViewById(R.id.textView2);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        Holder = locationManager.getBestProvider(criteria, false);

        context = getApplicationContext();

        CheckGpsStatus();

        buttonEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent1);


            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckGpsStatus();

                if(GpsStatus == true) {
                    if (Holder != null) {
                        if (ActivityCompat.checkSelfPermission(
                                TestedActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                &&
                                ActivityCompat.checkSelfPermission(TestedActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        location = locationManager.getLastKnownLocation(Holder);
                        locationManager.requestLocationUpdates(Holder, 0, 1, TestedActivity.this);
                    }
                }else {

                    Toast.makeText(TestedActivity.this, "Please Enable GPS First", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

        @Override
        public void onLocationChanged(Location location) {

            textViewLongitude.setText("Longitude:" + location.getLongitude());
            textViewLatitude.setText("Latitude:" + location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        public void CheckGpsStatus(){

            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        }

        public void EnableRuntimePermission(){

            if (ActivityCompat.shouldShowRequestPermissionRationale(TestedActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {

                Toast.makeText(TestedActivity.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

            } else {

                ActivityCompat.requestPermissions(TestedActivity.this,new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

            }
        }

        @Override
        public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

            switch (RC) {

                case RequestPermissionCode:

                    if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(TestedActivity.this,"Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(TestedActivity.this,"Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

                    }
                    break;
            }
        }


}*/



