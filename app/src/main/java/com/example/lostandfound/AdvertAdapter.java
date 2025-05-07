package com.example.lostandfound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    private List<AdvertModel> advertList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AdvertModel advert);
    }

    public AdvertAdapter(List<AdvertModel> advertList, OnItemClickListener listener) {
        this.advertList = advertList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        AdvertModel advert = advertList.get(position);
        holder.textTitle.setText(advert.getType() + " - " + advert.getName());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(advert));
    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }
}
