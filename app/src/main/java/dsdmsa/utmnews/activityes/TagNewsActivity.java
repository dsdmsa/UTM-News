package dsdmsa.utmnews.activityes;


import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.TagNewsFragment;
import dsdmsa.utmnews.utils.Constants;

public class TagNewsActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int tagId = getIntent().getExtras().getInt(Constants.TAG_ID);
        TagNewsFragment fragment = TagNewsFragment.newInstance(tagId);
        toolbarTitle.setText(fragment.getTitle());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
