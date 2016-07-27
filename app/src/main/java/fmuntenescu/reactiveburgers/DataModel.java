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
import rx.Subscriber;

/**
 * Model for the data.
 */
public class DataModel {

    @NonNull
    private static final String BUN_FILE = "/res/raw/buns.txt";
    @NonNull
    private static final List<Tomato> TOMATOES = new ArrayList<>(
            Arrays.asList(new Tomato(), new Tomato()));

    @NonNull
    public Observable<TomatoSlice> getTomatoSlice() {
        return Observable.from(TOMATOES)
                .flatMap(tomatoe -> Observable.from(tomatoe.getTomatoSlices())
                        .delay(3, TimeUnit.SECONDS));
    }

    /**
     * Generate buns based on the lines from the BUN_FILE.
     *
     * @return bun observable
     */
    @NonNull
    public Observable<Bun> getBun() {
        return getBunOccurenceFromFile()
                .delay(2, TimeUnit.SECONDS)
                .map(__ -> new Bun());
    }

    /**
     * @return observable containing the lines from the BUN_FILE.
     */
    @NonNull
    private Observable<String> getBunOccurenceFromFile() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                FileDataReader.readFileByLine(BUN_FILE, subscriber);
            }
        });
    }
}
