package com.comrade.comrade.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {


    ActivityFilterBinding binding;

    String ftAndCm;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding= DataBindingUtil.setContentView(this,R.layout.activity_filter);




       onCLick();


       binding.btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               onBackPressed();
           }
       });
    }

    private void onCLick() {

/*        binding.heightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                binding.heightMsg.setText(progress+" cm");
                seekBar.setMax(198);
            }
        });*/


    /*    binding.distanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                binding.destMsg.setText(progress+" km");
                seekBar.setMax(150);
            }
        });*/
    }
}