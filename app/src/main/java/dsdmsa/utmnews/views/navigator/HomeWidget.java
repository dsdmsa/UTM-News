package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utmnews.R;

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

    public HomeWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected int getDrawable() {
        return R.drawable.ic_home_black ;
    }

    @Override
    protected String getText() {
        return "Home";
    }

}
