package fmuntenescu.reactiveburgers.pojo;

import android.support.annotation.NonNull;

/**
 * Model for the meat used to make a burger with dummy implementation for cooking the meat.
 */
public class Meat {

    private boolean isFresh;

    public Meat(final boolean isFresh) {
        this.isFresh = isFresh;
    }

    public boolean isFresh() {
        return isFresh;
    }

    @NonNull
    public Meat cook() {
        // dummy implementation
        return this;
    }
}
