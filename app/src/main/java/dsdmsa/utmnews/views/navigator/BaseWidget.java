package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.utils.Navigator;

import static dsdmsa.utmnews.This.appComponent;

public abstract class BaseWidget extends LinearLayout {

    @Inject
    Navigator navigator;

    protected ImageView mIcon;
    protected TextView mName;

    public BaseWidget(Context context) {
        super(context);
    }

    public BaseWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        appComponent.inject(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wiget_navigator, this, true);
        mIcon = (ImageView) this.findViewById(R.id.widget_icon);
        mName = (TextView) this.findViewById(R.id.widget_name);
        mIcon.setImageResource(getDrawable());
        mName.setText(getText());
    }

    protected abstract int getDrawable();

    protected abstract String getText();


    public BaseWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
