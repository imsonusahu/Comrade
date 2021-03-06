package com.comrade.comrade.fragments;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.activity.MainActivity;
import com.comrade.comrade.adapters.ImagesAdapter;
import com.comrade.comrade.databinding.FragmentEditProfileBinding;
import com.comrade.comrade.fragments.bottomSheet.AboutMeBtmSheetFragment;
import com.comrade.comrade.fragments.bottomSheet.BasicInfoBtmSheetFragment;
import com.comrade.comrade.fragments.bottomSheet.EducationInfoBtmSheetFragment;
import com.comrade.comrade.fragments.bottomSheet.WorkInfoBtmSheetFragment;
import com.comrade.comrade.models.MediaModel;
import com.comrade.comrade.utils.RecyclerTouchListener;
import com.comrade.comrade.volly.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class EditProfileFragment extends Fragment {

    private static final int MY_PERMISSION_STORAGE = 1;

    FragmentEditProfileBinding binding;
    View view;
    ArrayList<MediaModel> arrayList = new ArrayList<>();
    ImagesAdapter imagesAdapter;
    int havePosition;
    private QueryPreferences queryPreferences;

    //Image request code
    private final int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        view = binding.getRoot();

        queryPreferences = new QueryPreferences(getActivity());

        onClick();
        initView();

        arrayList.clear();

        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());
        arrayList.add(new MediaModel());

        setUserProfile();

        return view;

    }

    private void setUserProfile() {

        HashMap<String, String> user = queryPreferences.getUserDetail();

        Log.e("userInfo", "user phone number : " + user.get(queryPreferences.uid) + "");

        binding.userNameAge.setText(user.get(queryPreferences.name));
        binding.genderAndLocation.setText(user.get(queryPreferences.gender));
        binding.addMyWork.setText(user.get(queryPreferences.jobTitle));
        binding.myEducation.setText(user.get(queryPreferences.goToSchool));
        binding.honestlyWant.setText(user.get(queryPreferences.shareMood));
        binding.aboutMeId.setText(user.get(queryPreferences.aboutUs));

    }

    private void initView() {

        binding.rvEditProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvEditProfile.setHasFixedSize(true);
        imagesAdapter = new ImagesAdapter(getActivity(), arrayList);
        binding.rvEditProfile.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();

    }

    private void onClick() {

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        binding.rvEditProfile.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                binding.rvEditProfile, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                havePosition = position;
                Toast.makeText(getApplicationContext(), "Long clicked Position no is - " + havePosition, Toast.LENGTH_SHORT).show();
                permission();

            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(getApplicationContext(), "Long clicked Position no is - " + position, Toast.LENGTH_SHORT).show();

            }
        }));
        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permission();
            }
        });


        binding.basicInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BasicInfoBtmSheetFragment basicInfoBtmSheet = BasicInfoBtmSheetFragment.newInstance();
                basicInfoBtmSheet.show(getChildFragmentManager(), "Basic info ");
            }
        });

        binding.myWorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkInfoBtmSheetFragment workInfoBtmSheet = WorkInfoBtmSheetFragment.newInstance();
                workInfoBtmSheet.show(getChildFragmentManager(), "Work info");
            }
        });

        binding.myEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EducationInfoBtmSheetFragment education = EducationInfoBtmSheetFragment.newInstance();
                education.show(getChildFragmentManager(), "My Education");
            }
        });

        binding.aboutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AboutMeBtmSheetFragment aboutMeBtmSheetFragment = AboutMeBtmSheetFragment.newInstance();
                aboutMeBtmSheetFragment.show(getChildFragmentManager(), "About me");
            }
        });

        binding.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new MoreInfoFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left,
                        R.anim.slide_out_left);

                fragmentTransaction.replace(R.id.mainContainer, fragment).addToBackStack("").commitAllowingStateLoss();

            }
        });
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    //Requesting permission
    private void permission() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_STORAGE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            Toast.makeText(getApplicationContext(), "Need Permission to access and upload your Image", Toast.LENGTH_SHORT).show();

        } else {
            showFileChooser();

        }
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

                showFileChooser();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    //handling the image chooser activity result

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                //displaying selected image to imageview


                Log.e("img path", "image path here = " + imageUri.getPath());

                arrayList.add(new MediaModel(imageUri));

                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Err", "Error = " + e.getMessage());
            }
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(Bitmap bitmap) {

        //getting the tag from the edittext

        Log.e("uid", "User Id = " + queryPreferences.uid);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "https://comrade.live/api/uploadimage",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.e("VOLLY", "Response" + response.statusCode);

                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            /*if (response.statusCode==200){

                                addNotification();
                            }*/

                            Log.e("VOLLY", "Response" + obj);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLY", "Error response : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.persion)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}

