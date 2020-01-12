package com.unittest.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class SlidingOpenListener implements View.OnTouchListener {

    private int initLeftTopView, windowwidth,crossoverMidpt;

    SlidingOpenListener(int initLeftTopView,int windowwidth,int crossoverMidpt){
        this.initLeftTopView = initLeftTopView;
        this.windowwidth = windowwidth;
        this.crossoverMidpt = crossoverMidpt;

    }

    private int _xDelta;

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // _xDelta record how far inside the view we have touched. These
                // values are used to compute new margins when the view is moved.
                _xDelta = X - view.getLeft();
                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams lpUp = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                if (view.getLeft() < crossoverMidpt){
                    lpUp.leftMargin = initLeftTopView - view.getWidth();
                    lpUp.rightMargin = view.getWidth() - lpUp.leftMargin - windowwidth;
                    Log.d("sdf", "onTouch: open");
                }
                /*else if (view.getLeft()>initLeftTopView){
                    return true;
                }*/
                else  {
                    lpUp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    Log.d("sdf", "onTouch: closed");
                }
                view.setLayoutParams(lpUp);

                PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
                PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
                PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
                PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);


                final Animator collapseExpandAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhLeft, pvhTop,
                        pvhRight, pvhBottom);
                collapseExpandAnim.setupStartValues();

                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        collapseExpandAnim.setupEndValues();
                        collapseExpandAnim.start();
                        return false;
                    }
                });
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // Do nothing
                break;
            case MotionEvent.ACTION_MOVE:
                int leftMargin = X - _xDelta;
                if (leftMargin >= initLeftTopView) return true;
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                // Image is centered to start, but we need to unhitch it to move it around.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                }
                else {
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                }
                lp.leftMargin = leftMargin;
                lp.rightMargin = view.getWidth() - lp.leftMargin - windowwidth;
                view.setLayoutParams(lp);
                break;
        }
        return true;
    }
}
