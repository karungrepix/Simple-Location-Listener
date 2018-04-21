package demo.com;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import im.delight.android.location.SimpleLocation;

public class WithoutMapCurrentLocation extends AppCompatActivity {


/*

    android {
        compileSdkVersion 27
        defaultConfig {
            applicationId "demo.com"
            minSdkVersion 21
            targetSdkVersion 27
            versionCode 1
            versionName "1.0"
            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            targetCompatibility 1.8
            sourceCompatibility 1.8
        }
    }

    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }

    dependencies {
        implementation 'com.android.support:appcompat-v7:27.1.1'
        implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
        implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.3@aar'
        implementation 'com.github.delight-im:Android-SimpleLocation:v1.0.1'



    }
*/


    private SimpleLocation location;
    private boolean isCheckPermisstion=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).doOnNext(this::accept).subscribe();


    }

    private void accept(Boolean granted) {
        if (granted) {
            runTimePermissions();
            isCheckPermisstion = true;
        } else {
            Toast.makeText(getApplicationContext(), "Permission has been denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void runTimePermissions(){

        location=  new SimpleLocation(this, false, false, 1000, false);
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        Toast.makeText(getApplicationContext(),"Current "+latitude +"," +longitude,Toast.LENGTH_SHORT).show();


        location.setListener(() -> {
            final double latitude1 = location.getLatitude();
            final double longitude1 = location.getLongitude();
            Toast.makeText(getApplicationContext(),"Updated "+ latitude1 +"," + longitude1,Toast.LENGTH_SHORT).show();
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(isCheckPermisstion) {
            location.beginUpdates();
        }
    }

    @Override
    protected void onPause() {
        if(isCheckPermisstion) {
            location.endUpdates();
        }
        super.onPause();
    }


}
