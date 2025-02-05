package com.comrade.comrade.fragments.menuTabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.adapters.menu.WhoLikedAdapter;
import com.comrade.comrade.databinding.FragmentWhoLikesBinding;
import com.comrade.comrade.models.WhoLikedModel;
import com.comrade.comrade.volly.Variables;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WhoLikesFragment extends Fragment {


    private static final String TAG = "WhoLiked";
    private FragmentWhoLikesBinding binding;
    private ArrayList<WhoLikedModel> arrayList;
    private Map<String, String> users;
    private QueryPreferences queryPreferences;
    private String liked_by;


    int havePosition;
    private String matchId;
    private String matchUserId, myId;

    WhoLikedAdapter adapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_who_likes, container, false);
        queryPreferences = new QueryPreferences(getActivity());

        users = queryPreferences.getUserDetail();
        binding.simmerViewWhoLike.startShimmerAnimation();

        initView();

        getUserList();
        onClick();


        return binding.getRoot();
    }

    private void onClick() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("search", "onStart() who");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("search", "onResume() who");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("search", "onPause() who");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e("search", "onDestroyView() who");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("search", "onDestroy() who");
    }

    private void initView() {

        binding.matchRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.matchRv.setHasFixedSize(true);
        binding.matchRv.setItemViewCacheSize(20);
        binding.matchRv.setDrawingCacheEnabled(true);
        binding.matchRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        searchView();
    }

    private void searchView() {

        binding.searchViewWho.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("search", "is working Who");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<WhoLikedModel> result = new ArrayList<>();

                for (WhoLikedModel state : arrayList) {

                    if (state.getGender().toLowerCase().contains(newText) || state.getUsername().toLowerCase().contains(newText)) {
                        result.add(state);
                    }
                    ((WhoLikedAdapter) Objects.requireNonNull(binding.matchRv.getAdapter())).updates(result);
                }
                return false;
            }
        });
    }

    private void getUserList() {

        arrayList = new ArrayList<>();
        arrayList.clear();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("liked_by", users.get(queryPreferences.uid));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json", e.getMessage());
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.GET_MATCHES, response -> {
            Log.e("LOG_VOLLEY", "My match : " + response);
            try {
                JSONObject object = new JSONObject(response);

                String totalMatches = object.optString("totalmatch");
                binding.totalMatchCount.setText(totalMatches);

                Log.e("WhoLiked", "totalMatches  : " + totalMatches);

                JSONArray arr = object.getJSONArray("Array");

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject data = arr.getJSONObject(i);
                    matchId = data.optString("_id");
                    liked_by = data.optString("liked_by");


                    Log.e("WhoLiked", "  : " + matchId);
                    Log.e("WhoLiked", "liked_by  : " + liked_by);

                    getMatchUserProfile(liked_by, matchId);

                }
            } catch (JSONException e) {
                e.printStackTrace();

                Log.e("WhoLiked", "liked_by Error : " + e.getMessage());
            }
        }, error -> Log.e("LOG_VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) {

                Log.e(TAG, "Time error volly : " + error.getMessage());
            }
        });
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void getMatchUserProfile(String liked_by, String matchId) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("_id", liked_by);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.FIND_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volly", "Matched user profile response : " + response);

                try {
                    JSONObject data = new JSONObject(response);

                    arrayList.add(new WhoLikedModel(data.optString("_id"),
                            data.optString("phone"),
                            data.optString("about"), data.optString("company"), data.optString("dob"),
                            data.optString("drinking"), data.optString("education"), data.optString("email"),
                            data.optString("gender"), data.optString("height"),
                            data.optString("interest_gender"),
                            data.optString("interest_in"),
                            data.optString("job"), data.optString("interest_gender"),
                            data.optString("relationship_status"),
                            data.optString("smoking"),
                            data.optString("username"),
                            data.optString("moods"),
                            "https://www.imagediamond.com/blog/wp-content/uploads/2019/07/girls-dpz6.jpg",
                            data.optString("distance"), data.optString("lat"),
                            data.optString("long"), matchId));

                    recyclerView();

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("Volly", "Matched user profile Error Response" + e.toString());
                }


            }
        }, error -> Log.e("Volly", "Error Response" + error.toString())) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
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

    private void recyclerView() {

        adapter = new WhoLikedAdapter(getActivity(), arrayList);
        binding.matchRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.simmerViewWhoLike.stopShimmerAnimation();
        binding.simmerViewWhoLike.setVisibility(View.GONE);
        binding.matchRv.setVisibility(View.VISIBLE);
    }


}