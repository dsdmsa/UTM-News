package dsdmsa.utmnews.presentation.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.ClasificationVP;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class TagListFragment extends BaseFragment implements
        ClasificationVP.View,
        SwipeRefreshLayout.OnRefreshListener {

//    @InjectPresenter
//    ClassificationPresenter presenter;
//
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout refreshLayout;
//
//    private TagAdapter tagAdapter;
//
    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void onRefresh() {

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


    @Override
    public void showCategories(List<Category> response) {

    }

    @Override
    public void showTags(List<Tag> response) {

    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        tagAdapter = new TagAdapter(this);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setHasFixedSize(true);
//        refreshLayout.setOnRefreshListener(this);
//        recyclerView.setAdapter(tagAdapter);
//        presenter.getTagList();
//    }
//
//    @Override
//    public void showCategories(List<Category> response) {
//        //....
//    }
//
//    @Override
//    public void showTags(List<Tag> response) {
//        tagAdapter.clearData();
//        tagAdapter.addNewses(response);
//    }
//
//    @Override
//    public void showProgressDialog() {
//        refreshLayout.setRefreshing(true);
//    }
//
//    @Override
//    public void hideProgressDialog() {
//        refreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void showInfoMessage(String errorMsg) {
//        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onShareClick(Integer tagId) {
//        Intent intent = new Intent(getContext(), TagNewsActivity.class);
//        intent.putExtra(Constants.TAG_ID,tagId);
//        startActivity(intent);
//    }
//
//    @Override
//    public String getTitle() {
//        return App.getAppComponent().getApp().getString(R.string.tag_title);
//    }
//
//    @Override
//    public void retry() {
//        presenter.getTagList();
//    }
//
//    @Override
//    public void onRefresh() {
//        presenter.getTagList();
//    }
}
