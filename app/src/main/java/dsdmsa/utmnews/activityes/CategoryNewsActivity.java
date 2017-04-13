package dsdmsa.utmnews.activityes;


import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.CategoryNewsFragment;

public class CategoryNewsActivity extends BaseActivity{

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();

        int categoryId = getIntent().getExtras().getInt("id");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, CategoryNewsFragment.newInstance(categoryId))
                .commit();
    }
}
