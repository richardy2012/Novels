package com.guuidea.inreading.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.guuidea.inreading.R;

/**
 * @author Garrett
 * @date 2018/11/29
 * contact me krouky@outlook.com
 */
public class MenuView extends FrameLayout {

    private boolean isShowing;
    private static final int ANIMATION_DURATION = 200;
    private static final String TRANSLATION_Y = "translationY";

    private final Toolbar mToolbar;
    private final FrameLayout mBottomMenu;

    public MenuView(@NonNull Context context) {
        this(context, null);
    }

    public MenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuView(@NonNull final Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.reader_menu_layout, this);
        mToolbar = findViewById(R.id.tool_bar);
        mBottomMenu = findViewById(R.id.bottom_menu);

        ((Activity) context).getWindow().getDecorView()
                .addOnLayoutChangeListener(new OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right,
                                               int bottom, int oldLeft, int oldTop,
                                               int oldRight, int oldBottom) {
                        ((Activity) context).getWindow().getDecorView()
                                .removeOnLayoutChangeListener(this);
                        mToolbar.setTranslationY(-mToolbar.getHeight());
                        mBottomMenu.setTranslationY(mBottomMenu.getHeight());
                        setVisibility(GONE);
                    }
                });
        setClickable(true);
    }

    private AnimatorSet mShowAnim;

    public void show() {
        setVisibility(View.VISIBLE);
        post(this::showAnim);
    }

    private void showAnim() {
        if (mShowAnim == null) {
            ObjectAnimator toolbarAnim = ObjectAnimator.ofFloat(mToolbar,
                    TRANSLATION_Y, -mToolbar.getHeight(), 0);
            ObjectAnimator bottomMenuAnim = ObjectAnimator.ofFloat(mBottomMenu,
                    TRANSLATION_Y, mBottomMenu.getHeight(), 0);
            mShowAnim = new AnimatorSet();
            mShowAnim.play(toolbarAnim).with(bottomMenuAnim);
            mShowAnim.setDuration(ANIMATION_DURATION);
            mShowAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isShowing = true;
                }
            });
        }
        if (!mShowAnim.isRunning()) {
            mShowAnim.start();
        }
    }

    private AnimatorSet mDismissAnim;

    public void dismiss() {
        if (mDismissAnim == null) {
            ObjectAnimator toolbarAnim = ObjectAnimator.ofFloat(mToolbar,
                    TRANSLATION_Y, -mToolbar.getHeight());
            ObjectAnimator bottomMenuAnim = ObjectAnimator.ofFloat(mBottomMenu,
                    TRANSLATION_Y, mBottomMenu.getHeight());
            mDismissAnim = new AnimatorSet();
            mDismissAnim.play(toolbarAnim).with(bottomMenuAnim);
            mDismissAnim.setDuration(ANIMATION_DURATION);
            mDismissAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.GONE);
                    isShowing = false;
                }
            });
        }
        if (!mDismissAnim.isRunning()) {
            mDismissAnim.start();
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    private boolean tempShowing;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempShowing = isShowing;
                break;
            case MotionEvent.ACTION_UP:
                if (tempShowing && isShowing) {
                    dismiss();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
