package com.example.uimdel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uimdel.R;

import java.util.ArrayList;
import java.util.List;

public class UIAdapter extends RecyclerView.Adapter<UIAdapter.Holder>{
    private List<String> dataList = new ArrayList<>();

    public void setDataList(List<String> list){
        dataList.clear();
        if (list != null && list.size() > 0){
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_ui_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        private TextView titleTv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title);
        }

        public void bindData(String title){
            titleTv.setText(title);
        }
    }
}
