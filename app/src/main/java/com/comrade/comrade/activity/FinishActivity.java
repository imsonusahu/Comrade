package com.comrade.comrade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.ActivityFinishBinding;
import com.comrade.comrade.volly.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FinishActivity extends AppCompatActivity {

    private static final String TAG = "FinishActivity";
    private ActivityFinishBinding binding;
    private QueryPreferences queryPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish);

        queryPreferences = new QueryPreferences(this);

        onClick();


    }

    private void onClick() {


        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        binding.getCard.setVisibility(View.VISIBLE);
        binding.getCard.setAnimation(myAnim);


        binding.myBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getUserData();

            }
        });
    }

    public void getUserData() {


        HashMap<String, String> user;
        user = queryPreferences.getUserDetail();


        JSONObject object = new JSONObject();


        try {
            object.put("_id", user.get(queryPreferences.uid));
            object.put("username", user.get(queryPreferences.name));
            object.put("dob", user.get(queryPreferences.age));
            object.put("gender", user.get(queryPreferences.gender));
            object.put("about", user.get(queryPreferences.aboutUs));
            object.put("phone", user.get(queryPreferences.phone));
            object.put("height", user.get(queryPreferences.height));
            object.put("relationship_status", user.get(queryPreferences.relStatus));
            object.put("sexuality", user.get(queryPreferences.sexuality));
            object.put("interest_gender", user.get(queryPreferences.lookingFor));
            object.put("drinking", user.get(queryPreferences.doYouDrink));
            object.put("smoking", user.get(queryPreferences.doYouSmoke));
            object.put("education", user.get(queryPreferences.goToSchool));
            object.put("moods", user.get(queryPreferences.shareMood));
            object.put("job", user.get(queryPreferences.jobTitle));
            object.put("company", user.get(queryPreferences.company));
            object.put("lat", user.get(queryPreferences.latitude));
            object.put("long", user.get(queryPreferences.longitude));
            object.put("user_type", "free");

            object.put("interest_in", user.get(queryPreferences.hasTag4));
            object.put("interest_in", user.get(queryPreferences.hasTag3));
            object.put("interest_in", user.get(queryPreferences.hasTag2));
            object.put("interest_in", user.get(queryPreferences.hasTag1));

        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("FinishActivity", e.getMessage());
        }


        final String mRequestBody = object.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.USER_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volly", "Response profile update " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Log.e(TAG, "Upload profile response is : " + message);

                    if (message.matches("success")) {

                        Intent intent = new Intent(FinishActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Log.e(TAG, "Upload profile response is : " + message);
                    }

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
