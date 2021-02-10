package com.comrade.comrade.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comrade.comrade.R;
import com.comrade.comrade.models.InterestModel;

import java.util.ArrayList;

public class UserAboutAdapter extends RecyclerView.Adapter<UserAboutAdapter.VH> {

    ArrayList<InterestModel> arrayList;
    Context context;
    boolean isActive;

    public UserAboutAdapter(Context activity, ArrayList<InterestModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_layout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAboutAdapter.VH holder, int position) {

        InterestModel interestModel = arrayList.get(position);
        holder.about.setText(interestModel.getInterestType());

    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;

    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView about;

        public VH(View itemView) {
            super(itemView);


            about = itemView.findViewById(R.id.interestAboutText);

        }
    }
}
