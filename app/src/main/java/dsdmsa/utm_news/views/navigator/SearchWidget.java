package dsdmsa.utm_news.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utm_news.R;

public class SearchWidget extends BaseWidget {

    public SearchWidget(Context context) {
        super(context);
    }

    public SearchWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startSearchActivity();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_search;
    }

    @Override
    protected String getText() {
        return "Search";
    }

    public SearchWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
