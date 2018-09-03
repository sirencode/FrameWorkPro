package com.syh.framework.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.syh.framework.R;


public class BaseDialog extends Dialog implements View.OnClickListener {

    private TextView titleView;
    private TextView contentView;
    private TextView singleBtn;
    private TextView leftBtn;
    private TextView rightBtn;

    private String title;
    private String content;
    private String single;
    private String left;
    private String right;
    private DialogClick click;

    public BaseDialog(Context context) {
        super(context);
    }

    public void setClick(DialogClick click) {
        this.click = click;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base);

        setCanceledOnTouchOutside(false);
        Window dialogWindow = this.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            final double proportion = 0.8;
            layoutParams.width = (int) (UIParameter.getWidth(getContext()) * proportion);
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(layoutParams);
            dialogWindow.setBackgroundDrawableResource(R.drawable.bg_base_dialog);
            initView();
        }
    }

    private void initView() {
        titleView = findViewById(R.id.tv_title);
        contentView = findViewById(R.id.tv_content);
        singleBtn = findViewById(R.id.tv_single);
        leftBtn = findViewById(R.id.tv_left);
        rightBtn = findViewById(R.id.tv_right);
        refreshView();
        singleBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    public void setTitle(String title) {
        this.title = title;
        refreshView();
    }

    public void setSingle(String singleText) {
        this.single = singleText;
        refreshView();
    }

    public void setTwo(String left, String right) {
        this.left = left;
        this.right = right;
        refreshView();
    }

    public void setContent(String content) {
        this.content = content;
        refreshView();
    }

    private void refreshView() {
        if (titleView != null) {
            if (!TextUtils.isEmpty(title)) {
                titleView.setText(title);
            }

            if (!TextUtils.isEmpty(content)) {
                contentView.setText(content);
            }

            if (!TextUtils.isEmpty(content)) {
                contentView.setText(content);
            }

            if (!TextUtils.isEmpty(single)) {
                singleBtn.setVisibility(View.VISIBLE);
                singleBtn.setText(single);
            } else {
                singleBtn.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
                leftBtn.setVisibility(View.VISIBLE);
                rightBtn.setVisibility(View.VISIBLE);
                leftBtn.setText(left);
                rightBtn.setText(right);
            } else {
                leftBtn.setVisibility(View.GONE);
                rightBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (click != null) {
            int i = v.getId();
            dismiss();
            if (i == R.id.tv_single) {
                click.onSingleClick();
            }

            if (i == R.id.tv_left) {
                click.onLeftClick();
            }

            if (i == R.id.tv_right) {
                click.onRightClick();
            }
        }
    }

    public interface DialogClick {
        void onSingleClick();

        void onLeftClick();

        void onRightClick();
    }
}

