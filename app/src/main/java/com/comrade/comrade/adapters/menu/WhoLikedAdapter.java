package com.comrade.comrade.adapters.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.comrade.comrade.R;
import com.comrade.comrade.models.WhoLikedModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhoLikedAdapter extends RecyclerView.Adapter<WhoLikedAdapter.VH> {

    ArrayList<WhoLikedModel> arrayList;
    Context context;
    boolean isActive;


    public WhoLikedAdapter(FragmentActivity activity, ArrayList<WhoLikedModel> myArraylist) {

        context = activity;
        arrayList = myArraylist;
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.who_liked_ui, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WhoLikedAdapter.VH holder, int position) {

        WhoLikedModel likedModel = arrayList.get(position);


      //  holder.name_age.setText(likedModel.getMatchUserName()+", "+likedModel.getAge()+"");
     //   holder.location_dist.setText(likedModel.getDistance()+", "+likedModel.getLocation());





/*


        try {
            Glide.with(context)
                    .load(likedModel.getMatchUserPic())
                    .centerCrop()
                    .into(holder.matchUserImg);

            Glide.with(context)
                    .load(likedModel.getMatchUserPic())
                    .centerCrop()
                    .into(holder.userMatchBg);



            Glide.with(context)
                    .load(likedModel.getCurrentUsrPic())
                    .centerCrop()
                    .into(holder.currentUserPic);



        }catch (Exception e){

            e.printStackTrace();
        }

        holder.letsChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("picUrl",likedModel.getMatchUserPic());
                intent.putExtra("name",likedModel.getMatchUserName());
                context.startActivity(intent);
            }
        });

*/

    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;

    }

    public void updates(ArrayList<WhoLikedModel> result) {

        arrayList = new ArrayList<>();
        arrayList.addAll(result);
        notifyDataSetChanged();

    }


    public static class VH extends RecyclerView.ViewHolder {

        ImageView userMatchBg;

        CircleImageView matchUserImg, currentUserPic;
        TextView name_age,location_dist;

        Button letsChatBtn;

        public VH(View itemView) {
            super(itemView);


            userMatchBg = itemView.findViewById(R.id.userMatchBg);
            name_age = itemView.findViewById(R.id.matchName);
            location_dist = itemView.findViewById(R.id.location_dist);
            matchUserImg = itemView.findViewById(R.id.matchUserImg);
            currentUserPic = itemView.findViewById(R.id.currentUserPic);
            letsChatBtn = itemView.findViewById(R.id.sendMsgButtonMatch);



        }
    }
}
