package com.example.uimdel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.uimdel.adapter.UIAdapter;
import com.example.uimdel.ui.StorageActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private UIAdapter uiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        uiAdapter = new UIAdapter();
        uiAdapter.setItemClickCallback(clickCallback);
        recyclerView.setAdapter(uiAdapter);
        initData();
    }

    private void initData() {
        uiAdapter.setDataList(Arrays.asList("1、固定比例试图", "2、N等分布局", "3、复杂元素相对居中", "4、百分比对齐", "5、角度布局", "6、整体居中", "7、超长限制强制约束", "8、多组件协同约束", "9、容器约束下的边界约束"));
    }

    private final UIAdapter.onItemClickCallback clickCallback = (position, action) -> startActivity(new Intent(MainActivity.this, StorageActivity.class));
}