package com.mylibrary.networks.checknetwork;

import android.animation.Animator;
import android.content.Context;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class BannerView extends AppCompatTextView {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHOWN, SHOWING, HIDDEN, HIDING})
    @interface State {}
    static final int SHOWN = 100;
    static final int SHOWING = 101;
    static final int HIDDEN = 102;
    static final int HIDING = 103;

    @BannerView.State
    private int state;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setVisibility(GONE);
        state = HIDDEN;
    }

    public void show() {
        if (state == HIDING || state == HIDDEN) {
            state = SHOWING;
            clearAnimation();
            setVisibility(VISIBLE);
            setTranslationY(-getHeight());
            animate()
                    .translationY(0)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            state = SHOWN;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }
    }

    public void hide() {
        if (state == SHOWN || state == SHOWING) {
            state = HIDING;
            clearAnimation();
            animate()
                    .translationY(-getHeight())
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setVisibility(GONE);
                            state = HIDDEN;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
        }
    }
}
