package com.unittest.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class AnimActivity extends AppCompatActivity{

    View bottomView;
    View topView;
    int initLeftTopView;
    int crossoverMidpt;

    int windowwidth; // Actually the width of the RelativeLayout.
    int windowheight; // Actually the height of the RelativeLayout.
    View mRrootLayout;
    int initLeftMargin,initRightMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mRrootLayout = findViewById(R.id.root);
        bottomView = findViewById(R.id.bottomView);
        topView = findViewById(R.id.topView);


        topView.post(new Runnable() {
            @Override
            public void run() {
                initLeftTopView = topView.getLeft();
                windowwidth = mRrootLayout.getWidth();
                windowheight = mRrootLayout.getHeight();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) topView
                        .getLayoutParams();
                initLeftMargin = lp.leftMargin;
                initRightMargin = lp.rightMargin;

                crossoverMidpt = initLeftTopView - (topView.getWidth()/2);
                topView.setOnTouchListener(new SlidingOpenListener(initLeftTopView,windowwidth,crossoverMidpt));
            }
        });
    }






    /*
    * reference https://stackoverflow.com/a/46922923/2700574
    *
    * */
}
