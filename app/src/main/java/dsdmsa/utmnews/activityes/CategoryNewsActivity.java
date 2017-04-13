package dsdmsa.utmnews.activityes;


import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.utils.Constants;

public class CategoryNewsActivity extends BaseActivity{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int categoryId = getIntent().getExtras().getInt(Constants.CATEGORY_ID);
        CategoryNewsFragment fragment = CategoryNewsFragment.newInstance(categoryId);
        toolbarTitle.setText(fragment.getTitle());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
