package com.syh.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.syh.framework.util.NativeLoadePathUtil;
import com.syh.framework.util.ZipUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jacksgong on 12/21/15.
 */
public class SoLoadActivity extends AppCompatActivity {

    private static final String LIULISHUO_ZIP_URL = "https://wos2.58cdn.com.cn/DeFazYxWvDti/frsupload/1ea70176129bad01cde20cd3625b43d5_lib.zip";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soload);
        llsZipFilePath = NativeLoadePathUtil.getZipPath(this) + File.separator +
                LIULISHUO_ZIP_FILENAME;
        initViews();
    }

    private void initViews() {
        startBtn = findViewById(R.id.start_btn_1);
        pauseBtn = findViewById(R.id.pause_btn_1);
        deleteBtn = findViewById(R.id.delete_btn_1);
        unZipBtn = findViewById(R.id.btn_unzip);
        checkBtn = findViewById(R.id.btn_check);
        filenameTv = findViewById(R.id.filename_tv_1);
        speedTv = findViewById(R.id.speed_tv_1);
        progressBar = findViewById(R.id.progressBar_1);

        startBtn.setOnClickListener(v -> downloadId1 = createDownloadTask().start());
        unZipBtn.setOnClickListener(v -> unzip());
        checkBtn.setOnClickListener(v -> check());
        pauseBtn.setOnClickListener(v -> FileDownloader.getImpl().pause(downloadId1));
        deleteBtn.setOnClickListener(v -> {
            new File(llsZipFilePath).delete();
            new File(FileDownloadUtils.getTempPath(llsZipFilePath)).delete();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().pause(downloadId1);
    }

    private void unzip() {
        try {
            ZipUtil.unzip(llsZipFilePath, NativeLoadePathUtil.getZipPath(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void check() {
        try {
            System.loadLibrary("libarvopencv4.so");
            toast("加载成功");
        } catch (UnsatisfiedLinkError e) {
            toast("加载失败");
            e.printStackTrace();
        }
    }

    private BaseDownloadTask createDownloadTask() {
        return FileDownloader.getImpl().create(LIULISHUO_ZIP_URL)
                .setPath(llsZipFilePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        if (filenameTv != null) {
                            filenameTv.setText(task.getFilename());
                        }
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
                        e.printStackTrace();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        if (filenameTv != null) {
                            filenameTv.setText(task.getFilename());
                        }
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
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        toast("warn");
                        progressBar.setIndeterminate(false);
                    }
                });
    }

    private void updateSpeed(int speed) {
        speedTv.setText(String.format("%dKB/s", speed));
    }

    private void toast(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
