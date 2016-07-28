package fmuntenescu.reactiveburgers;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import fmuntenescu.reactiveburgers.pojo.Bun;
import fmuntenescu.reactiveburgers.pojo.Burger;
import fmuntenescu.reactiveburgers.pojo.Meat;
import fmuntenescu.reactiveburgers.pojo.TomatoSlice;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * View that displays emissions of buns, meat, tomatoe slices and burgers.
 * The view allows creating meat pieces by pressing a button.
 */
public class BurgersActivity extends AppCompatActivity {

    @Nullable
    private BurgersViewModel mViewModel;

    @Nullable
    private LinearLayout mTomatoSlicesLayout;

    @Nullable
    private LinearLayout mMeatLayout;

    @Nullable
    private LinearLayout mBunLayout;

    @Nullable
    private LinearLayout mBurgerLayout;

    @NonNull
    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burgers);

        mViewModel = new BurgersViewModel(getDataModel());

        mTomatoSlicesLayout = (LinearLayout) findViewById(R.id.tomato_slice_layout);
        mMeatLayout = (LinearLayout) findViewById(R.id.meat_layout);
        mBunLayout = (LinearLayout) findViewById(R.id.bun_layout);
        mBurgerLayout = (LinearLayout) findViewById(R.id.burger_layout);

        findViewById(R.id.meat_button).setOnClickListener(__ -> meatAvailable());
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    private void bind() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(mViewModel.getTomatoSliceStream()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTomatoSlice));

        mSubscription.add(mViewModel.getBunStream()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setBun));

        mSubscription.add(mViewModel.getMeatStream()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMeat));

        mSubscription.add(mViewModel.getBurgerStream()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setBurger));
    }

    private void unBind() {
        mSubscription.unsubscribe();
    }

    @NonNull
    private DataModel getDataModel() {
        return ((BurgerApplication) getApplication()).getDataModel();
    }

    private void setTomatoSlice(@NonNull final TomatoSlice tomatoSlice) {
        assert mTomatoSlicesLayout != null;

        addImageToContainer(mTomatoSlicesLayout, R.drawable.tomatoe_cartoon);
    }

    private void setBun(@NonNull final Bun bun) {
        assert mBunLayout != null;

        addImageToContainer(mBunLayout, R.drawable.bun_white_bkgr);
    }

    private void setMeat(@NonNull final Meat meat) {
        assert mMeatLayout != null;

        @DrawableRes int meatImg = meat.isFresh() ?
                R.drawable.meat_raw_fresh : R.drawable.meat_raw_old;
        addImageToContainer(mMeatLayout, meatImg);
    }

    private void setBurger(@NonNull final Burger burger) {
        assert mBurgerLayout != null;

        addImageToContainer(mBurgerLayout, R.drawable.burger_cartoon);
    }

    private void addImageToContainer(@NonNull final LinearLayout container,
                                     @NonNull @DrawableRes final int imageSource) {

        int width = getResources().getDimensionPixelSize(R.dimen.image_max_width);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(width, WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageSource);
        container.addView(imageView, viewParams);
    }

    /**
     * Generate a new piece of meat with a random value of freshness.
     */
    private void meatAvailable() {
        boolean isFresh = new Random().nextBoolean();
        mViewModel.meatAvailable(isFresh);
    }
}
