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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import com.example.uimdel.R;
import com.example.uimdel.viewmodel.StorageViewModel;
import com.example.uimdel.weiget.LineView;
import com.example.uimdel.weiget.RangView;

public class StorageActivity extends AppCompatActivity {
    private MotionLayout motionLayout;
    private int[] ids = new int[]{R.id.bot,R.id.bot2,R.id.bot3,R.id.bot4,R.id.bot5,R.id.bot6,R.id.bot7,R.id.bot8};
    private int[] sizeId = new int[]{R.id.bot8_size,R.id.bot7_size,R.id.bot6_size,R.id.bot5_size,R.id.bot4_size,R.id.bot3_size,R.id.bot2_size,R.id.bot1_size};
    private TextView[] sizeTv = new TextView[sizeId.length];
    private RangView[] botViews = new RangView[ids.length];
    private int[] lineColors = new int[]{R.color.storage_pie_line_1,R.color.storage_pie_line_2,R.color.storage_pie_line_3,R.color.storage_pie_line_4,R.color.storage_pie_line_5,R.color.storage_pie_line_6,R.color.storage_pie_line_7,R.color.storage_pie_line_8};
    private ConstraintSet changeSet;
    private LineView lineView;

    private StorageViewModel storageModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        initViewModel();
        initUI();
        initData();
        motionLayout.post(new Runnable() {
            @Override
            public void run() {
                motionLayout.transitionToEnd();
            }
        });
    }

    private void initUI(){
        motionLayout = findViewById(R.id.motionLayout);
        lineView = findViewById(R.id.lineView);
        changeSet = motionLayout.getConstraintSet(R.id.end);
        motionLayout.addTransitionListener(transitionListener);

        for (int i=0;i<sizeId.length;i++){
            sizeTv[i] = findViewById(sizeId[i]);
            botViews[i] = findViewById(ids[i]);
        }

        findViewById(R.id.group).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                Log.d("TAG","action = "+action);
                if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
                    touchView(-1);
                    return false;
                }else{
//                    Log.d("TAG","touchY = "+event.getY()+", height = "+v.getMeasuredHeight());
                    storageModel.setTouchY(v.getMeasuredHeight() - event.getY());
                }
                return true;
            }
        });
    }

    private void initData(){
        storageModel.pullSize();

    }

    private void initViewModel(){
        storageModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(StorageViewModel.class);
        storageModel.setRangMinHeight(getResources().getDimensionPixelSize(R.dimen.rang_min_height));
        storageModel.setTopDimen(getResources().getDimensionPixelSize(R.dimen.rang_top_height));
        storageModel.getSizeData().observe(this, new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                showNextAnim(ints);
            }
        });

        storageModel.getTouchIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                if (index == -1) {
                    lineView.cleanLine();
                }
                touchView(index);
            }
        });
    }

    private void touchView(int index){
        for (int i=0;i<botViews.length;i++){
            botViews[i].setTint(index == -1 || index == i);
        }
        if (index == -1) {
            lineView.cleanLine();
            return;
        }
        lineView.drawLine(botViews[index], sizeTv[index], getResources().getColor(lineColors[index]));
    }

    private MotionLayout.TransitionListener transitionListener = new MotionLayout.TransitionListener() {
        @Override
        public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        }

        @Override
        public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        }

        @Override
        public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
            storageModel.initAnimFinish = true;
        }

        @Override
        public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        }
    };

    private void showNextAnim(int[] sizeDatas){
        for (int i=0;i<sizeDatas.length;i++){
            int size = sizeDatas[i];
            sizeTv[i].setText(size+"MB");
            changeSet.constrainHeight(ids[i], size);
        }
        if (storageModel.initAnimFinish){
            TransitionManager.beginDelayedTransition(motionLayout);
            changeSet.applyTo(motionLayout);
        }
    }


}
