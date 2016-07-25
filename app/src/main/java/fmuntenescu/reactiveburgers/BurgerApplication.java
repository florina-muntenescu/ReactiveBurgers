package fmuntenescu.reactiveburgers;

import android.app.Application;
import android.support.annotation.NonNull;

public class BurgerApplication extends Application {

    @NonNull
    private DataModel mDataModel;

    public BurgerApplication() {
        mDataModel = new DataModel();
    }

    @NonNull
    public DataModel getDataModel() {
        return mDataModel;
    }
}
