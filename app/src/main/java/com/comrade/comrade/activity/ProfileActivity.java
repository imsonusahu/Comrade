
package com.comrade.comrade.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.adapters.UserImgAdapter;
import com.comrade.comrade.databinding.ActivityProfileBinding;
import com.comrade.comrade.models.UserImgModel;
import com.comrade.comrade.volly.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    ArrayList< UserImgModel > arrayList;
    UserImgAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        uid = getIntent().getExtras().getString("uid");
        Log.e("ProfileActivity", "User id : " + uid);


        getProfile(uid);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        binding.imgListRv.setLayoutManager(layoutManager);


    }

    private void getProfile(String uid) {

        arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("_id", "602e176c83912a3a63dc3d61");

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

                    arrayList.add(new UserImgModel(jsonObject.getString("username"),
                            jsonObject.getString("dob"),
                            "Indore"));


                    JSONArray jsonArray = jsonObject.getJSONArray("imagearray");


                    for (int i = 0; i <jsonArray.length(); i++) {

                        String imgUrl = jsonArray.getJSONObject(i).getString("avatar");

                        arrayList.add(new UserImgModel(imgUrl));
                    }
                    adapterSetup();


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, error -> Log.e("Volly", "Error Response" + error.toString())) {

            @Override
            public Map< String, String > getHeaders() throws AuthFailureError {
                HashMap< String, String > headers = new HashMap< String, String >();
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

    private void adapterSetup() {


        adapter = new UserImgAdapter(this, arrayList);
        binding.imgListRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
