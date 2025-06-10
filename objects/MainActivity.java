package com.example.objects;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View ball1, ball2;
    private TextView textAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball1 = findViewById(R.id.ball1);
        ball2 = findViewById(R.id.ball2);
        textAnimated = findViewById(R.id.textAnimated);

        animateBalls();
        animateText();
        animateBackground();
    }

    private void animateBalls() {
        // Ball 1 bergerak horizontal
        ObjectAnimator moveX = ObjectAnimator.ofFloat(ball1, "translationX", 0f, 800f);
        moveX.setDuration(2000);
        moveX.setRepeatCount(ValueAnimator.INFINITE);
        moveX.setRepeatMode(ValueAnimator.REVERSE);
        moveX.setInterpolator(new LinearInterpolator());

        // Ball 2 rotasi dan skala
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ball2, "rotation", 0f, 360f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ball2, "scaleX", 1f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ball2, "scaleY", 1f, 1.5f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(moveX, rotate, scaleX, scaleY);
        set.start();
    }

    private void animateText() {
        // Fade + warna + scale
        ObjectAnimator fade = ObjectAnimator.ofFloat(textAnimated, "alpha", 0f, 1f);
        fade.setDuration(2000);
        fade.setRepeatCount(ValueAnimator.INFINITE);
        fade.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textAnimated, "scaleX", 1f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textAnimated, "scaleY", 1f, 1.2f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setDuration(2000);
        scaleY.setDuration(2000);

        ObjectAnimator colorAnim = ObjectAnimator.ofObject(textAnimated,
                "textColor",
                new ArgbEvaluator(),
                Color.WHITE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
                Color.RED);
        colorAnim.setDuration(4000);
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet textSet = new AnimatorSet();
        textSet.playTogether(fade, scaleX, scaleY, colorAnim);
        textSet.start();
    }

    private void animateBackground() {
        final View root = findViewById(R.id.parentLayout);

        ValueAnimator bgAnim = ValueAnimator.ofObject(
                new ArgbEvaluator(),
                Color.BLACK,
                Color.DKGRAY,
                Color.BLUE,
                Color.BLACK);
        bgAnim.setDuration(6000);
        bgAnim.setRepeatCount(ValueAnimator.INFINITE);
        bgAnim.setRepeatMode(ValueAnimator.REVERSE);

        bgAnim.addUpdateListener(animation -> {
            int color = (int) animation.getAnimatedValue();
            root.setBackgroundColor(color);
        });

        bgAnim.start();
    }
}
