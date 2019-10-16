package com.chinalwb.are.styles;

import com.chinalwb.are.Constants;
import com.chinalwb.are.R;

import android.view.View;
import android.widget.ImageView;

public class ARE_Helper {

    /**
     * Updates the check status.
     *
     * @param areStyle
     * @param checked
     */
    public static void updateCheckStatus(IARE_Style areStyle, boolean checked) {
        areStyle.setChecked(checked);
        View imageView = areStyle.getImageView();
        if (imageView!=null) {
            if (checked)
                imageView.setBackgroundColor(imageView.getContext().getResources().getColor(R.color.filter_elected));
            else imageView.setBackgroundColor(Constants.UNCHECKED_COLOR);
        }
    }


}
