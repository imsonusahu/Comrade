package com.comrade.comrade.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.models.HashTagModel;

import java.util.ArrayList;
import java.util.Random;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.VH> {

    ArrayList<HashTagModel> arrayList;
    Context context;

    String s;
    QueryPreferences queryPreferences;

    ArrayList<HashTagModel> selectedTagList = new ArrayList<>();

    public ArrayList<HashTagModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HashTagModel> arrayList) {
        this.arrayList = arrayList;
    }


    public HashtagAdapter(Context activity, ArrayList<HashTagModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_hashtags, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagAdapter.VH holder, int position) {

        HashTagModel hashTagModel = arrayList.get(position);


        queryPreferences = new QueryPreferences(context);

        holder.hasTags.setText(hashTagModel.getHashtags());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.hasTags.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.hasTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashTagModel.setSelected(!hashTagModel.isSelected());
                holder.hasTags.setBackgroundTintList(ColorStateList.valueOf(hashTagModel.isSelected() ?
                        Color.RED : color));


                try {

                    for (int i = 0; i <= 5; i++) {

                        selectedTagList.add(i, new HashTagModel(hashTagModel.getHashtags()));
                    }
                    queryPreferences.setWhatMakeHappy(selectedTagList.get(0).getHashtags(),
                            selectedTagList.get(1).getHashtags(),
                            selectedTagList.get(2).getHashtags(),
                            selectedTagList.get(3).getHashtags(),
                            selectedTagList.get(4).getHashtags());

                } catch (NullPointerException e) {




                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;

    }

    public static class VH extends RecyclerView.ViewHolder {
        private int position;

        CardView cardView;
        TextView hasTags;

        public VH(View itemView) {
            super(itemView);

            hasTags = itemView.findViewById(R.id.hastagText);
            cardView = itemView.findViewById(R.id.myCard);

        }
    }
}
