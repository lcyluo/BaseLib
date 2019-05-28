package com.lcy.base.core.widgets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import com.lcy.base.core.R;

/**
 * 加载进度条
 * <p/>
 * Created by lcy on 15/7/21.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context, R.style.BaseCoreLoadingDialog);
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
        @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.base_core_dialog_loading_progress, null);
        ProgressBar progressBar = contentView.findViewById(R.id.progress_bar);
        if (progressBar == null) return;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateDrawable(new CircularProgressDrawable(
                    context.getResources().getColor(R.color.base_core_color_primary),
                    context.getResources().getDimension(R.dimen.base_core_dp_2)));
        }
        setTint(progressBar, context.getResources().getColor(R.color.base_core_color_primary));

        setContentView(contentView);
        Window window = this.getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }
        this.setCanceledOnTouchOutside(false);
        //  this.setCancelable(false);
    }

    private static void setTint(@NonNull ProgressBar progressBar, @ColorInt int color) {
        ColorStateList sl = ColorStateList.valueOf(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(sl);
            progressBar.setSecondaryProgressTintList(sl);
            progressBar.setIndeterminateTintList(sl);
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (progressBar.getIndeterminateDrawable() != null) {
                progressBar.getIndeterminateDrawable().setColorFilter(color, mode);
            }
            if (progressBar.getProgressDrawable() != null) {
                progressBar.getProgressDrawable().setColorFilter(color, mode);
            }
        }
    }
}
