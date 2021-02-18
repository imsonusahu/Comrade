package com.comrade.comrade.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.adapters.ImagesAdapter;

import com.comrade.comrade.databinding.ActivityUploadBinding;
import com.comrade.comrade.models.MediaModel;
import com.comrade.comrade.utils.RecyclerTouchListener;
import com.comrade.comrade.volly.Variables;
import com.comrade.comrade.volly.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int REQUEST_PERMISSIONS = 111;
    private final Context CONTEXT = this;
    ActivityUploadBinding binding;
    ImagesAdapter imagesAdapter;
    private int havePosition;


    private static final int MY_PERMISSION_STORAGE = 1;


    ArrayList<MediaModel> arrayList = new ArrayList<>();

    private QueryPreferences queryPreferences;
    //Image request code


    //storage permission code


    Dialog dialog;
    private String filePath;
    private Map< String, String > user;
    private NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload);

        queryPreferences = new QueryPreferences(this);
        user = new HashMap<>();
        user = queryPreferences.getUserDetail();


        viewInit();
        onClick();

        dialogBox();


    }

    private void onClick() {

        binding.uploadImgVidRv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                binding.uploadImgVidRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                havePosition = position;

                permission();

            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(getApplicationContext(), "Long clicked Position no is - " + position, Toast.LENGTH_SHORT).show();

                dialog.show();

                havePosition = position;
            }
        }));


        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permission();
            }
        });


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UploadActivity.this,FinishActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dialogBox() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.remove_or_replace_imge);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button deleteBtn = dialog.findViewById(R.id.deleteBtn);
        Button replaceBtn = dialog.findViewById(R.id.replaceBtn);

        replaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
                imagesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList.remove(havePosition);
                imagesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void viewInit() {

        binding.uploadImgVidRv.setLayoutManager(new GridLayoutManager(this, 3));
        binding.uploadImgVidRv.setHasFixedSize(true);
        binding.uploadImgVidRv.setItemViewCacheSize(10);
        imagesAdapter = new ImagesAdapter(this, arrayList);
        binding.uploadImgVidRv.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();


    }

    private void permission() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(UploadActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            showFileChooser();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_PERMISSIONS) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(UploadActivity.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

                showFileChooser();
            } else {
                //Displaying another toast if permission is not granted
                permission();
                Toast.makeText(UploadActivity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //handling the image chooser activity result


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(UploadActivity.this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                Log.e("img path", "image path here = " + imageUri.getPath());

                arrayList.add(new MediaModel(imageUri));
                imagesAdapter.notifyDataSetChanged();

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

        //getting the tag from the edittex

        Log.e("uid", "User Id = " + queryPreferences.uid);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Variables.UPLOAD_USER_IMG,
                new Response.Listener< NetworkResponse >() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if (response.statusCode == 200) {

                                Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_LONG).show();
                            }

                            Log.e("VOLLY", "Response" + obj);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),""+e.getMessage().toString(),Toast.LENGTH_LONG).show();
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
                params.put("uid",user.get(queryPreferences.uid));
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
        Volley.newRequestQueue(UploadActivity.this).add(volleyMultipartRequest);
        volleyMultipartRequest.setShouldCache(false);
    }
}
