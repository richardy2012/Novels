package com.guuidea.inreading.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.guuidea.inreading.R;

/**
 *
 * @author Garrett
 * @date 2018/12/5
 * contact me krouky@outlook.com
 */
public class SettingView extends FrameLayout {

    private boolean isShowing;
    private final LinearLayout mSettingContainer;

    public SettingView(@NonNull Context context) {
        this(context, null);
    }

    public SettingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(@NonNull final Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.reader_setting_layout, this);
        mSettingContainer = findViewById(R.id.setting_container);

        ((Activity) context).getWindow().getDecorView()
                .addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ((Activity) context).getWindow().getDecorView().removeOnLayoutChangeListener(this);
                mSettingContainer.setTranslationY(mSettingContainer.getHeight());
                setVisibility(GONE);
            }
        });

        setOnClickListener(v -> dismiss());
    }

    public void dismiss() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(
                mSettingContainer,
                "translationY",
                mSettingContainer.getHeight());
        anim.setDuration(200).start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
                isShowing = false;
            }
        });
    }

    public void show() {
        setVisibility(View.VISIBLE);
        isShowing = true;
        post(() -> ObjectAnimator.ofFloat(
                mSettingContainer,
                "translationY",
                0)
                .setDuration(200).start());
    }

    public boolean isShowing() {
        return isShowing;
    }
}
