package com.comrade.comrade.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.comrade.comrade.databinding.ActivityMainBinding;
import com.comrade.comrade.fragments.CardSwipeFragment;
import com.comrade.comrade.fragments.MassageFragment;
import com.comrade.comrade.fragments.NearbyFragment;
import com.comrade.comrade.fragments.ProfileFragment;
import com.comrade.comrade.volly.Variables;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    QueryPreferences queryPreferences;

    String phone;
    private static final String home="HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        queryPreferences = new QueryPreferences(this);

        loadFragment(new CardSwipeFragment());

        bottomTabs();


        try {

          //  new ProfileFragment().getUserData();
            getUserData();
        }catch (Exception e){
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }


       // userLogs();

    }

    private void userLogs() {


        HashMap<String, String> user = queryPreferences.getUserDetail();


        Log.e(home, "user id : " + user.get(queryPreferences.uid)  + "");
        Log.e(home, "user Name : " + user.get(queryPreferences.name) + "");
        Log.e(home, "user gender : " + user.get(queryPreferences.gender) + "");
        Log.e(home, "user age : " + user.get(queryPreferences.age) + "");
        Log.e(home, "user aboutUs : " + user.get(queryPreferences.aboutUs) + "");
        Log.e(home, "user height : " + user.get(queryPreferences.height) + "");
        Log.e(home, "user relStatus : " + user.get(queryPreferences.relStatus) + "");
        Log.e(home, "user sexuality : " + user.get(queryPreferences.sexuality) + "");
        Log.e(home, "user lookingFor : " + user.get(queryPreferences.lookingFor) + "");
        Log.e(home, "user doYourDrink : " + user.get(queryPreferences.doYouDrink) + "");
        Log.e(home, "user doYourSmoke : " + user.get(queryPreferences.doYouSmoke) + "");
        Log.e(home, "user goToSchool : " + user.get(queryPreferences.goToSchool) + "");
        Log.e(home, "user shareMood : " + user.get(queryPreferences.shareMood) + "");
        Log.e(home, "user jobTitle : " + user.get(queryPreferences.jobTitle) + "");
        Log.e(home, "user company : " + user.get(queryPreferences.company) + "");

        Log.e(home, "user latitude : " + user.get(queryPreferences.latitude) + "");
        Log.e(home, "user longitude : " + user.get(queryPreferences.longitude) + "");

        //hashTags
        Log.e(home, "user hasTag1 : " + user.get(queryPreferences.hasTag1) + "");
        Log.e(home, "user hasTag2 : " + user.get(queryPreferences.hasTag2) + "");
        Log.e(home, "user hasTag3 : " + user.get(queryPreferences.hasTag3) + "");
        Log.e(home, "user hasTag4 : " + user.get(queryPreferences.hasTag4) + "");
        Log.e(home, "user hasTag5 : " + user.get(queryPreferences.hasTag5) + "");
    }

    private void bottomTabs() {

        BottomTabView nearBy = new BottomTabView(this);
        nearBy.setTabIcon(R.drawable.ic_baseline_location_on_24);
        nearBy.setTabIconOnly(true);
        nearBy.setTabIconSize(24);


        BottomTabView slide = new BottomTabView(this);
        slide.setTabIcon(R.drawable.top_pics);
        slide.setTabIconOnly(true);
        slide.setTabIconSize(24);


        BottomTabView chatFrag = new BottomTabView(this);
        chatFrag.setTabIcon(R.drawable.chat_icon);
        chatFrag.setTabIconOnly(true);
        chatFrag.setTabIconSize(24);


        BottomTabView profileFrag = new BottomTabView(this);
        profileFrag.setTabIcon(R.drawable.profile_icon);
        profileFrag.setTabIconOnly(true);
        profileFrag.setTabIconSize(24);



        binding.bottomNav.setCurrentTab(1);

        binding.bottomNav
                .addTab(nearBy)
                .addTab(slide)
                .addTab(chatFrag)
                .addTab(profileFrag)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        //you can switch the fragment here


                        if (tab.getTabPosition() == 0) {
                            loadFragment(new NearbyFragment());
                        } else if (tab.getTabPosition() == 1) {
                            loadFragment(new CardSwipeFragment());
                        } else if (tab.getTabPosition() == 2) {
                            loadFragment(new MassageFragment());
                        } else if (tab.getTabPosition() == 3) {
                            loadFragment(new ProfileFragment());
                        }
                        tab.setSelectedColor(Color.parseColor("#FE2E49"));
                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {

                        tab.setUnselectedColor(Color.parseColor("#B6B5B5"));

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {

                    }
                });


    }




    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.mainContainer, fragment)
                .commitAllowingStateLoss();

    }
    public void getUserData() {


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
        Volley.newRequestQueue(this).add(stringRequest);

    }


}