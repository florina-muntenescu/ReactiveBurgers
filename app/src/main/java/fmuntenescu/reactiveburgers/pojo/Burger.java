package fmuntenescu.reactiveburgers.pojo;

import android.support.annotation.NonNull;

/**
 * Model for a burger. A burger contains a bun, a piece of meat and a tomato slice.
 */
public class Burger {

    @NonNull
    private Bun mBun;

    @NonNull
    private Meat mMeat;

    @NonNull
    private TomatoSlice mTomatoSlice;

    public Burger(@NonNull final Bun bun,
                  @NonNull final Meat meat,
                  @NonNull final TomatoSlice tomatoSlice) {
        mBun = bun;
        mMeat = meat;
        mTomatoSlice = tomatoSlice;
    }
}
