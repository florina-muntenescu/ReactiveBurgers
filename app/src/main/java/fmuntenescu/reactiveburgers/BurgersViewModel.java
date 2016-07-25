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
    private Observable<Meat> getMeatObservable() {
        return mMeatSubject.asObservable();
    }

    @NonNull
    public Observable<Meat> getCookedMeat() {
        return getMeatObservable().filter(meat -> isMeatFresh(meat))
                                  .map(meat -> cook(meat));
    }

    @NonNull
    public Observable<Burger> getBurger() {
        return Observable.zip(getBun(),
                              getCookedMeat(),
                              getTomatoSlice(),
                              (bun, meat, tomato) -> makeBurger(bun, meat, tomato));
    }

    private Burger makeBurger(final Bun bun, final Meat meat, final TomatoSlice tomato) {
        return new Burger(bun, meat, tomato);
    }

    public void meatAvailable() {
        mMeatSubject.onNext(new Meat());
    }

    private boolean isMeatFresh(Meat meat) {
        return true;
    }

    private Meat cook(Meat meat) {
        return meat;
    }
}
