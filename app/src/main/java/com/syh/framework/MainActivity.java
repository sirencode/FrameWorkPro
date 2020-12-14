package com.syh.framework;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.syh.framework.anim.CardViewAnimActivity;
import com.syh.framework.bind_life.LifeCycleManager;
import com.syh.framework.bind_life.LifeListener;
import com.syh.framework.defense.DefenseActivity;
import com.syh.framework.frame_animation.AnimationActivity;
import com.syh.framework.http.Api.HomeV2Api;
import com.syh.framework.http.ApiFactory;
import com.syh.framework.http.BaseSubscriber;
import com.syh.framework.http.RetrofitException;
import com.syh.framework.http.ServerDomainType;
import com.syh.framework.http.model.DataDemo;
import com.syh.framework.http.model.HttpBaseResult;
import com.syh.framework.http.model.User;
import com.syh.framework.http.net_check.NetCheckForNetManager;
import com.syh.framework.http.net_check.NetCheckManager;
import com.syh.framework.http.net_check.gson_adapter.test.GsonTest;
import com.syh.framework.http.net_check.model.NetCheckBean;
import com.syh.framework.img.ImageConfig;
import com.syh.framework.img.ImageLoaderHelp;
import com.syh.framework.largeImage.LargeImageViewActivity;
import com.syh.framework.list.ListActivity;
import com.syh.framework.test.LiveDataBusDemo;
import com.syh.framework.test.SPActivity;
import com.syh.framework.thirdLib.ImageLoadUtil;
import com.syh.framework.util.BaseDialog;
import com.syh.framework.util.BaseTimerActivity;
import com.syh.framework.util.ClickProxy;
import com.syh.framework.util.DialogBuild;
import com.syh.framework.util.FrameSpan;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.NativeLoadePathUtil;
import com.syh.framework.util.SecurityCheck;
import com.syh.framework.util.StringUtil;
import com.syh.framework.util.ToastUtil;
import com.syh.framework.util.UIParameter;
import com.syh.framework.util.toast.ToastFactory;
import com.syh.framework.view.FloatingLogViewService;
import com.syh.framework.view.FloatingViewManager;
import com.syh.framework.view.state_layout.StateLayActivity;
import com.syh.framework.view.state_layout.StateLayWithFragmentActivity;
import com.syh.framework.web.WebViewActivity;
import com.syh.framework.web_p_error.ActivityOne;
import com.syh.plugin.annotation.Cost;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseTimerActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView1;
    private ImageView imageView2;
    private ToneGenerator toneGenerator;
    private TextView textView;
    private TextView timeView;

    private Long actionTime = 1607702400000L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_msg);
        timeView = findViewById(R.id.tv_time);
        imageView1 = findViewById(R.id.image1);
        imageView1.setOnClickListener(v -> {
            ApiFactory.getInstance().create(ServerDomainType.Home, HomeV2Api.class)
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

                            NetCheckManager.INSTANCE.checkValue(s.getData(), s.getRequest());
                            NetCheckManager.INSTANCE.checkValue(s.getData().get(0), s.getRequest());
                        }

                        @Override
                        protected void onFailed(RetrofitException e) {
                            super.onFailed(e);
                        }
                    });
        });
        imageView2 = findViewById(R.id.image2);
        imageView2.setOnClickListener(v -> showDialog());
//        ImageLoadUtil.loadFixSizeIV(this, "http://d.lanrentuku.com/down/png/1712/if_christmass_holidays_celebrate/christmass_santa_slide.png", 100, 100, imageView1);
        ImageLoadUtil.loadImageView(this, "http://d.lanrentuku.com/down/png/1712/if_christmass_holidays_celebrate/christmass_santa_slide.png", imageView2);
        ImageLoaderHelp.Companion.displayImg(imageView1, "http://d.lanrentuku.com/down/png/1712/if_christmass_holidays_celebrate/christmass_santa_slide.png", null, ImageConfig.Companion.getImageConfig1());

        TextView textView = findViewById(R.id.tv_msg);
        textView.setOnClickListener(v -> startWebAct());
        setText(textView);

        findViewById(R.id.tv_spact).setOnClickListener(v -> startSpAct());
        findViewById(R.id.tv_list).setOnClickListener(v -> startListAct());
        findViewById(R.id.btn_shadow).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShadowAct.class)));
        findViewById(R.id.btn_time_wheel).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TimeWheelAct.class)));
        findViewById(R.id.btn_show_databus).setOnClickListener(v -> LiveDataBusDemo.start(MainActivity.this));
        findViewById(R.id.btn_start_sound).setOnClickListener(v -> playTone(MainActivity.this, 100));
        findViewById(R.id.btn_sub_string).setOnClickListener(v -> ToastUtil.showToast(this, StringUtil.getLengthSub("sshshshhshh设计师", 12)));
        findViewById(R.id.btn_clickproxy).setOnClickListener(new ClickProxy(10000) {
            @Override
            public void checkClick(View view) {
                ToastUtil.showToast(MainActivity.this, "clickproxy");
                ToastFactory.getInstance(getApplicationContext()).makeTextShow("clickproxy", Toast.LENGTH_LONG);
                bindLife();
                bindLife2();
            }
        });
        findViewById(R.id.btn_check_root).setOnClickListener(v -> ToastFactory.getInstance(getApplicationContext()).makeTextShow("root == " + SecurityCheck.chechRoot(), Toast.LENGTH_LONG));
        findViewById(R.id.btn_check_hook).setOnClickListener(v -> ToastUtil.showToast(this, "has hook app " + SecurityCheck.hocked(this)));
        findViewById(R.id.btn_setText).setOnClickListener(v -> setTextViewText("hello"));
        findViewById(R.id.btn_largeimg).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LargeImageViewActivity.class)));
        findViewById(R.id.btn_scroll).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScrollerActivity.class)));
        findViewById(R.id.btn_drop).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DropdownAct.class)));
        findViewById(R.id.btn_check_nolive).setOnClickListener(v -> checkLive());
        findViewById(R.id.btn_load_so).setOnClickListener(v -> showSoLoad());
        findViewById(R.id.btn_float_window).setOnClickListener(v -> showFW());

        findViewById(R.id.btn_direction).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DirectAct.class)));
        findViewById(R.id.btn_defense).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DefenseActivity.class)));
        findViewById(R.id.btn_extend).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExtendAct.class)));
        findViewById(R.id.btn_animation).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AnimationActivity.class)));
        findViewById(R.id.btn_state_lay).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StateLayActivity.class)));
        findViewById(R.id.btn_state_one_lay).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StateLayWithFragmentActivity.class)));
        findViewById(R.id.btn_activity_one).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityOne.class)));
        findViewById(R.id.btn_activity_stick_lay).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StickLayActivity.class)));
        findViewById(R.id.btn_activity_scroll_menu).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScrollMenuActivity.class)));
        findViewById(R.id.btn_activity_clean_screen).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClearScreenActivity.class)));
        findViewById(R.id.btn_activity_shape).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShapeActivity.class)));
        findViewById(R.id.btn_activity_shape1).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShapeAct.class)));
        findViewById(R.id.btn_activity_card_anim).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CardViewAnimActivity.class)));
        findViewById(R.id.btn_activity_opencv).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OpenCVAct.class)));
        findViewById(R.id.btn_activity_net_check).setOnClickListener(v -> checkNet());
        findViewById(R.id.btn_activity_gson).setOnClickListener(v -> testGson());
    }

    private void checkNet() {
        DataDemo dataDemo = new DataDemo();
        dataDemo.setCom("com");
        dataDemo.setNu("nu");
        dataDemo.setMessage("");
        DataDemo.AAA aaa = new DataDemo.AAA();
        aaa.setName("name");
        User user = new User();
        user.setAge(12);
        List<User> users = new ArrayList<>();
        users.add(user);
        dataDemo.setUsers(users);
        dataDemo.setAaa(aaa);
        String jsonStr = JSONObject.toJSONString(dataDemo);
        try {
            org.json.JSONObject object = new org.json.JSONObject(jsonStr);
            NetCheckBean checkBean = new NetCheckBean();
            checkBean.setRequestUrl("/api/sku_detail");
            List<String> list = new ArrayList<>();
            list.add("com");
            list.add("message");
            list.add("aaa.age");
            list.add("users.phoneNum");
            list.add("users.age");
            checkBean.setParams(list);
            NetCheckForNetManager.INSTANCE.checkValue(object, "http://www.95fenapp.com/api_goods/skuDetail/v1.0?goods_id=YKp8k2NKJ&sn=HomeThreeCList&timestamp=1599037009824&token=1bc6e18a4ef672acc78c8237b6d7c716", checkBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showFW() {
        FloatingViewManager.INSTANCE.start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatingLogViewService.class));
            }
        }
    }

    private void showSoLoad() {
        NativeLoadePathUtil.installSoDir(this);
        SoloadDialog dialog = new SoloadDialog();
        dialog.show(getFragmentManager(), "soload");
        dialog.setCancelable(false);
    }

    private void checkLive() {
        int alwaysFinish = Settings.Global.getInt(getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            Dialog dialog = null;
            dialog = new AlertDialog.Builder(this)
                    .setMessage(
                            "由于您已开启'不保留活动',导致i呼部分功能无法正常使用.我们建议您点击左下方'设置'按钮,在'开发者选项'中关闭'不保留活动'功能.")
                    .setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss()).setPositiveButton("设置", (dialog12, which) -> {
                        Intent intent = new Intent(
                                Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                        startActivity(intent);
                    }).create();
            dialog.show();

        }
    }

    public void setTextViewText(String string) {
        textView.setText(string);
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

    @Cost
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Cost
    private void testGson() {
        GsonTest.gsonTest();
        for (int i = 0; i < 100; i++) {
            System.out.println(new ExposeBean(
                    "8C6BA11C-A792-9D1A-0C34-1FDE90DF30C5.goodsDetail.shoeHome.listGoodsItem.1..E4KgDZr9W.3.1.695_1159.1.12",
                    "8C6BA11C-A792-9D1A-0C34-1FDE90DF30C5.goodsDetail.shoeHome.listGoodsItem.1..E4KgDZr9W.3.1.695_1159.1.12"
            ).toString());
        }
    }

    @Cost
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LaunchRecord.Companion.endRecord("onWindowFocusChanged");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTimerSecond() {
        System.out.println(this.getClass().getSimpleName() + ":" + System.currentTimeMillis());
    }
}
