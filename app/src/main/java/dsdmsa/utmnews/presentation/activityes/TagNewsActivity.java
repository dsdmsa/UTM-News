package dsdmsa.utmnews.presentation.activityes;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.R;

public class TagNewsActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayout() {
        return R.layout.activity_item;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        int tagId = getIntent().getExtras().getInt(Constants.TAG_ID);
//        TagNewsFragment fragment = TagNewsFragment.newInstance(tagId);
//        toolbarTitle.setText(fragment.getTitle());
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container, fragment)
//                .commit();
    }
}
