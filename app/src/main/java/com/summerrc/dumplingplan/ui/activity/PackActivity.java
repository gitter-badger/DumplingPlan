package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.activion.listener.PlayAnimClickListener;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015/7/11 0011.
 *         description : 包馅界面
 */
public class PackActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pack);
        initView();
    }


    private void initView() {
        final Handler handler = new Handler();
        final SimpleDraweeView sdv_soho_pack = (SimpleDraweeView) findViewById(R.id.sdv_soho_pack);
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (anim != null) {
                    sdv_soho_pack.setOnClickListener(new PlayAnimClickListener(anim, handler, 1000,
                            new PlayAnimClickListener.AnimStopCallBack() {
                                @Override
                                public void afterAnimStop() {
                                    UIHelper.openPutActivity(PackActivity.this);
                                }
                            }));
                }
            }
        };

        Uri uri = Uri.parse("res://com.summerrc.dumplingplan/" + R.mipmap.soho_pack_gif);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)
                .setControllerListener(controllerListener)
                .build();

        /** 设置Controller */
        sdv_soho_pack.setController(controller);
    }

}
