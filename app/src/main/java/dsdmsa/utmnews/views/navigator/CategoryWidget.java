package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utmnews.R;

public class CategoryWidget extends BaseWidget {

    public CategoryWidget(Context context) {
        super(context);
    }

    public CategoryWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startCategoryActivity();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_category;
    }

    @Override
    protected String getText() {
        return "Category";
    }

    public CategoryWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
