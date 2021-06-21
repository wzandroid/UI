package com.example.uimdel.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uimdel.R;

public class StorageViewModel extends ViewModel {
    private MutableLiveData<int[]> sizeData = new MutableLiveData<>();
    private MutableLiveData<Integer> touchData = new MutableLiveData<>();
    public boolean initAnimFinish;
    private int minHeight, mTopMargin;

    public LiveData<int[]> getSizeData(){
        return sizeData;
    }

    public LiveData<Integer> getTouchIndex(){
        return touchData;
    }

    public void setRangMinHeight(int minHeight){
        this.minHeight = minHeight;
    }

    public void pullSize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] heightSizes = new int[8];
                for (int i=0;i<8;i++){
                    heightSizes[i] = getDotHeight();
                }
                sizeData.postValue(heightSizes);
            }
        }).start();
    }

    private int getDotHeight(){
        int height = (int) (Math.random() * 300);
        height = height == 0 ? height : Math.max(height, minHeight);
        return height;
    }

    public void setTouchY(final float y) {
        Log.d("TAG","y = "+y);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] height = sizeData.getValue();
                if (height == null) return;
                int index = -1;
                synchronized (StorageViewModel.this){
                    int touchY = (int) y;
                    for (int i= 0; i < height.length; i++){
                        if (height[i] == 0) continue;
                        if (touchY< height[i]) {
                            index = i;
                            break;
                        }
                        touchY = touchY - height[i] + mTopMargin;
                    }
                    if (touchData.getValue() == null || index != touchData.getValue()){
                        touchData.postValue(index);
                    }
                }
            }
        }).start();
    }

    public int getLineColor(int index){
        switch (index){
            case 0:
                return R.color.storage_pie_line_1;
            case 1:
                return R.color.storage_pie_line_2;
            case 2:
                return R.color.storage_pie_line_3;
            case 3:
                return R.color.storage_pie_line_4;
            case 5:
                return R.color.storage_pie_line_6;
            case 6:
                return R.color.storage_pie_line_7;
            case 7:
                return R.color.storage_pie_line_8;
        }
        return R.color.black;
    }

    public void setTopDimen(int topDimen) {
        mTopMargin = topDimen;
    }
}
