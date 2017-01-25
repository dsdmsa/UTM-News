package dsdmsa.utm_news.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utm_news.R;

public class SettingsWidget extends BaseWidget {

    public SettingsWidget(Context context) {
        super(context);
    }

    public SettingsWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startSettings();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_settings;
    }

    @Override
    protected String getText() {
        return "Settings";
    }

    public SettingsWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
