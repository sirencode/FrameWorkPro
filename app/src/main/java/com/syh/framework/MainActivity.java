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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.syh.framework.bind_life.LifeCycleManager;
import com.syh.framework.bind_life.LifeListener;
import com.syh.framework.http.Api.HomeApi;
import com.syh.framework.http.ApiFactory;
import com.syh.framework.http.BaseSubscriber;
import com.syh.framework.http.model.DataDemo;
import com.syh.framework.http.RetrofitException;
import com.syh.framework.http.ServerDomainType;
import com.syh.framework.http.model.HttpBaseResult;
import com.syh.framework.http.model.User;
import com.syh.framework.largeImage.LargeImageViewActivity;
import com.syh.framework.list.ListActivity;
import com.syh.framework.test.LiveDataBusDemo;
import com.syh.framework.test.SPActivity;
import com.syh.framework.thirdLib.ImageLoadUtil;
import com.syh.framework.util.BaseActivity;
import com.syh.framework.util.BaseDialog;
import com.syh.framework.util.ClickProxy;
import com.syh.framework.annotions.DataCheckManager;
import com.syh.framework.util.DialogBuild;
import com.syh.framework.util.FrameSpan;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.SecurityCheck;
import com.syh.framework.util.StringUtil;
import com.syh.framework.util.ToastUtil;
import com.syh.framework.util.UIParameter;
import com.syh.framework.util.toast.CustomToast;
import com.syh.framework.util.toast.ToastFactory;
import com.syh.framework.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView1;
    private ImageView imageView2;
    private int count;
    private ToneGenerator toneGenerator;
    private TextView textView;

    private HttpBaseResult<List<DataDemo>> dataDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_msg);
        imageView1 = findViewById(R.id.image1);
        imageView1.setOnClickListener(v -> {
            ApiFactory.getInstance().create(ServerDomainType.Home, HomeApi.class)
                    .getTest("yuantong", "11111111111")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<HttpBaseResult<List<DataDemo>>>() {
                        @Override
                        protected void onSuccess(HttpBaseResult<List<DataDemo>> s) {
                            ToastUtil.showToast(MainActivity.this, s.getMessage());
                            LogUtil.d("onSuccess", s.getMessage());
                            DataDemo dataDemo = null;
                            for (int i = 0; i < 100; i++) {
                                dataDemo = new DataDemo();
//                                dataDemo.setShenTest("shen" + i);
                                DataDemo.AAA aaa = new DataDemo.AAA();
                                User user = new User();
//                                user.setAaa(aaa);
                                aaa.setUsers(user);
                                List<User> users = new ArrayList<>();
                                users.add(user);
                                users.add(user);
                                dataDemo.setUsers(users);
                                dataDemo.setAaa(aaa);
                                s.getData().add(dataDemo);
                            }
                            DataCheckManager.checkValue(s.getData(), s.getRequest());
                            DataCheckManager.checkValue(s.getData().get(0), s.getRequest());
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
        findViewById(R.id.btn_start_sound).setOnClickListener(v -> playTone(MainActivity.this, 100));
        findViewById(R.id.btn_sub_string).setOnClickListener(v -> ToastUtil.showToast(this, StringUtil.getLengthSub("sshshshhshh设计师", 12)));
        findViewById(R.id.btn_clickproxy).setOnClickListener(new ClickProxy(10000) {
            @Override
            public void checkClick(View view) {
                ToastUtil.showToast(MainActivity.this, "clickproxy");
                ToastFactory.getInstance(getApplicationContext()).makeTextShow("clickproxy",Toast.LENGTH_LONG);
                bindLife();
                bindLife2();
            }
        });
        findViewById(R.id.btn_check_root).setOnClickListener(v -> ToastFactory.getInstance(getApplicationContext()).makeTextShow("root == " + SecurityCheck.chechRoot(),Toast.LENGTH_LONG));
        findViewById(R.id.btn_check_hook).setOnClickListener(v -> ToastUtil.showToast(this, "has hook app " + SecurityCheck.hocked(this)));
        findViewById(R.id.btn_setText).setOnClickListener(v -> textView.setText("hello"));
        findViewById(R.id.btn_largeimg).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LargeImageViewActivity.class)));
        findViewById(R.id.btn_scroll).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScrollerActivity.class)));
        findViewById(R.id.btn_drop).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DropdownAct.class)));
    }

    private void check() {
//        if (TextUtils.isEmpty(dataDemo.getData().getShenTest())) {
//            dataDemo.getData().setShenTest("shen");
//        } else {
//            dataDemo.getData().setShenTest(null);
//        }
        DataCheckManager.checkValue(dataDemo, "query");
    }

    private void bindLife() {
        LifeCycleManager.bindLife(this, new LifeListener() {
            @Override
            public void onStart() {
                LogUtil.d("LifeListener", "onStart");
            }

            @Override
            public void onStop() {
                LogUtil.d("LifeListener", "onStop");
            }

            @Override
            public void onCreate() {
                LogUtil.d("LifeListener", "onCreate");
            }

            @Override
            public void onPause() {
                LogUtil.d("LifeListener", "onPause");
            }

            @Override
            public void onResume() {
                LogUtil.d("LifeListener", "onResume");
            }

            @Override
            public void onDestroy() {
                LogUtil.d("LifeListener", "onDestroy");
            }

            @Override
            public void onFragmentHiddenChanged(boolean isHidden) {

            }
        });
    }

    private void bindLife2() {
        LifeCycleManager.bindLife(this, new LifeListener() {
            @Override
            public void onStart() {
                LogUtil.d("LifeListener2", "onStart");
            }

            @Override
            public void onStop() {
                LogUtil.d("LifeListener2", "onStop");
            }

            @Override
            public void onCreate() {
                LogUtil.d("LifeListener2", "onCreate");
            }

            @Override
            public void onPause() {
                LogUtil.d("LifeListener2", "onPause");
            }

            @Override
            public void onResume() {
                LogUtil.d("LifeListener2", "onResume");
            }

            @Override
            public void onDestroy() {
                LogUtil.d("LifeListener2", "onDestroy");
            }

            @Override
            public void onFragmentHiddenChanged(boolean isHidden) {

            }
        });
    }

    private void showDialog() {
        LogUtil.d("StringUtil", "StringUtil" + StringUtil.getLengthSub("shenyonghe是个1，硬性", 16));
        LogUtil.d("StringUtil", "StringUtil" + StringUtil.getLengthSub("shenyonghe是个1，硬性", 17));
        LogUtil.d("StringUtil", "StringUtil" + StringUtil.getLengthSub("shenyonghe是个1，硬性", 18));
        LogUtil.d("StringUtil", "StringUtil" + StringUtil.getLengthSub("shenyonghe是个1，硬性", 19));
        LogUtil.d("StringUtil", "StringUtil" + StringUtil.getLengthSub("shenyonghe是个1，硬性", 20));
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
        WebViewActivity.startWebAct("file:////android_asset/test.html", this, true);
    }

    private void playTone(Context context, int mediaFileRawId) {
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
