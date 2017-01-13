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

public class AboutWidget extends LinearLayout {

    @Inject
    Navigator navigator;

    public AboutWidget(Context context) {
        super(context);
    }

    public AboutWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        appComponent.inject(this);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wiget_abaut, this, true);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startAbautActivity();
            }
        });
    }

    public AboutWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
