package com.comrade.comrade.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comrade.comrade.R;
import com.comrade.comrade.models.HashTagModel;
import com.comrade.comrade.models.InterestModel;
import com.comrade.comrade.models.UserProfileModel;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.VH> {

    ArrayList<UserProfileModel> arrayList;
    ArrayList<HashTagModel> hashTagsList = new ArrayList<>();
    ArrayList<InterestModel> aboutList = new ArrayList<>();
    Context context;
    boolean isActive;
    private HashtagAdapter hashtagAdapter;
    private UserAboutAdapter aboutAdapter;

    public ProfileAdapter(FragmentActivity activity, ArrayList<UserProfileModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;

    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_view_items, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.VH holder, int position) {

        UserProfileModel imageModel = arrayList.get(position);

        String age = imageModel.getAge();

        holder.location.setText(imageModel.getLocation());
        holder.imHere.setText(imageModel.getIamHereAns());
        holder.name.setText(imageModel.getName() + ", " + age);
        holder.whatMakeYouLaugh.setText(imageModel.getWhatMakeYouLaugh());


        //adding hashtags layout here

        hashTagsList.add(new HashTagModel("#Music"));
        hashTagsList.add(new HashTagModel("#Dance"));
        hashTagsList.add(new HashTagModel("#Dating"));

        holder.hashTag_Rv.setLayoutManager(new GridLayoutManager(context, 3));
        holder.hashTag_Rv.setHasFixedSize(true);
        holder.hashTag_Rv.setItemViewCacheSize(20);
        holder.hashTag_Rv.setDrawingCacheEnabled(true);

        hashtagAdapter = new HashtagAdapter(context, hashTagsList);
        hashtagAdapter.notifyDataSetChanged();
        holder.hashTag_Rv.setAdapter(hashtagAdapter);


        //adding about layout here

        aboutList.add(new InterestModel("Straight"));
        aboutList.add(new InterestModel("Smoking"));
        aboutList.add(new InterestModel("Drinking"));

        holder.aboutRv.setLayoutManager(new GridLayoutManager(context, 3));
        holder.aboutRv.setHasFixedSize(true);
        holder.aboutRv.setItemViewCacheSize(20);
        holder.aboutRv.setDrawingCacheEnabled(true);

        aboutAdapter = new UserAboutAdapter(context, aboutList);
        aboutAdapter.notifyDataSetChanged();
        holder.aboutRv.setAdapter(aboutAdapter);



/*
        ImageView imageView=((AppCompatActivity)context).findViewById(R.id.storyProfileImg);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Glide.with(context)
                            .load("https://www.thisiscolossal.com/wp-content/uploads/2014/03/120430.gif")
                            .centerCrop()
                            .into(imageView);


                } catch (Exception e) {

                    e.printStackTrace();
                    Log.e("adapter", e.getMessage());
                }
            }
        });
*/


        try {

            Glide.with(context)
                    .load(imageModel.getProfilePic())
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

    public static class VH extends RecyclerView.ViewHolder {

        ImageView imageView;
        RecyclerView hashTag_Rv, aboutRv;
        TextView name, imHere, whatMakeYouLaugh, location;


        public VH(View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.userProfielImg);

            name = itemView.findViewById(R.id.profileUserName);
            location = itemView.findViewById(R.id.userLocation);
            whatMakeYouLaugh = itemView.findViewById(R.id.whatMakeYouLaugh);
            imHere = itemView.findViewById(R.id.imHere);
            aboutRv = itemView.findViewById(R.id.about_rv);
            hashTag_Rv = itemView.findViewById(R.id.interest_rv_hasTag);


        }
    }
}
