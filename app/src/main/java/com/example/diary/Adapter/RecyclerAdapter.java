package com.example.diary.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.Model.DiaryClass;
import com.example.diary.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.holder>{
    @NonNull
    ArrayList<DiaryClass> arrayList;

    public RecyclerAdapter(ArrayList<DiaryClass> arrayList) {
        this.arrayList = arrayList;
    }
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new holder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.recyclerRowBinding.title.setText(arrayList.get(position).title);
        holder.recyclerRowBinding.day.setText(arrayList.get(position).day);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public holder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
