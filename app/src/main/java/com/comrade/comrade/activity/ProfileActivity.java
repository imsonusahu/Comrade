
package com.comrade.comrade.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.databinding.ActivityProfileBinding;
import com.comrade.comrade.volly.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;


    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        uid = getIntent().getExtras().getString("uid");
        Log.e("ProfileActivity", "User id : " + uid);


        getProfile(uid);

    }

    private void getProfile(String uid) {

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
                Log.e("Volly", "Response profile " + response);


                try {
                    JSONObject jsonObject = new JSONObject(response);


                    Log.e("ProfileActivity", "User profile details " + jsonObject);


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
