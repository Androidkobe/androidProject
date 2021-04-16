package com.example.demo.sundu.touchevent;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        LinearLayout linearLayout = findViewById(R.id.bottom_group);
        VideoMoveSplashAdMoveLineView videoMoveSplashAdMoveLineView = findViewById(R.id.move_line);
        List<Rect> rects = new ArrayList<>();
        Rect rect = new Rect();
        linearLayout.getGlobalVisibleRect(rect);
        videoMoveSplashAdMoveLineView.setIgnoreClickRect(rects);
    }
}