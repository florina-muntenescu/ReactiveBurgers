package fmuntenescu.reactiveburgers.pojo;

/**
 * Model for the meat used to make a burger.
 */
public class Meat {

    private boolean isFresh;

    private boolean isCooked = false;

    public Meat(final boolean isFresh) {
        this.isFresh = isFresh;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public Meat cook() {
        isCooked = true;
        return this;
    }
}
