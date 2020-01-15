package com.syh.framework;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.syh.framework.model.SoConfig;
import com.syh.framework.util.LogUtil;
import com.syh.framework.util.NativeLoadePathUtil;
import com.syh.framework.util.ZipUtil;

import org.jetbrains.annotations.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyonghe on 2020-01-02.
 */
public class SoloadDialog extends DialogFragment {

    private static final String LIULISHUO_ZIP_URL = "https://wos2.58cdn.com.cn/DeFazYxWvDti/frsupload/c61c177d40327bfba03b8f5e6b6f2b4b_lib.zip";
    private static final String LIULISHUO_ZIP_FILENAME = "lib.zip";

    private int downloadId1;
    private String llsZipFilePath;
    private Button startBtn;
    private Button pauseBtn;
    private Button deleteBtn;
    private Button unZipBtn;
    private Button checkBtn;
    private TextView filenameTv;
    private TextView speedTv;
    private ProgressBar progressBar;

    private static final String TAG = "SoloadDialog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflaterView = inflater.inflate(R.layout.activity_soload, container, false);
        initView(inflaterView);
        return inflaterView;
    }

    private void initView(View root) {
        startBtn = root.findViewById(R.id.start_btn_1);
        pauseBtn = root.findViewById(R.id.pause_btn_1);
        deleteBtn = root.findViewById(R.id.delete_btn_1);
        unZipBtn = root.findViewById(R.id.btn_unzip);
        checkBtn = root.findViewById(R.id.btn_check);
        filenameTv = root.findViewById(R.id.filename_tv_1);
        speedTv = root.findViewById(R.id.speed_tv_1);
        progressBar = root.findViewById(R.id.progressBar_1);
        llsZipFilePath = NativeLoadePathUtil.getZipPath(getActivity()) + File.separator +
                LIULISHUO_ZIP_FILENAME;
        startBtn.setOnClickListener(v -> start());
        unZipBtn.setOnClickListener(v -> unzip());
        checkBtn.setOnClickListener(v -> check());
        pauseBtn.setOnClickListener(v -> FileDownloader.getImpl().pause(downloadId1));
        deleteBtn.setOnClickListener(v -> {
            delete();
        });
        root.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());
        root.findViewById(R.id.btn_finish).setOnClickListener(v -> dismiss());
    }

    private void start() {
        startBtn.setClickable(false);
        new Thread(() -> {
            delete();
            downloadId1 = createDownloadTask().start();
        }).start();
    }

    private void delete() {
        try {
            new File(llsZipFilePath).delete();
            new File(FileDownloadUtils.getTempPath(llsZipFilePath)).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams attr = this.getDialog().getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        attr.width = (int) (280.0F * dm.density);
        this.getDialog().getWindow().setAttributes(attr);
    }

    private void unzip() {
        try {
            filenameTv.setText("完成下载，开始解压文件");
            ZipUtil.unzip(llsZipFilePath, NativeLoadePathUtil.getZipPath(getActivity()));
            filenameTv.setText("完成解压，可以使用对应功能");
        } catch (Exception e) {
            filenameTv.setText("解压失败，请重试");
            onFail();
            e.printStackTrace();
        }
        toast("完成");
        delete();
    }

    private void onFail() {
        startBtn.setClickable(true);
        toast("操作失败请重试");
    }

    private void check() {
//        try {
////            System.loadLibrary("c++_shared");
//            System.loadLibrary("arvopencv4");
//            toast("加载成功");
//        } catch (UnsatisfiedLinkError e) {
//            toast("加载失败");
//            e.printStackTrace();
//        }

//        String configPath = NativeLoadePathUtil.getZipPath(getActivity()) + File.separator + "/lib/config.json";
//        File configFile = new File(configPath);
//        if (configFile.exists()) {
//            SoConfig soConfig = readJsonFile(configPath);
//            if (soConfig != null) {
//                List<String> libs = soConfig.getLibs();
//                for (int i = 0; i < libs.size(); i++) {
////                        System.loadLibrary(libs.get(i));
//                }
//                toast("加载成功");
//            } else {
//                toast("加载失败");
//                LogUtil.e(TAG,"read config fail");
//            }
//            loadSo("arvopencv4");
//            loadSo("c++_shared");
//        } else {
//            toast("加载失败");
//            LogUtil.e(TAG,"config file not exist");
//        }
//        LogUtil.i(TAG, "injectSo end");


        try {
            String libPath = NativeLoadePathUtil.getZipPath(getActivity()) + File.separator + "/lib";
            File file = new File(libPath);
            if (file.exists() && file.isDirectory() && TextUtils.isEmpty(loadSoLibs(getSoLoadName(file.list())))) {
                LogUtil.i(TAG, "injectSo Success");
            } else {
                LogUtil.i(TAG, "injectSo fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "injectSo error" + e.getMessage());
        }
    }

    private List<String> getSoLoadName(String[] files) {
        List<String> list = new ArrayList<>();
        if (files == null || files.length == 0) return list;
        for (int i = 0; i < files.length; i++) {
            if (files[i].endsWith(".so") && files[i].length() > 6) {
                list.add(files[i].substring(3, files[i].length() - 3));
            }
        }
        return list;
    }

    private String loadSoLibs(List<String> list) {
        if (list == null || list.size() == 0) return null;
        LogUtil.i(TAG, "loadSoLibs" + list);
        int current = list.size();
        List<String> errorList = new ArrayList();
        for (String s : list) {
            String result = loadSo(s);
            if (!TextUtils.isEmpty(result)) {
                errorList.add(s);
            }
        }

        if (current != 0 && current == errorList.size()) {
            return "rely error";
        }

        if (errorList.size() > 0) {
            return loadSoLibs(errorList);
        }
        return null;
    }

    private String loadSo(String name) {
        String result = "";
        try {
            System.loadLibrary(name);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = TextUtils.isEmpty(e.getMessage()) ? "Exception" : e.getMessage();
        } catch (Throwable throwable) {
//            DevUtil.e(TAG, "injectSo error" + throwable.getMessage());
            result = TextUtils.isEmpty(throwable.getMessage()) ? "throwable" : throwable.getMessage();
        }
        return result;
    }

    private BaseDownloadTask createDownloadTask() {
        return FileDownloader.getImpl().create(LIULISHUO_ZIP_URL)
                .setPath(llsZipFilePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setAutoRetryTimes(3)
                .setListener(new FileDownloadSampleListener() {
                    //等待，已经进入下载队列
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        filenameTv.setText("开始下载" + task.getFilename());
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        if (totalBytes == -1) {
                            progressBar.setIndeterminate(true);
                        } else {
                            progressBar.setMax(totalBytes);
                            progressBar.setProgress(soFarBytes);
                        }
                        updateSpeed(task.getSpeed());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        toast(String.format("error %s", e));
                        updateSpeed(task.getSpeed());
                        progressBar.setIndeterminate(false);
                        filenameTv.setText("下载失败请重试");
                        onFail();
                        e.printStackTrace();
                    }

                    //已经连接上
                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        filenameTv.setText("开始下载" + task.getFilename());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        toast(String.format("paused"));
                        updateSpeed(task.getSpeed());
                        progressBar.setIndeterminate(false);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        toast(String.format("completed %s", task.getTargetFilePath()));
                        updateSpeed(task.getSpeed());
                        progressBar.setIndeterminate(false);
                        progressBar.setMax(task.getSmallFileTotalBytes());
                        progressBar.setProgress(task.getSmallFileSoFarBytes());
                        unzip();
                    }

                    //在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务
                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        toast("warn");
                        progressBar.setIndeterminate(false);
                        filenameTv.setText("下载失败请重试");
                        onFail();
                    }
                });
    }

    private void updateSpeed(int speed) {
        speedTv.setText(String.format("%dKB/s", speed));
    }

    private void toast(final String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commit();
    }

    public static SoConfig readJsonFile(String filePath) {
        BufferedReader reader = null;
        String readJson = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                readJson += tempString;
            }
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, e.getMessage());
                }
            }
        }

        // 获取json
        try {
            SoConfig soConfig = JSONObject.parseObject(readJson, SoConfig.class);
            return soConfig;
        } catch (JSONException e) {
            LogUtil.e(TAG, e.getMessage());
        }

        return null;
    }

}
