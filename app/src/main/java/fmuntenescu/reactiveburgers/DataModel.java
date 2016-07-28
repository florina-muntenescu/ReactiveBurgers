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
 * It creates a stream of tomato slices from a list of tomatoes.
 * It creates a bun stream based on the lines in a file that contain the word "bun".
 */
public class DataModel {

    @NonNull
    private static final String BUN_FILE = "/res/raw/buns.txt";

    @NonNull
    private static final List<Tomato> TOMATOES = new ArrayList<>(
            Arrays.asList(new Tomato(), new Tomato()));

    @NonNull
    public Observable<TomatoSlice> getTomatoSliceStream() {
        return Observable.from(TOMATOES)
                .flatMap(tomato -> Observable.from(tomato.getTomatoSlices()));
    }

    /**
     * Generate buns based on the lines from the BUN_FILE that contain the word "bun".
     *
     * @return the stream of buns
     */
    @NonNull
    public Observable<Bun> getBunStream() {
        return getBunOccurrenceFromFileStream()
                .map(__ -> new Bun());
    }

    /**
     * @return observable containing the lines from the BUN_FILE that contain the word "bun"
     */
    @NonNull
    private Observable<String> getBunOccurrenceFromFileStream() {
        return FileDataReader.readFileByLine(BUN_FILE)
                .filter(line -> line.contains("bun"));
    }
}
