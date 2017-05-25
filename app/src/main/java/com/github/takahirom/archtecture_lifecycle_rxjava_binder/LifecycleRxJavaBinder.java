package com.github.takahirom.archtecture_lifecycle_rxjava_binder;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;


public class LifecycleRxJavaBinder {
    public static <T> FlowableTransformer<T, T> applyFlowable(LifecycleOwner lifecycleOwner) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                LiveData<T> liveData = LiveDataReactiveStreams.fromPublisher(flowable);
                return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, liveData));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyObservable(LifecycleOwner lifecycleOwner, BackpressureStrategy strategy) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                LiveData<T> liveData = LiveDataReactiveStreams.fromPublisher(observable.toFlowable(strategy));
                return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, liveData)).toObservable();
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyObservable(LifecycleOwner lifecycleOwner) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                LiveData<T> liveData = LiveDataReactiveStreams.fromPublisher(observable.toFlowable(BackpressureStrategy.BUFFER));
                return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, liveData)).toObservable();
            }
        };
    }

    public static <T> SingleTransformer<T, T> applySingle(LifecycleOwner lifecycleOwner) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                LiveData<T> liveData = LiveDataReactiveStreams.fromPublisher(single.toFlowable());
                return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, liveData)).singleOrError();
            }
        };
    }

    public static <T> MaybeTransformer<T, T> applyMaybe(LifecycleOwner lifecycleOwner) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> single) {
                LiveData<T> liveData = LiveDataReactiveStreams.fromPublisher(single.toFlowable());
                return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, liveData)).firstElement();
            }
        };
    }
}
