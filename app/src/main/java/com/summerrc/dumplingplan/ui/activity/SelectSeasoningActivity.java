package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.config.MMApplication;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 选择调料界面
 */
public class SelectSeasoningActivity extends Activity implements View.OnClickListener{
    private Handler handler;
    private int num = 0;


    private Bitmap bitmap_background;

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background==null || bitmap_background.isRecycled()) {
            bitmap_background = BitmapFactory.decodeResource(getResources(), R.mipmap.soho_background_select_seasoning);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background != null && !bitmap_background.isRecycled()) {
            bitmap_background.recycle();
            bitmap_background = null;
            System.gc();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seasoning);
        initView();
        handler = new Handler();
    }

    private void initView() {
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
        setBackground();
        setVisible();
        findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMApplication.isSelected = !MMApplication.isSelected;
                setVisible();
            }
        });
    }

    private void setVisible() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_yingyue).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.iv_yingxiao).setVisibility(View.GONE);
            findViewById(R.id.iv_yingyue).setVisibility(View.GONE);
            findViewById(R.id.iv_yingxiao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinxiao = !MMApplication.isSelected_yinxiao;
                    if(MMApplication.isSelected_yinxiao) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });
            findViewById(R.id.iv_yingyue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinyue = !MMApplication.isSelected_yinyue;
                    if(MMApplication.isSelected_yinyue) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });

        }
    }

    private void setBackground() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
        } else {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_ONE);
                break;
            case R.id.iv_two:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_TWO);
                break;
            case R.id.iv_three:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_THREE);
                break;
            case R.id.iv_four:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_FOUR);
                break;
            case R.id.iv_five:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_FIVE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            FoodTypeManager.Food food = (FoodTypeManager.Food)data.getSerializableExtra(IntentConstant.SELECTED_FOOD);
            switch (food) {
                case O_ONE:
                    findViewById(R.id.iv_one).setVisibility(View.INVISIBLE);
                    break;
                case O_TWO:
                    findViewById(R.id.iv_two).setVisibility(View.INVISIBLE);
                    break;
                case O_THREE:
                    findViewById(R.id.iv_three).setVisibility(View.INVISIBLE);
                    break;
                case O_FOUR:
                    findViewById(R.id.iv_four).setVisibility(View.INVISIBLE);
                    break;
                case O_FIVE:
                    findViewById(R.id.iv_five).setVisibility(View.INVISIBLE);
                    break;
                default:
                    return;
            }
            num++;
            if(num == 1) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UIHelper.openStuffingActivity(SelectSeasoningActivity.this);
                    }
                }, 500);
            }
        }
    }
}
