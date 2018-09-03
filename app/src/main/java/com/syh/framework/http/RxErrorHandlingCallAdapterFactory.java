package com.syh.framework.http;


import org.reactivestreams.Publisher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public RxErrorHandlingCallAdapterFactory(Scheduler scheduler) {
        original = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
    }

    public static CallAdapter.Factory create() {

        return new RxErrorHandlingCallAdapterFactory();
    }

    public static CallAdapter.Factory createWithScheduler(Scheduler scheduler) {
        return new RxErrorHandlingCallAdapterFactory(scheduler);
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = original.get(returnType, annotations, retrofit);
        if (callAdapter == null) {
            return null;
        }
        return new RxCallAdapterWrapper(retrofit, callAdapter);
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Retrofit retrofit;
        private final CallAdapter<R, ?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, ?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(Call<R> call) {
            Object o = wrapped.adapt(call);
            if (o == null) {
                return o;
            }
            if (o instanceof Observable) {
                Observable observable = (Observable) o;
                return observable.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Flowable) {
                Flowable flowable = (Flowable) o;
                return flowable.onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Single) {
                Single single = (Single) o;
                return single.onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Maybe) {
                Maybe maybe = (Maybe) o;
                return maybe.onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Completable) {
                Completable completable = (Completable) o;
                return completable.onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Throwable throwable) throws Exception {
                        return Completable.error(asRetrofitException(throwable));
                    }
                });
            } else {
                return o;
            }
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }
            // A network error happened
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.unexpectedError(throwable);
        }
    }

}
