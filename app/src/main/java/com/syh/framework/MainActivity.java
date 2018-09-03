package com.syh.framework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.syh.framework.http.Api.HomeApi;
import com.syh.framework.http.ApiFactory;
import com.syh.framework.http.BaseSubscriber;
import com.syh.framework.http.RetrofitException;
import com.syh.framework.http.ServerDomainType;
import com.syh.framework.list.ListActivity;
import com.syh.framework.test.LiveDataBusDemo;
import com.syh.framework.test.SPActivity;
import com.syh.framework.thirdLib.ImageLoadUtil;
import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.BaseDialog;
import com.syh.framework.util.DialogBuild;
import com.syh.framework.util.FrameSpan;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.ToastUtil;
import com.syh.framework.util.UIParameter;
import com.syh.framework.util.WebViewActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView1;
    private ImageView imageView2;
    private int count;
    private ToneGenerator toneGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = findViewById(R.id.image1);
        imageView1.setOnClickListener(v -> {
            ApiFactory.getInstance().create(ServerDomainType.Home, HomeApi.class)
                    .getTest("yuantong", "11111111111")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<DataDemo>() {
                        @Override
                        protected void onSuccess(DataDemo s) {
                            ToastUtil.showToast(MainActivity.this, s.getMessage());
                            LogUtil.LogD("onSuccess", s.getMessage());
                        }

                        @Override
                        protected void onFailed(RetrofitException e) {
                            super.onFailed(e);
                        }
                    });
        });
        imageView2 = findViewById(R.id.image2);
        imageView2.setOnClickListener(v -> showDialog());
        ImageLoadUtil.loadFixSizeIV(this, "http://pic34.nipic.com/20131101/10633410_194949329000_2.png", 100, 100, imageView1);
        ImageLoadUtil.loadImageView(this, "http://d.lanrentuku.com/down/png/1712/if_christmass_holidays_celebrate/christmass_santa_slide.png", imageView2);

        TextView textView = findViewById(R.id.tv_msg);
        textView.setOnClickListener(v -> startWebAct());
        setText(textView);

        findViewById(R.id.tv_spact).setOnClickListener(v -> startSpAct());
        findViewById(R.id.tv_list).setOnClickListener(v -> startListAct());
        findViewById(R.id.btn_shadow).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShadowAct.class)));
        findViewById(R.id.btn_show_databus).setOnClickListener(v -> LiveDataBusDemo.start(MainActivity.this));
        findViewById(R.id.btn_start_sound).setOnClickListener(v -> playTone(MainActivity.this,100));
    }


    private void showDialog() {
        new DialogBuild(this).setTitle("是否删除？")
                .setContent("删除的视频无法再恢复")
//                .setSigle("确定")
                .setTwo("取消", "确定")
                .setClick(new BaseDialog.DialogClick() {
                    @Override
                    public void onSingleClick() {

                    }

                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {

                    }
                })
                .show();
    }

    private void startSpAct() {
        startActivity(new Intent(this, SPActivity.class));
    }

    private void startListAct() {
        startActivity(new Intent(this, ListActivity.class));
    }

    private void startWebAct() {
        WebViewActivity.startWebAct("https://blog.csdn.net/carson_ho/article/details/52693322", this);
    }

    private  void playTone(Context context, int mediaFileRawId) {
        try {
            if (toneGenerator == null) {
                toneGenerator = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, ToneGenerator.MAX_VOLUME);
            }
            toneGenerator.startTone(mediaFileRawId, 200);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (toneGenerator != null) {
                        Log.d(TAG, "ToneGenerator released");
                        toneGenerator.release();
                        toneGenerator = null;
                    }
                }

            }, 200);
        } catch (Exception e) {
            Log.d(TAG, "Exception while playing sound:" + e);
        }
    }

    private void setText(TextView text) {
        String imgStr = "讲师 : 苹果在WWDC上推出了全新的iOS12操作系统，所有支持iOS11的用户将免费更新iOS12。:苹果称，iOS 12专注于提高iPhone 6等旧版设备的速度。";
        SpannableString spannableStringImg = new SpannableString(imgStr);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableStringImg.setSpan(colorSpan, 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringImg.setSpan(new FrameSpan(Color.parseColor("#ff8700"), UIParameter.dp2px(getBaseContext(), 10)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableStringImg);
    }

}
