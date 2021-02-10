package com.comrade.comrade.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.databinding.ActivityLoginBinding;
import com.comrade.comrade.onBoardingScreen.OnBoardItem;
import com.comrade.comrade.onBoardingScreen.OnBoard_Adapter;
import com.comrade.comrade.volly.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Dialog dialog;

    QueryPreferences queryPreferences;
    private int dotsCount = 3;
    private ImageView[] dots;

    private OnBoard_Adapter mAdapter;

    int previous_pos = 0;
    private ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        queryPreferences = new QueryPreferences(this);








        onClick();


        loadData();

        mAdapter = new OnBoard_Adapter(this, onBoardItems);
        binding.pagerIntroduction.setAdapter(mAdapter);
        binding.pagerIntroduction.setCurrentItem(0);

        setUiPageViewController();

    }

    private void onClick() {


        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = 91+binding.phoneNumber.getText().toString().trim();
                binding.btnSignIn.setVisibility(View.VISIBLE);
                validation();
            }
        });
    }

    private void validation() {

        if (binding.phoneNumber.getText().toString().trim().length() == 10) {

            sendOtp(phoneNumber);

        } else {

            binding.btnSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void sendOtp(String phoneNumber) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
            binding.btnSignIn.setVisibility(View.VISIBLE);
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.SEND_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOG_VOLLEY", response);

                binding.btnSignIn.setVisibility(View.VISIBLE);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getJSONObject("data").getString("type");
                    String otp = jsonObject.getString("otp");


                    Log.e("LoginActivity", " OTP Status" + status);
                    Log.e("LoginActivity", " OTP Status" + otp);

                    if (status.matches("success")) {


                        queryPreferences.setPhone(phoneNumber);
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("phone", phoneNumber);

                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.btnSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());

                binding.btnSignIn.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    binding.btnSignIn.setVisibility(View.VISIBLE);

                    return null;
                }
            }

        };
        stringRequest.setShouldCache(false);

        Volley.newRequestQueue(this).add(stringRequest);


    }

    public void loadData() {

        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3, R.string.ob_header3};
        int[] desc = {R.string.ob_desc1, R.string.ob_desc2, R.string.ob_desc3};
        int[] imageId = {R.drawable.on_boed_one, R.drawable.on_bord_two, R.drawable.on_bord_three};

        for (int i = 0; i < imageId.length; i++) {
            OnBoardItem item = new OnBoardItem();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));


            onBoardItems.add(item);
        }
    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            binding.viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.selected_item_dot));
    }


}