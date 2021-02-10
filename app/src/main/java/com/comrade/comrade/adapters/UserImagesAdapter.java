package com.comrade.comrade.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.models.UserImagesModel;

import java.util.ArrayList;

public class UserImagesAdapter extends RecyclerView.Adapter<UserImagesAdapter.VH> {

    ArrayList<UserImagesModel> arrayList = new ArrayList<>();

    Context context;



    public UserImagesAdapter(Context activity, ArrayList<UserImagesModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;

    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_items_user, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserImagesAdapter.VH holder, int position) {

        UserImagesModel imageModel = arrayList.get(position);



        try {

            Glide.with(context)
                    .load(imageModel.getUserImages())
                    .centerCrop()
                    .into(holder.imageView);


        } catch (Exception e) {

            e.printStackTrace();
            Log.e("adapters","User Images Adapter"+ e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;

    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView imageView;




        public VH(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.item_image);


        }
    }
}
