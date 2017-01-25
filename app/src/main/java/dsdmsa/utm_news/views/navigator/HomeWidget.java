package dsdmsa.utm_news.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utm_news.R;

public class HomeWidget extends BaseWidget {

    public HomeWidget(Context context) {
        super(context);
    }

    public HomeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startMainActivity();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_home_black ;
    }

    @Override
    protected String getText() {
        return "Home";
    }

    public HomeWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
