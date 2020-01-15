package com.unittest.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnimActivity extends AppCompatActivity{

    View bottomView;
    View topView;
    int initLeftTopView;
    int crossoverMidpt;

    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    View mRrootLayout;
    int initLeftMargin,initRightMargin;
    TextView alphaText,swipeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mRrootLayout = findViewById(R.id.root);
        bottomView = findViewById(R.id.bottomView);
        topView = findViewById(R.id.topView);

        alphaText = findViewById(R.id.tv_view_alpha);
        swipeLeft = findViewById(R.id.tv_swipe_left);


        topView.post(new Runnable() {
            @Override
            public void run() {
                topView.setOnTouchListener(new SlidingOpenListener(AnimActivity.this, topView.getLeft(), mRrootLayout.getWidth()
                        , topView.getWidth(),
                        new SlidingOpenListener.OnScrollOpenListener() {
                    @Override
                    public void onScrollChanged(int percent) {
                        alphaText.setText(String.valueOf(percent));
                        swipeLeft.setAlpha((float) percent / 100);
                    }
                },80));
            }
        });


    }






    /*
    * reference https://stackoverflow.com/a/46922923/2700574
    *
    * */
}
