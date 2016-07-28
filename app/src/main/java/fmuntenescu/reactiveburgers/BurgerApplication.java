package fmuntenescu.reactiveburgers;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Application class. Allows injection of the data model.
 */
public class BurgerApplication extends Application {

    @NonNull
    private final DataModel mDataModel;

    public BurgerApplication() {
        mDataModel = new DataModel();
    }

    @NonNull
    public DataModel getDataModel() {
        return mDataModel;
    }
}
