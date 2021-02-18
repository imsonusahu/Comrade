package com.comrade.comrade.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.activity.FilterActivity;
import com.comrade.comrade.activity.MenuActivity;
import com.comrade.comrade.adapters.CardStackAdapter;
import com.comrade.comrade.databinding.FragmentCardSwipeBinding;
import com.comrade.comrade.dto.UsersModel;
import com.comrade.comrade.volly.Variables;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardSwipeFragment extends Fragment implements CardStackListener {

    FragmentCardSwipeBinding binding;
    CardStackLayoutManager manager;
    List<UsersModel> arrayList;
    CardStackAdapter adapter;
    public String phone;
    QueryPreferences queryPreferences;
    private final String home = "home";
    HashMap<String, String> user;

    int position;

    String uid, likedTo_uid, userName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_swipe, container, false);
        queryPreferences = new QueryPreferences(getActivity());

        user = queryPreferences.getUserDetail();

        initView();
        onClick();

        binding.contentLoader.startRippleAnimation();

        getUserList();

        new Handler().postDelayed(() -> {

            binding.contentLoader.stopRippleAnimation();
            binding.contentLoader.setVisibility(View.GONE);
            binding.cardStackView.setVisibility(View.VISIBLE);

        }, 500);

        return binding.getRoot();
    }

    //Send match API
    private void sendMatch() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("liked_by", user.get(queryPreferences.uid));
            jsonObject.put("liked_to", likedTo_uid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.SEND_LIKE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Volly", "Response Send math : " + response);
            }
        }, error -> Log.e("Volly", "Error Response" + error.toString())) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    //Send super-like API

    private void sendSuperLike() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("super_liked_by", user.get(queryPreferences.uid));
            jsonObject.put("super_liked_to", likedTo_uid);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Volly", "Response Send super like  " + e.getMessage());
        }
        String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.SEND_SUPER_LIKE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volly", "Response Send super like  " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volly", "Error Response" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    //Get user list API
    private void getUserList() {

        arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("_id", user.get(queryPreferences.uid));
            jsonObject.put("genderpref", "Male");


            Log.e("jsonx", "Female");
            Log.e("jsonx", user.get(queryPreferences.uid));

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("json", e.getMessage());
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.FILTER_VIEW, response -> {
            Log.e("LOG_VOLLEY", "Users response ssssssssssss" + response);

            try {

                JSONObject o = new JSONObject(response);
                JSONArray arr = o.getJSONArray("users");

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject data = arr.getJSONObject(i);

                    arrayList.add(new UsersModel(data.optString("_id"),
                            data.optString("phone"),
                            data.optString("about"),
                            data.optString("company"),
                            data.optString("dob"),
                            data.optString("drinking"),
                            data.optString("education"),
                            data.optString("email"),
                            data.optString("gender"),
                            data.optString("height"),
                            data.optString("interest_gender"),
                            data.optString("interest_in"),
                            data.optString("job"),
                            data.optString("interest_gender"),
                            data.optString("relationship_status"),
                            data.optString("smoking"),
                            data.optString("username"),
                            data.optString("moods"),
                            "https://www.imagediamond.com/blog/wp-content/uploads/2019/07/girls-dpz6.jpg",
                            data.optString("distance"),
                            data.optString("lat"),
                            data.optString("long")));
                   // Log.e("LOG_VOLLEY", "Users response ssssssssssss" + response);
                }
                recyclerViewAdapt();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("LOG_VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };
        stringRequest.setShouldCache(false);

        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }

    //send-visitor

    private void sendVisitors() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("visited_by", user.get(queryPreferences.uid));
            jsonObject.put("visited_on_profile", likedTo_uid);

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("Volly", "Response visited_on_profile  " + e.getMessage());
        }

        String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.VISITOR_REGISTER, response -> Log.e("Volly", "Response visited_on_profile  " + response), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volly", "Error Response" + error.toString());

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }


    private void initView() {
        manager = new CardStackLayoutManager(getActivity(), this);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());

    }

    private void onClick() {




        binding.filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        binding.searchBtn.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
        });
        binding.likeBtn.setOnClickListener(v -> {

            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(350)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(setting);
            binding.cardStackView.swipe();


            Toast.makeText(getActivity(), "Like" + setting.getDirection(), Toast.LENGTH_SHORT).show();
            Log.e(home, "Direction : " + setting.getDirection());


            sendMatch();
        });
        binding.rewindBtn.setOnClickListener(v -> {

            RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .build();
            manager.setRewindAnimationSetting(setting);
            binding.cardStackView.rewind();

            Toast.makeText(getActivity(), "Rewind" + setting.getDirection(), Toast.LENGTH_SHORT).show();
            Log.e(home, "Direction : " + setting.getDirection());
        });
        binding.nopeBtn.setOnClickListener(v -> {

            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(400)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(setting);
            binding.cardStackView.swipe();
            Log.e(home, "Direction : " + setting.getDirection());
        });
        binding.superLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Top)
                        .setDuration(400)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                binding.cardStackView.swipe();
                Log.e(home, "Direction : " + setting.getDirection());
            }
        });
    }


    private void recyclerViewAdapt() {

        adapter = new CardStackAdapter(getActivity(), arrayList);
        binding.cardStackView.setLayoutManager(manager);
        binding.cardStackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        //   Log.d("onCardSwiped", "onCardRewound: " + direction);

        //     Toast.makeText(getActivity(), direction.toString(), Toast.LENGTH_SHORT).show();


        if (direction == Direction.Right) {

            Log.d("onCardSwiped", " Direction :" + direction);


            sendMatch();
            sendVisitors();

        } else if (direction == Direction.Left) {

            Log.d("onCardSwiped", " Direction :" + direction);

            sendMatch();
            sendVisitors();

        } else if (direction == Direction.Top) {

            Log.d("onCardSwiped", " Direction :" + direction);

            sendSuperLike();
            sendVisitors();
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {
        // Log.d("onCardSwiped", "onCardCanceled: ");

    }

    @Override
    public void onCardAppeared(View view, int position) {
        //   Log.d("onCardSwiped", "onCardAppeared: " + position);


        likedTo_uid = arrayList.get(position).get_id();
        Log.e("onCardAppeared", "Current userId  " + user.get(queryPreferences.uid));
        Log.e("onCardAppeared", " appear user Id  " + likedTo_uid);

    }

    @Override
    public void onCardDisappeared(View view, int position) {
        //  Log.d("onCardSwiped", "onCardDisappeared: " + position);


    }
}