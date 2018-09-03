package com.syh.framework.mvp;

/**
 * Created bg shenyonghe on 2018/5/21.
 * M 数据相关，数据拉去，数据转换，存取
 * V UI更新
 * P 逻辑处理
 */
public class BasePresenter<T extends IModel, V extends IView> {
    private T model;
    private V view;

    public BasePresenter(T model, V view) {
        this.model = model;
        this.view = view;
    }

    public BasePresenter(V view) {
        this.view = view;
    }
}
