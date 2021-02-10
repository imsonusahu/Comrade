package com.comrade.comrade.fragments.menuTabs;

import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.adapters.menu.VisitorsAdapter;
import com.comrade.comrade.databinding.FragmentProfileVisitorsListBinding;
import com.comrade.comrade.models.VisitorsModel;
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

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileVisitorsListFragment extends Fragment {

    FragmentProfileVisitorsListBinding binding;
    private VisitorsAdapter adapter;
    private ArrayList<VisitorsModel> arrayList;
    private QueryPreferences queryPreferences;
    private HashMap<String, String> users;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    Boolean loading = true;

    private ArrayList<String> visitorUidList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_visitors_list, container, false);

        queryPreferences = new QueryPreferences(getActivity());
        users = queryPreferences.getUserDetail();

        binding.simmerView.setVisibility(View.VISIBLE);
        binding.simmerView.startShimmerAnimation();


        getVisitors();
        initView();


        return binding.getRoot();
    }

    private void initView() {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.visitorsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.visitorsRv.setHasFixedSize(true);
        binding.visitorsRv.setItemViewCacheSize(20);
        binding.visitorsRv.setDrawingCacheEnabled(true);
        binding.visitorsRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.visitorsRv.setItemAnimator(new DefaultItemAnimator());
        adapter = new VisitorsAdapter(getActivity(), arrayList);
        binding.visitorsRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchViewOne();


    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("search", "onStart() profile");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("search", "onResume() profile");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("search", "onPause() profile");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e("search", "onDestroyView() profile");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("search", "onDestroy() profile");
    }

    private void searchViewOne() {
        binding.searchVisitors.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("search", "is working profile");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<VisitorsModel> result = new ArrayList<>();

                for (VisitorsModel state : arrayList) {
                    if (state.getGender().toLowerCase().contains(newText) || state.getUsername().toLowerCase().contains(newText)) {

                        result.add(state);
                    }
                    ((VisitorsAdapter) Objects.requireNonNull(binding.visitorsRv.getAdapter())).updatesList(result);
                }
                return false;
            }
        });
    }


    private void getVisitors( ) {


        arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("visited_by", users.get(queryPreferences.uid));
            Log.e("json", "My Id : "+users.get(queryPreferences.uid));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json", e.getMessage());
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.GET_VISITORS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOG_VOLLEY", "Visitors response" + response);

                try {
                    JSONObject object = new JSONObject(response);

                    String status = object.optString("status");

                    String totalVisits = object.optString("totalvisits");
                    Log.e("VisitsFrag", "totalVisits  : " + totalVisits);

                    JSONArray arr = object.getJSONArray("visitedon");
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject data = arr.getJSONObject(i);

                        String uid = data.optString("visited_on_profile");

                        Log.e("LOG_VOLLEY", "Visitors response : " + uid);
                        getProfiles(uid);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("LOG_VOLLEY", "Visitors response" + e.getMessage());
                }
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

    private void getProfiles(String uid) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("_id", uid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.FIND_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volly", "Visitors user profile response :  " + response);

                try {
                    JSONObject data = new JSONObject(response);

                    arrayList.add(new VisitorsModel(data.optString("_id"),
                            data.optString("phone"),
                            data.optString("about"), data.optString("company"), data.optString("dob"),
                            data.optString("drinking"), data.optString("education"), data.optString("email"),
                            data.optString("gender"), data.optString("height"),
                            data.optString("interest_gender"),
                            data.optString("interest_in"),
                            data.optString("job"), data.optString("interest_gender"),
                            data.optString("relationship_status"), data.optString("smoking"),
                            data.optString("username"), data.optString("moods"),
                            "https://www.imagediamond.com/blog/wp-content/uploads/2019/07/girls-dpz6.jpg",
                            data.optString("distance"), data.optString("lat"), data.optString("long")));

                    recyclerViewAdapt();

                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e(getActivity().getLocalClassName(), "Visitors user profile Error Response" + e.toString());
                }


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
            public byte[] getBody() {
                return mRequestBody.getBytes(StandardCharsets.UTF_8);
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }

    private void recyclerViewAdapt() {

        adapter.notifyDataSetChanged();
        binding.visitorsRv.setVisibility(View.VISIBLE);
        binding.simmerView.setVisibility(View.GONE);
        binding.simmerView.stopShimmerAnimation();


        binding.visitorsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = true;
                            //page = page+1;
                            getVisitors();
                        }
                    }
                }
            }
        });

    }

}