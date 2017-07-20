package dsdmsa.utmnews.presentation.activityes;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;

public class CategoryNewsActivity extends BaseActivity{

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
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int categoryId = getIntent().getExtras().getInt(Constants.CATEGORY_ID);
        CategoryNewsFragment fragment = CategoryNewsFragment.newInstance(categoryId);
        toolbarTitle.setText(fragment.getTitle());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
