package com.unittest.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class SlidingOpenListener implements View.OnTouchListener {

    private int initLeftTopView, parentWidth,crossoverMidpt;
    private OnScrollOpenListener listener;
    private int _xDelta;
    private int leftMostRange;
    private int viewWidth;

    public SlidingOpenListener(Context context,int initLeftTopView, int parentWidth, int viewWidth,
                               OnScrollOpenListener listener, int overlapWidthDefaultDp){
        this.initLeftTopView = initLeftTopView;
        this.parentWidth = parentWidth;
        this.listener = listener;
        this.viewWidth = viewWidth;
        this.leftMostRange = initLeftTopView - viewWidth + (int)(convertDpToPx(context, overlapWidthDefaultDp));
        this.crossoverMidpt = this.leftMostRange + ((initLeftTopView -this.leftMostRange) / 2);
    }


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
                    lpUp.leftMargin = this.leftMostRange;
                    lpUp.rightMargin = view.getWidth() - lpUp.leftMargin - parentWidth;
                }
                else  {
                    lpUp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                }
                view.setLayoutParams(lpUp);
                if (listener != null){
                    listener.onScrollChanged(view.getLeft() < crossoverMidpt ? 0: 100);
                }

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
                /*int rightMostRange = initLeftTopView;*/
                if (leftMargin < leftMostRange  ||  leftMargin >= initLeftTopView){
                    return true;
                }
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
                lp.rightMargin = view.getWidth() - lp.leftMargin - parentWidth;
                view.setLayoutParams(lp);
                if (listener != null){
                    int percent = ((leftMargin - leftMostRange)*100 ) / viewWidth;
                    listener.onScrollChanged(percent);
                }
                break;
        }
        return true;
    }

    public interface OnScrollOpenListener{
        void onScrollChanged(int percent);
    }

    public float convertDpToPx(Context context,float dip){
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        return px;
    }
}


/*
* Additional features
* 1. Overlapping Area
* 2.
*
* */