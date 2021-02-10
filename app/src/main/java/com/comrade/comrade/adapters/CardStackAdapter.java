package com.comrade.comrade.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.dto.UsersModel;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<UsersModel> items;
    Context context;

    public CardStackAdapter(Context contexts, List<UsersModel> items) {
        context = contexts;
        this.items = items;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersModel data = items.get(position);

        Log.e("CardStackAdapter", data.getProfilePic());

        try {
            Glide.with(context)
                    .load("https://www.imagediamond.com/blog/wp-content/uploads/2019/07/girls-dpz6.jpg")
                    .centerCrop()
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("adapters", "User Images Adapter" + e.getMessage());
        }


        holder.name.setText(data.getUsername());
        holder.job.setText(data.getJob());
        holder.school.setText(data.getEducation());
        holder.location.setText(data.getDistance()+" Km");




    }


    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<UsersModel> getItems() {
        return null;
    }

    public void setItems(List<UsersModel> baru) {

        items = baru;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView imgList_rv;
        Button pre, next;
        ImageView imageView;
        TextView name, job, school, location, distance;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgList_rv = itemView.findViewById(R.id.images_user_rv);
            name = itemView.findViewById(R.id.item_name);
            job = itemView.findViewById(R.id.jobWork);
            school = itemView.findViewById(R.id.study);
            distance = itemView.findViewById(R.id.item_distance);
            location = itemView.findViewById(R.id.city);
            imageView = itemView.findViewById(R.id.imgView);
            //next = itemView.findViewById(R.id.btnNextImg);

        }


    }


}
