package com.comrade.comrade.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.comrade.comrade.SessionManager.SessionManager;
import com.comrade.comrade.databinding.ActivityOtpBinding;
import com.comrade.comrade.utils.SmsReceiver;
import com.comrade.comrade.volly.Variables;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG = "Otp Activity";
    ActivityOtpBinding binding;

    SessionManager sessionManager;

    String phoneNumber;
    String otp;

    QueryPreferences queryPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        queryPreferences = new QueryPreferences(this);
        sessionManager=new SessionManager(this);

        otp = getIntent().getStringExtra("otp");
        phoneNumber = getIntent().getStringExtra("phone");

        Log.e(TAG, "Phone number : " + phoneNumber + "");
        Log.e(TAG, "OTP : " + otp + "");

        onClick();
        otpReader();

    }

    private void otpReader() {


        SmsRetrieverClient client = SmsRetriever.getClient(this);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Android will provide message once receive. Start your broadcast receiver.
                IntentFilter filter = new IntentFilter();
                filter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
                registerReceiver(new SmsReceiver(), filter);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details

                Log.e("otp", "OTP READ ERROR : " + e.getMessage());
            }
        });
    }

    private void onClick() {

        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        binding.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyOtp(phoneNumber, binding.otpView.getOTP().toString());

              /*  Intent intent = new Intent(OtpActivity.this, BaseProfileActivity.class);
                startActivity(intent);*/

            }
        });
    }

    private void verifyOtp(String phoneNumber, String otp) {

        binding.otpView.setOTP(otp);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", phoneNumber);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Variables.OTP_VERIFY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("otp", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String otp_status = jsonObject.getJSONObject("obj").getJSONObject("data").getString("type");
                    String uId = jsonObject.getString("savedUser");

                    String checkUser = jsonObject.getString("userExist");

                    if (otp_status.matches("success")) {


                        Log.e("otp_verify", " OTP status " + otp_status);


                        if (checkUser.matches("true")) {

                            Log.e("otp_verify", " OTP status " + checkUser);

                            Intent intent = new Intent(OtpActivity.this, LocationActivity.class);
                            sessionManager.createSession(uId);
                            queryPreferences.setUid(uId);
                            startActivity(intent);

                        } else if (checkUser.matches("false")) {

                            Log.e("otp_verify", " OTP status " + checkUser);




                            sessionManager.createSession(uId);
                            Intent intent = new Intent(OtpActivity.this, BaseProfileActivity.class);
                            queryPreferences.setUid(uId);
                            startActivity(intent);

                        }


                    } else if (otp_status.matches("error")) {

                        Log.e("otp_verify", " OTP status " + otp_status);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.e("LOG_VOLLEY", e.getMessage().toString());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(this).add(stringRequest);


    }


}