package dsdmsa.utm_news.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import dsdmsa.utm_news.R;
import dsdmsa.utm_news.utils.Navigator;

import static dsdmsa.utm_news.This.appComponent;

public class HomeWidget extends LinearLayout {

    @Inject
    Navigator navigator;

    public HomeWidget(Context context) {
        super(context);
    }

    public HomeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        appComponent.inject(this);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wiget_home, this, true);


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
}