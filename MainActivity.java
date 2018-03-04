package com.demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.rx.ObservableFactory;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity  extends AppCompatActivity{


   // compile 'io.nlopez.smartlocation:library:3.3.3'
   // compile 'io.nlopez.smartlocation:rx:3.3.3'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Observable<Location> locationObservable = ObservableFactory.from(SmartLocation.with(this).location());
        locationObservable.subscribe(new Observer<Location>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Location location) {

                Log.d("TAGS",""+ location.getLatitude() );
                Log.d("TAGS",""+ location.getLongitude() );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

    });
    }



//for demo test
    private Bitmap getClusteredLabel(Integer count, Context ctx) {
        Resources r = ctx.getResources();
        Bitmap res = BitmapFactory.decodeResource(r, R.drawable.circle);
        res = res.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(res);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.RED);
        float density = getResources().getDisplayMetrics().density;
        textPaint.setTextSize(16 * density);

        c.drawText(String.valueOf(count.toString()), res.getWidth() / 2, res.getHeight() / 2 + textPaint.getTextSize() / 3, textPaint);

        return res;
    }
}
