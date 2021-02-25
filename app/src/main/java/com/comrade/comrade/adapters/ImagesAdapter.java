package com.comrade.comrade.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.comrade.comrade.R;
import com.comrade.comrade.models.MediaModel;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.VH> {

    ArrayList<MediaModel> arrayList;
    Context context;

    public ImagesAdapter(FragmentActivity activity, ArrayList<MediaModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
        notifyDataSetChanged();

    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.VH holder, int position) {

        MediaModel mediaModel = arrayList.get(position);
        holder.imageView.setImageURI(mediaModel.getImageUri());


    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 10;

    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView imageView, addImage;

        public VH(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.userImages);


        }
    }
}
