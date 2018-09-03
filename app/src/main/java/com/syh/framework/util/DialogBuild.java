package com.syh.framework.util;

import android.content.Context;

/**
 * Created bg shenyonghe on 2018/7/11.
 */
public class DialogBuild {
    private BaseDialog dialog;

    public DialogBuild(Context context) {
        this.dialog = new BaseDialog(context);
    }

    public DialogBuild setTitle(String title) {
        this.dialog.setTitle(title);
        return this;
    }

    public DialogBuild setContent(String content) {
        this.dialog.setContent(content);
        return this;
    }

    public DialogBuild setSigle(String single) {
        this.dialog.setSingle(single);
        return this;
    }

    public DialogBuild setTwo(String left, String right) {
        this.dialog.setTwo(left, right);
        return this;
    }

    public DialogBuild setClick(BaseDialog.DialogClick click) {
        this.dialog.setClick(click);
        return this;
    }

    public void show() {
        this.dialog.show();
    }
}
