package com.comrade.comrade.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.activity.FilterActivity;
import com.comrade.comrade.adapters.NearByProfileAdapter;
import com.comrade.comrade.adapters.NearByStoryAdapter;
import com.comrade.comrade.databinding.FragmentNearbyBinding;
import com.comrade.comrade.dto.UsersModel;
import com.comrade.comrade.models.NearByProfileModel;
import com.comrade.comrade.models.StoryProfileModel;
import com.comrade.comrade.volly.Variables;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


public class NearbyFragment extends Fragment {


    FragmentNearbyBinding binding;
    ArrayList<StoryProfileModel> storyArrayList = new ArrayList<>();
    ArrayList<NearByProfileModel> arrayList = new ArrayList<>();

    NearByStoryAdapter adapter;

    NearByProfileAdapter nearByProfileAdapter;
    private HashMap<String,String> user;
    QueryPreferences queryPreferences;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);


        queryPreferences=new QueryPreferences(getActivity());

        user=queryPreferences.getUserDetail();




        onClick();
        getUserList();

        return binding.getRoot();
    }

    private void getUserList() {

        arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {



            jsonObject.put("user_id", user.get(queryPreferences.uid));
            jsonObject.put("long", user.get(queryPreferences.latitude));
            jsonObject.put("lat", user.get(queryPreferences.longitude));


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

                   /* arrayList.add(new NearByProfileModel(data.optString("_id"),
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
                            data.optString("long")));*/
                    // Log.e("LOG_VOLLEY", "Users response ssssssssssss" + response);
                }
             //   recyclerViewAdapt();

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
    private void onClick() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvStory.setLayoutManager(layoutManager);
        adapter = new NearByStoryAdapter(getActivity(), storyArrayList);
        binding.rvStory.setHasFixedSize(true);
        binding.rvStory.setItemViewCacheSize(20);
        binding.rvStory.setDrawingCacheEnabled(true);
        binding.rvStory.setAdapter(adapter);

        binding.rvNearbyProfile.setLayoutManager(new GridLayoutManager(getActivity(), 3));
       // nearByProfileAdapter = new NearByProfileAdapter(getActivity(), nearByProfileModels);
        binding.rvNearbyProfile.setHasFixedSize(true);
        binding.rvNearbyProfile.setItemViewCacheSize(20);
        binding.rvNearbyProfile.setDrawingCacheEnabled(true);
        binding.rvNearbyProfile.setAdapter(nearByProfileAdapter);

        binding.filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });

    }
}