package fmuntenescu.reactiveburgers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class BurgersActivity extends AppCompatActivity {

    @NonNull
    private BurgersViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burgers);

        mViewModel = new BurgersViewModel(getDataModel());
    }

    @NonNull
    private DataModel getDataModel() {
        return ((BurgerApplication) getApplication()).getDataModel();
    }
}
