package dsdmsa.utmnews.views.navigator;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dsdmsa.utmnews.R;

public class CollectionsBookmarkWidget extends BaseWidget {

    public CollectionsBookmarkWidget(Context context) {
        super(context);
    }

    public CollectionsBookmarkWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.startCollectionsBookmark();
            }
        });
    }

    @Override
    protected int getDrawable() {
        return R.drawable.ic_bookmarcs;
    }

    @Override
    protected String getText() {
        return "Bookmarks";
    }

    public CollectionsBookmarkWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
