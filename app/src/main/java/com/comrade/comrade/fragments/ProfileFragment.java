package com.comrade.comrade.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.comrade.comrade.activity.BaseProfileActivity;
import com.comrade.comrade.activity.SettingActivity;
import com.comrade.comrade.databinding.FragmentProfileBinding;
import com.comrade.comrade.volly.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding;
    Fragment editProfileFragment = new EditProfileFragment();

    QueryPreferences queryPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);


        queryPreferences=new QueryPreferences(getActivity());
        onCLick();


      //  getUserData();
        setData();

        return binding.getRoot();
    }

    public void getUserData() {
        queryPreferences = new QueryPreferences(getActivity());

        HashMap<String, String> user;
        user = queryPreferences.getUserDetail();


        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("_id", user.get(queryPreferences.uid));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.FIND_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volly", "Response profile " + response);


                try {
                    JSONObject jsonObject = new JSONObject(response);


                    queryPreferences.setName(jsonObject.getString("username"),
                            jsonObject.getString("dob"));
                    queryPreferences.setGender(jsonObject.getString("gender"));

                    queryPreferences.setAbout(jsonObject.getString("about"));
                    queryPreferences.setPhone(jsonObject.getString("phone"));
                    queryPreferences.setHeight(jsonObject.getString("height"));
                    queryPreferences.relStatus(jsonObject.getString("relationship_status"));
                    queryPreferences.sexualOrientation(jsonObject.getString("sexuality"));
                    queryPreferences.lookingForGender(jsonObject.getString("interest_gender"));
                    queryPreferences.doYouDrink(jsonObject.getString("drinking"));
                    queryPreferences.doYouSmoke(jsonObject.getString("smoking"));
                    queryPreferences.gotoSchool(jsonObject.getString("education"));
                    queryPreferences.setShareMood(jsonObject.getString("moods"));
                    queryPreferences.doYouWork(jsonObject.getString("job"),
                            jsonObject.getString("company"));

                    queryPreferences.setWhatMakeHappy(jsonObject.getString("interest_in"),
                            jsonObject.getString("interest_in"),
                            jsonObject.getString("interest_in"),
                            jsonObject.getString("interest_in"),
                            jsonObject.getString("interest_in"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


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

    private void onCLick() {

        binding.editProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out  // popExit
                        )
                        .replace(R.id.mainContainer, editProfileFragment).addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });


        binding.completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BaseProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData() {


        HashMap<String, String> user = queryPreferences.getUserDetail();


        binding.about.setText(user.get(queryPreferences.aboutUs));
        binding.userNameAndAge.setText(user.get(queryPreferences.name) + ", " + user.get(queryPreferences.age));


    }

}