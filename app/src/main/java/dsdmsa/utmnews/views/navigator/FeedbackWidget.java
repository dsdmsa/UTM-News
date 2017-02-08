package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utmnews.R;

public class FeedbackWidget extends BaseWidget {


    public FeedbackWidget(Context context) {
        super(context);
    }

    public FeedbackWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startFeedBack();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_feedback;
    }

    @Override
    protected String getText() {
        return "Feedback";
    }

    public FeedbackWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
