package com.sebchlan.picassocompat.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sebchlan.picassocompat.PicassoBridge;
import com.sebchlan.picassocompat.PicassoCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.imageview);

        Context context = this;

        PicassoBridge.init(this)
                .load("https://www.w3schools.com/w3css/img_lights.jpg")
                .into(img);

        PicassoCompat init = PicassoBridge.init(context);

        PicassoCompat.Builder builder = PicassoBridge.builder(context);
    }
}
