package com.comrade.comrade.fragments.bottomSheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.comrade.comrade.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EducationInfoBtmSheetFragment extends BottomSheetDialogFragment {

    public static EducationInfoBtmSheetFragment newInstance() {
        EducationInfoBtmSheetFragment fragment = new EducationInfoBtmSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.education_info_btm_sheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }


}