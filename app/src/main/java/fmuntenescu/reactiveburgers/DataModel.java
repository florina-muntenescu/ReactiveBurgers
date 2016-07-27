package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fmuntenescu.reactiveburgers.pojo.Bun;
import fmuntenescu.reactiveburgers.pojo.Tomato;
import fmuntenescu.reactiveburgers.pojo.TomatoSlice;
import rx.Observable;

/**
 * Model for the data.
 */
public class DataModel {

    @NonNull
    private static final List<Tomato> TOMATOES = new ArrayList<>(
            Arrays.asList(new Tomato(), new Tomato()));

    @NonNull
    public Observable<TomatoSlice> getTomatoSlice() {
        return Observable.from(TOMATOES)
                .flatMap(tomatoe -> Observable.from(tomatoe.getTomatoSlices())
                        .delay(3, TimeUnit.SECONDS));
    }

    @NonNull
    public Observable<Bun> getBun() {
        return Observable.never();
    }

}
