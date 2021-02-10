package com.comrade.comrade.fragments.menuTabs;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.adapters.menu.VisitorsAdapter;
import com.comrade.comrade.databinding.FragmentProfileVisitorsListBinding;
import com.comrade.comrade.models.VisitorsModel;
import com.comrade.comrade.volly.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ProfileVisitorsListFragment extends Fragment {

    FragmentProfileVisitorsListBinding binding;
    private VisitorsAdapter adapter;
    private ArrayList<VisitorsModel> arrayList;
    private QueryPreferences queryPreferences;
    private HashMap< String, String > users;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile_visitors_list, container, false);

        queryPreferences=new QueryPreferences(getContext());
        users=queryPreferences.getUserDetail();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                initView();
            }
        },500);


        return binding.getRoot();
    }
    private void initView() {


        binding.visitorsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.visitorsRv.setHasFixedSize(true);
        binding.visitorsRv.setItemViewCacheSize(20);
        binding.visitorsRv.setDrawingCacheEnabled(true);
        binding.visitorsRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter = new VisitorsAdapter(getActivity(), arrayList);
        binding.visitorsRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchViewOne();



        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getUserList();
                //Your code to run in GUI thread here
            }//public void run() {
        });


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


    private void getUserList() {


        arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("_id", users.get(queryPreferences.uid));

            jsonObject.put("lat", users.get(queryPreferences.latitude));
            jsonObject.put("lng", users.get(queryPreferences.longitude));

            Log.e("Latlong","Lat :  " +users.get(queryPreferences.latitude)+" " +
                    "   "+"long : " +users.get(queryPreferences.longitude));

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("json", e.getMessage());
        }


        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.GET_VISITORS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOG_VOLLEY", "Users response" + response);

                try {

                    JSONObject o = new JSONObject(response);
                    JSONArray arr = o.getJSONArray("users");

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject data = arr.getJSONObject(i);

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

                        Log.e("UsersProfile", " Profile visitor list : " + arrayList.get(i).get_id());

                    }
                    recyclerViewAdapt();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());


            }
        }) {
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

    private void recyclerViewAdapt() {

        adapter.notifyDataSetChanged();
    }

}