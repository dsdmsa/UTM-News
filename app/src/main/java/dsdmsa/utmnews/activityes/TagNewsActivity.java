package dsdmsa.utmnews.activityes;


import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.TagNewsFragment;

public class TagNewsActivity extends BaseActivity{

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();

        int tagId = getIntent().getExtras().getInt("id");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, TagNewsFragment.newInstance(tagId))
                .commit();
    }
}
