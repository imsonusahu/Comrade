package com.comrade.comrade.adapters.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.models.VisitorsModel;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.VH> {

    ArrayList<VisitorsModel> arrayList;
    Context context;
    private int delayAnimate;

    public VisitorsAdapter(FragmentActivity activity, ArrayList<VisitorsModel> myArraylist, int listCountz) {

        context = activity;
        arrayList = myArraylist;

    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitors_list_ui, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VisitorsAdapter.VH holder, int position) {

        VisitorsModel profileModel = arrayList.get(position);

        String age = profileModel.getDob();
        holder.name.setText(profileModel.getUsername() + ", " + age);
        holder.gender.setText(profileModel.getGender());



        /*holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 boolean isAnimated=false;
                if (!isAnimated){
                    holder.button.setSpeed(3f);
                    isAnimated=true;
                    holder.button.playAnimation();
                } else {
                    holder.button.setSpeed(-1F);
                    isAnimated=false;
                    holder.button.playAnimation();
                }
            }
        });*/

        holder.button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

                if (buttonState) {
                    // Button is active

                    button.setImageResource(R.drawable.like_icon);

                    holder.sendHeart.setText("Sent");
                    holder.sendHeart.setTextColor(Color.RED);

                } else {
                    // Button is inactive
                    button.setImageResource(R.drawable.unlike_icon);
                    holder.sendHeart.setText("Request match");
                    holder.sendHeart.setTextColor(Color.BLACK);

                }
                Log.e("datax", holder.sendHeart.getText() + "");
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        try {

            Glide.with(context)
                    .load(profileModel.getProfilePic())
                    .centerCrop()
                    .into(holder.imageView);

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("adapter", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public void updatesList(ArrayList<VisitorsModel> result) {

        arrayList = new ArrayList<>();
        arrayList.addAll(result);
        notifyDataSetChanged();
    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, gender, sendHeart;
        SparkButton button;

        public VH(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userImgSearchList);
            name = itemView.findViewById(R.id.usernameSearchList);
            gender = itemView.findViewById(R.id.genderProfileSearch);
            button = itemView.findViewById(R.id.like_button);
            sendHeart = itemView.findViewById(R.id.sendHeartBtn);


        }
    }

}
