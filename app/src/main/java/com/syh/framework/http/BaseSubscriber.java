package com.syh.framework.http;

import com.syh.framework.util.DataCheckUtil;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.observers.DisposableObserver;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    @Override
    public final void onNext(T t) {
        if (isDisposed()) {
            return;
        }

        try {
            DataCheckUtil.checkValue(t);
            onSuccess(t);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            onFailed(RetrofitException.from(e));
        } finally {
            try {
                dispose();
            } catch (Throwable e1) {
                Exceptions.throwIfFatal(e1);
                onFailed(RetrofitException.from(e1));
            }
        }
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onComplete() {

    }

    @Override
    public final void onError(Throwable e) {
        if (isDisposed()) {
            return;
        }
        dispose();
        try {
            RetrofitException exception;
            if (e instanceof RetrofitException) {
                exception = (RetrofitException) e;
            } else {
                exception = RetrofitException.from(e);
            }
            onFailed(exception);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            onComplete();
        }
    }

    protected void onFailed(RetrofitException e) {

    }
}
