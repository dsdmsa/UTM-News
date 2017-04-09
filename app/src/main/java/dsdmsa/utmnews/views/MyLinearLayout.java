package dsdmsa.utmnews.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class MyLinearLayout extends LinearLayoutManager {

    private boolean isScrollEnabled;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public boolean isScrollEnabled() {
        return isScrollEnabled;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        isScrollEnabled = scrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled;
    }
}