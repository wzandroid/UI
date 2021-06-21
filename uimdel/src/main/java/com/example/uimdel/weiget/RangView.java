package com.example.uimdel.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.uimdel.R;

public class RangView extends FrameLayout {
    ImageView top,middle,bottom;
    int topResId, middleResId, bottomResId;
    private int minHeight;
    public RangView(Context context) {
        this(context, null);
    }

    public RangView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        minHeight = getResources().getDimensionPixelOffset(R.dimen.rang_min_height);
        LayoutInflater.from(context).inflate(R.layout.view_rang_layout, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RangView);
        topResId = ta.getResourceId(R.styleable.RangView_top, 0);
        middleResId = ta.getResourceId(R.styleable.RangView_middle, 0);
        bottomResId = ta.getResourceId(R.styleable.RangView_bottom, 0);
        ta.recycle();
        init();
    }

    private void init(){
        top = findViewById(R.id.top);
        middle = findViewById(R.id.middle);
        bottom = findViewById(R.id.bottom);
        top.getContext().getResources().getColor(R.color.storage_pie_mask);
        if (topResId != 0){
            top.setBackgroundResource(topResId);
            top.setBackgroundTintMode(PorterDuff.Mode.DST);
        }
        if (middleResId != 0){
            middle.setBackgroundResource(middleResId);
            middle.setBackgroundTintMode(PorterDuff.Mode.DST);
        }
        if (bottomResId != 0){
            bottom.setBackgroundResource(bottomResId);
            bottom.setBackgroundTintMode(PorterDuff.Mode.DST);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize != 0){
            heightSize = Math.max(heightSize, minHeight);
        }
        int minHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, minHeightMeasureSpec);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void setTint(boolean isTouch){
        if (isTouch && top.getBackgroundTintMode() != PorterDuff.Mode.DST){
            top.setBackgroundTintMode(PorterDuff.Mode.DST);
            middle.setBackgroundTintMode(PorterDuff.Mode.DST);
            bottom.setBackgroundTintMode(PorterDuff.Mode.DST);
            return;
        }
        if (!isTouch && top.getBackgroundTintMode() != PorterDuff.Mode.SRC_ATOP){
            top.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            middle.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            bottom.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }
}
