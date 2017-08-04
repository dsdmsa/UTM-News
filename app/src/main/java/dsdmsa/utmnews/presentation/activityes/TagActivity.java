package dsdmsa.utmnews.presentation.activityes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.presentation.fragments.TagNewsFragment;


public class TagActivity extends BaseActivity {

    @BindView(R.id.tab_title)
    TextView tabTitle;

    public static Intent getTagIntent(Context context, Tag tag) {
        Intent intent = new Intent(context, TagActivity.class);
        intent.putExtra(Constants.TAG, tag);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag tag = getIntent().getParcelableExtra(Constants.TAG);
        tabTitle.setText(tag.getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, TagNewsFragment.newInstance(tag))
                .commit();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_tag;
    }

    @OnClick(R.id.iv_close)
    protected void onClose() {
        this.finish();
    }

}
