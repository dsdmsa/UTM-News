package dsdmsa.utmnews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.DetailsFragmentVP;
import dsdmsa.utmnews.presenters.DetailsFragmentPresenter;
import dsdmsa.utmnews.utils.Constants;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class DetailsFragmentFragment extends BaseFragment implements DetailsFragmentVP.View {

    @InjectPresenter
    DetailsFragmentPresenter presenter;

    @BindView(R.id.test_content)
    TextView textView;

    @BindView(R.id.test_image)
    ImageView imageView;

    private Post post;

    public static DetailsFragmentFragment newInstance(Post post) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.NEWS_DETAILS_KEY, post);
        DetailsFragmentFragment fragment = new DetailsFragmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_details;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        post = (Post) getArguments().getSerializable(Constants.NEWS_DETAILS_KEY);

        Glide.with(getContext())
                .load(post.getContent().getImageUrl())
                .centerCrop()
                .into(imageView);

        textView.setText(Html.fromHtml(post.getContent().getRendered()));

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showInfoMessage(String errorMsg) {

    }
}
