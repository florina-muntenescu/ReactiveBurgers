package fmuntenescu.reactiveburgers;

import android.support.annotation.NonNull;

import fmuntenescu.reactiveburgers.pojo.Bun;
import fmuntenescu.reactiveburgers.pojo.Burger;
import fmuntenescu.reactiveburgers.pojo.Meat;
import fmuntenescu.reactiveburgers.pojo.TomatoSlice;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * View Model for the BurgersActivity.
 * It provides the activity streams of bun, meat, pieces of tomato and burgers.
 * The activity notifies the view model when a new piece of meat is available.
 * In order to create the burger, the meat needs to be fresh and cooked. Then, the bun, the cooked
 * meat and the tomatoe slice will be zipped together to create the burger.
 */
public class BurgersViewModel {

    @NonNull
    private final DataModel mDataModel;

    @NonNull
    private final BehaviorSubject<Meat> mMeatSubject = BehaviorSubject.create();

    public BurgersViewModel(@NonNull final DataModel dataModel) {
        mDataModel = dataModel;
    }

    /**
     * @return a stream of tomato slices
     */
    @NonNull
    public Observable<TomatoSlice> getTomatoSliceStream() {
        return mDataModel.getTomatoSliceStream();
    }

    /**
     * @return a stream of buns
     */
    @NonNull
    public Observable<Bun> getBunStream() {
        return mDataModel.getBunStream();
    }

    /**
     * @return the stream of raw meat
     */
    @NonNull
    public Observable<Meat> getMeatStream() {
        return mMeatSubject.asObservable();
    }

    /**
     * To make the burger, we need the meat to be fresh and then cooked.
     *
     * @return cooked meat.
     */
    @NonNull
    private Observable<Meat> getCookedMeatStream() {
        return getMeatStream()
                .filter(Meat::isFresh)
                .map(Meat::cook);
    }

    /**
     * @return a stream of burgers created by zipping emissions of the bun stream, cooked meat
     * stream and tomato slices stream.
     */
    @NonNull
    public Observable<Burger> getBurgerStream() {
        return Observable.zip(getBunStream(),
                getCookedMeatStream(),
                getTomatoSliceStream(),
                this::makeBurger);
    }

    @NonNull
    private Burger makeBurger(@NonNull final Bun bun,
                              @NonNull final Meat meat,
                              @NonNull final TomatoSlice tomato) {
        return new Burger(bun, meat, tomato);
    }

    /**
     * Notifies that a new piece of meat is available.
     *
     * @param isFresh value indicating whether the meat is fresh or not
     */
    public void meatAvailable(final boolean isFresh) {
        mMeatSubject.onNext(new Meat(isFresh));
    }

}
