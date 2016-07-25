package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        return Observable.just(TOMATOES)
                         .flatMap(tomatoe -> getTomatoSlice());
    }

    @NonNull
    public Observable<Bun> getBun() {
        return Observable.never();
    }

}
