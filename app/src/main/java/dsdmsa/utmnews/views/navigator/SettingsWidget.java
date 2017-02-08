package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utmnews.R;

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

    public SettingsWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_settings;
    }

    @Override
    protected String getText() {
        return "Settings";
    }
}
