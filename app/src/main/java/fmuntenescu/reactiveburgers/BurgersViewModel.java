package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;

import fmuntenescu.reactiveburgers.pojo.Bun;
import fmuntenescu.reactiveburgers.pojo.Burger;
import fmuntenescu.reactiveburgers.pojo.Meat;
import fmuntenescu.reactiveburgers.pojo.TomatoSlice;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * View Model for the burgers activity.
 */
public class BurgersViewModel {

    @NonNull
    private DataModel mDataModel;

    @NonNull
    private BehaviorSubject<Meat> mMeatSubject = BehaviorSubject.create();

    public BurgersViewModel(@NonNull final DataModel dataModel) {
        mDataModel = dataModel;
    }

    @NonNull
    public Observable<TomatoSlice> getTomatoSlice() {
        return mDataModel.getTomatoSlice();
    }

    @NonNull
    public Observable<Bun> getBun() {
        return mDataModel.getBun();
    }

    @NonNull
    public Observable<Meat> getMeat() {
        return mMeatSubject.asObservable();
    }

    @NonNull
    private Observable<Meat> getCookedMeat() {
        return getMeat().filter(Meat::isFresh)
                .map(Meat::cook);
    }

    @NonNull
    public Observable<Burger> getBurger() {
        return Observable.zip(getBun(),
                getCookedMeat(),
                getTomatoSlice(),
                this::makeBurger);
    }

    private Burger makeBurger(final Bun bun, final Meat meat, final TomatoSlice tomato) {
        return new Burger(bun, meat, tomato);
    }

    public void meatAvailable(final boolean isFresh) {
        mMeatSubject.onNext(new Meat(isFresh));
    }

}
