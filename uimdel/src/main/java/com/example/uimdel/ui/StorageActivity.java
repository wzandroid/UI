package com.example.uimdel.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import com.example.uimdel.R;

public class StorageActivity extends AppCompatActivity {
    private MotionLayout motionLayout;
    private int[] ids = new int[]{R.id.bot,R.id.bot2,R.id.bot3,R.id.bot4,R.id.bot5,R.id.bot6,R.id.bot7,R.id.bot8};
    private int[] sizeId = new int[]{R.id.bot1_size,R.id.bot2_size,R.id.bot3_size,R.id.bot4_size,R.id.bot5_size,R.id.bot6_size,R.id.bot7_size,R.id.bot8_size};
    private TextView[] sizeTv = new TextView[8];
    private ConstraintSet changeSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        motionLayout = findViewById(R.id.motionLayout);
        changeSet = motionLayout.getConstraintSet(R.id.end);
        motionLayout.addTransitionListener(transitionListener);
        motionLayout.post(new Runnable() {
            @Override
            public void run() {
                motionLayout.transitionToEnd();
            }
        });
        for (int i=0;i<sizeId.length;i++){
            sizeTv[i] = findViewById(sizeId[i]);
        }

        findViewById(R.id.group).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TAG","action = "+event.getAction()+",x = "+event.getX());
                return true;
            }
        });
    }

    private MotionLayout.TransitionListener transitionListener = new MotionLayout.TransitionListener() {
        @Override
        public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            Log.d("TAG","startId = "+startId+", endId = "+endId);
        }

        @Override
        public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
            Log.d("TAG","startId = "+startId+", endId = "+endId+",progress = "+progress);
        }

        @Override
        public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
            showNextAnim();
            Log.d("TAG","currentId = "+currentId);
        }

        @Override
        public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
            Log.d("TAG","triggerId = "+triggerId+", positive = "+positive+", progress = "+progress);
        }
    };

    private void showNextAnim(){
        for (int i=0;i<ids.length;i++){
            int size = getDotHeight();
            sizeTv[i].setText(size+"MB");
            changeSet.constrainHeight(ids[i], size);
        }
        TransitionManager.beginDelayedTransition(motionLayout);
        changeSet.applyTo(motionLayout);
    }

    private int getDotHeight(){
        return (int) (Math.random() * 300);
    }
}
