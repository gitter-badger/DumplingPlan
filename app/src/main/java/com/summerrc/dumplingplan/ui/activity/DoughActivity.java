package com.summerrc.dumplingplan.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 和面界面
 */
public class DoughActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_dough;
    private ImageView iv_kettle;                //水壶
    private boolean tag = false;                //用户标识是否播放了动画

    @Override
    protected void setView() {
        setContentView(R.layout.activity_dough);
        initView();
        translateAnimationStart(findViewById(R.id.ll_tip_click_kettle), 10, 50, 800, Integer.MAX_VALUE, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_dough==null || bitmap_background_dough.isRecycled()) {
            bitmap_background_dough = BitmapFactory.decodeResource(getResources(), R.mipmap.background_dough);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_dough));
    }

    protected void initView() {
        super.initView();
        iv_kettle = (ImageView)findViewById(R.id.iv_kettle);
        iv_kettle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openRubActivity(this);
                break;
            case R.id.iv_kettle:
                kettleAnimatorSetStart();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_dough != null && !bitmap_background_dough.isRecycled()) {
            bitmap_background_dough.isRecycled();
            bitmap_background_dough = null;
            System.gc();
        }
    }

    /**
     * 水壶移动的动画，一边移动一边小角度旋转
     */
    private void kettleAnimatorSetStart() {
        if(tag) {
            return;
        }
        tag = true;
        translateAnimationStop(findViewById(R.id.ll_tip_click_kettle));
        findViewById(R.id.ll_tip_click_kettle).setVisibility(View.GONE);

        AnimatorSet animatorSet = new AnimatorSet();
        int x = (int)findViewById(R.id.iv_basin).getX() - (int)iv_kettle.getX();
        int y = (int)findViewById(R.id.iv_basin).getY() - (int)iv_kettle.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_kettle, "translationX", 0f , x-40);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_kettle, "translationY", 0f , y-320);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv_kettle, "rotation", 0f, 61f);
        animatorSet.play(anim1).with(anim2);
        animatorSet.play(anim3).after(anim2);
        animatorSet.setDuration(1000);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                findViewById(R.id.iv_stream).setVisibility(View.VISIBLE);
                final AnimationDrawable animation_frame = (AnimationDrawable) findViewById(R.id.iv_basin).getBackground();
                /** 在异步线程中执行启动动画的方法 */
                findViewById(R.id.iv_basin).post(new Runnable() {
                    @Override
                    public void run() {
                        animation_frame.start();   //启动动画
                    }
                });
                /** 计算动画播放的时间 */
                long duration = 0l;
                for (int i = 0; i < animation_frame.getNumberOfFrames(); i++) {
                    duration += animation_frame.getDuration(i);
                }
                /** 动画播放结束后下一步按钮显示 */
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        DoughActivity.super.hintToNext();
                    }
                }, duration);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

}
