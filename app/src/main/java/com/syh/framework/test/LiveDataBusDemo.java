package com.syh.framework.test;

import android.app.Activity;
import androidx.lifecycle.Observer;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.syh.framework.R;
import com.syh.framework.databinding.ActivityLiveDataBusDemoBinding;
import com.syh.framework.http.BaseSubscriber;
import com.syh.framework.util.LiveDataBus;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LiveDataBusDemo extends AppCompatActivity {

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, LiveDataBusDemo.class));
    }

    private ActivityLiveDataBusDemoBinding binding;
    private Observer<String> observer = s -> Toast.makeText(LiveDataBusDemo.this, s, Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_data_bus_demo);
        binding.setHandler(this);
        binding.setLifecycleOwner(this);
        LiveDataBus.get()
                .with("random_data", String.class)
                .observe(this, s -> Toast.makeText(LiveDataBusDemo.this, s, Toast.LENGTH_SHORT).show());
        LiveDataBus.get()
                .with("random_data1", String.class)
                .observeForever(observer);
        LiveDataBus.get()
                .with("test", String.class)
                .observe(this, s -> binding.tvName.setText(s));
        LiveDataBus.get()
                .with("close_all_page", Boolean.class)
                .observe(this, b -> {
                    if (b) {
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveDataBus.get()
                .with("random_data1", String.class)
                .removeObserver(observer);
    }

    public void sendMsg() {
        Observable.just(new Random())
                .map(random -> random.nextInt(100) + "")
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        LiveDataBus.get().with("random_data").setValue(s);
                    }
                });
    }

    public void sendMsg1() {
        Observable.just(new Random())
                .map(random -> random.nextInt(100) + "")
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        LiveDataBus.get().with("random_data1").setValue(s);
                    }
                });
    }

    public void sendMsgDelay5() {
        Observable.just(new Random())
                .map(random -> random.nextInt(100) + "")
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        LiveDataBus.get().with("test").setValue(s);
                    }
                });
    }

    public void newActivityAndFinish() {
        startActivity(new Intent(this, LiveDataBusDemo.class));
        finish();
    }

    public void newActivityNotFinish() {
        startActivity(new Intent(this, LiveDataBusDemo.class));
    }

    public void closeAll() {
        LiveDataBus.get().with("close_all_page").setValue(true);
    }
}