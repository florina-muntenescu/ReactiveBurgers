package fmuntenescu.reactiveburgers.pojo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model for a tomato. Contains a random number of tomato slices.
 */
public class Tomato {

    @NonNull
    private List<TomatoSlice> mTomatoSlices;

    public Tomato() {
        // create a random number of slices for a tomato.
        int slices = new Random().nextInt(2) + 1;

        mTomatoSlices = new ArrayList<>(slices);
        for (int i = 0; i < slices; i++) {
            mTomatoSlices.add(new TomatoSlice());
        }
    }

    @NonNull
    public List<TomatoSlice> getTomatoSlices() {
        return mTomatoSlices;
    }
}
