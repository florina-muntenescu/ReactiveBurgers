package fmuntenescu.reactiveburgers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fmuntenescu.reactiveburgers.pojo.Bun;
import fmuntenescu.reactiveburgers.pojo.Burger;
import fmuntenescu.reactiveburgers.pojo.TomatoSlice;
import rx.observers.TestSubscriber;
import rx.subjects.BehaviorSubject;

import static org.mockito.Mockito.*;

public class BurgersViewModelTest {

    private static final TomatoSlice TOMATOE_SLICE = new TomatoSlice();
    private static final Bun BUN = new Bun();

    @Mock
    private DataModel mDataModel;

    private BurgersViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new BurgersViewModel(mDataModel);
    }

    @Test
    public void getTomatoSliceStream_emits_whenDataModelEmits() {
        ArrangeBuilder builder = new ArrangeBuilder();
        TestSubscriber<TomatoSlice> testSubscriber = new TestSubscriber<>();
        mViewModel.getTomatoSliceStream().subscribe(testSubscriber);

        builder.withTomatoSlice(TOMATOE_SLICE);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(TOMATOE_SLICE);
    }

    @Test
    public void getBunStream_emits_whenDataModelEmits() {
        ArrangeBuilder builder = new ArrangeBuilder();
        TestSubscriber<Bun> testSubscriber = new TestSubscriber<>();
        mViewModel.getBunStream().subscribe(testSubscriber);

        builder.withBun(BUN);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(BUN);
    }

    @Test
    public void getBurgerStream_emits_whenBunTomatoSliceMeatFresh() {
        new ArrangeBuilder()
                .withBun(BUN)
                .withTomatoSlice(TOMATOE_SLICE);
        TestSubscriber<Burger> testSubscriber = new TestSubscriber<>();
        mViewModel.getBurgerStream().subscribe(testSubscriber);

        mViewModel.meatAvailable(true);

        testSubscriber.assertValueCount(1);
    }

    @Test
    public void getBurgerStream_doesNotEmit_whenMeatNotFresh() {
        new ArrangeBuilder()
                .withBun(BUN)
                .withTomatoSlice(TOMATOE_SLICE);
        TestSubscriber<Burger> testSubscriber = new TestSubscriber<>();
        mViewModel.getBurgerStream().subscribe(testSubscriber);

        mViewModel.meatAvailable(false);

        testSubscriber.assertNoValues();
    }

    @Test
    public void getBurgerStream_doesNotEmit_whenNoMeat() {
        new ArrangeBuilder()
                .withBun(BUN)
                .withTomatoSlice(TOMATOE_SLICE);
        TestSubscriber<Burger> testSubscriber = new TestSubscriber<>();
        mViewModel.getBurgerStream().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
    }

    @Test
    public void getBurgerStream_emitsOnce_whenMultipleTomatoSlices() {
        new ArrangeBuilder()
                .withBun(BUN)
                .withTomatoSlice(TOMATOE_SLICE)
                .withTomatoSlice(TOMATOE_SLICE);
        TestSubscriber<Burger> testSubscriber = new TestSubscriber<>();
        mViewModel.getBurgerStream().subscribe(testSubscriber);

        mViewModel.meatAvailable(true);

        testSubscriber.assertValueCount(1);
    }

    private class ArrangeBuilder {

        private BehaviorSubject<Bun> mBunSubject = BehaviorSubject.create();
        private BehaviorSubject<TomatoSlice> mTomatoSliceSubject = BehaviorSubject.create();

        public ArrangeBuilder() {
            when(mDataModel.getBunStream()).thenReturn(mBunSubject);
            when(mDataModel.getTomatoSliceStream()).thenReturn(mTomatoSliceSubject);
        }

        ArrangeBuilder withBun(Bun bun) {
            mBunSubject.onNext(bun);
            return this;
        }

        ArrangeBuilder withTomatoSlice(TomatoSlice tomatoSlice) {
            mTomatoSliceSubject.onNext(tomatoSlice);
            return this;
        }
    }

}
