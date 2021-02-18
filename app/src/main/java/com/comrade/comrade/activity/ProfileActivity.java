
package com.comrade.comrade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;


    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        uid = getIntent().getStringExtra("uid");


      //  getProfile(uid);

    }

 /*   private void getProfile(String uid) {


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


                    binding.profileUserName.setText(jsonObject.getString("username")+" ,"
                            +jsonObject.getString("dob")+"\n "+ jsonObject.getString("gender"));

                    binding.aboutUser.setText(jsonObject.getString("about"));
                    binding.profileUserName.setText(jsonObject.getString("height"));
                    binding.profileUserName.setText(jsonObject.getString("relationship_status"));
                    binding.profileUserName.setText(jsonObject.getString("sexuality"));
                    binding.profileUserName.setText(jsonObject.getString("interest_gender"));
                    binding.profileUserName.setText(jsonObject.getString("drinking"));
                    binding.profileUserName.setText(jsonObject.getString("smoking"));
                    binding.profileUserName.setText(jsonObject.getString("education"));
                    binding.profileUserName.setText(jsonObject.getString("moods"));
                    binding.profileUserName.setText(jsonObject.getString("job"));

                    binding.profileUserName.setText(jsonObject.getString("company"));

                    binding.profileUserName.setText(jsonObject.getString("interest_in") + " " +
                            jsonObject.getString("interest_in") + " " +
                            jsonObject.getString("interest_in") + " " +
                            jsonObject.getString("interest_in") + " " +
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
    }*/
}
