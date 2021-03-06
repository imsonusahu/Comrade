package com.comrade.comrade.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.models.UserImagesModel;
import com.comrade.comrade.models.UserImgModel;

import java.util.ArrayList;

public class UserImgAdapter extends RecyclerView.Adapter<UserImgAdapter.VH> {

    ArrayList< UserImgModel > arrayList = new ArrayList<>();

    Context context;



    public UserImgAdapter(Context activity, ArrayList<UserImgModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;

    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_items_user, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserImgAdapter.VH holder, int position) {

        UserImgModel imageModel = arrayList.get(position);


        Log.e("Adapter","imageModel "+imageModel.getImgUrl());

        holder.nameAge.setText(imageModel.getName()+", "+imageModel.getAge());

        holder.nameAge.setText(imageModel.getLocation());


        try {

            Glide.with(context)
                    .load(imageModel.getImgUrl())
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

        ImageView imageView,verifyIc;

        TextView nameAge,address;

        public VH(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.item_image);
            verifyIc = itemView.findViewById(R.id.verifyIc);
            nameAge=itemView.findViewById(R.id.nameAge);
            address=itemView.findViewById(R.id.location);


        }
    }
}
