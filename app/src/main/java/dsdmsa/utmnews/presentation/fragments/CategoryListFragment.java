package dsdmsa.utmnews.presentation.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.ClasificationVP;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class CategoryListFragment extends BaseFragment implements
        ClasificationVP.View,
        SwipeRefreshLayout.OnRefreshListener {
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

    @Override
    protected int getLayout() {
        return 0;
    }

//    @InjectPresenter
//    ClassificationPresenter presenter;
//
//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout refreshLayout;
//
//    private CategoryAdapter categoryAdapter;
//
//    @Override
//    protected int getLayout() {
//        return R.layout.fragment_news_list;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        categoryAdapter = new CategoryAdapter(this);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setHasFixedSize(true);
//        refreshLayout.setOnRefreshListener(this);
//        recyclerView.setAdapter(categoryAdapter);
//        presenter.getCategoryList();
//    }
//
//    @Override
//    public void showCategories(List<Category> response) {
//        categoryAdapter.clearData();
//        categoryAdapter.addNewses(response);
//    }
//
//    @Override
//    public void showTags(List<Tag> response) {
//        //....
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
//    public void onShareClick(Integer categoryId) {
//        Intent intent = new Intent(getContext(), CategoryNewsActivity.class);
//        intent.putExtra(Constants.CATEGORY_ID,categoryId);
//        startActivity(intent);
//    }
//
//    @Override
//    public String getTitle() {
//        return App.getAppComponent().getApp().getString(R.string.categories_title);
//    }
//
//    @Override
//    public void retry() {
//        presenter.getCategoryList();
//    }
//
//    @Override
//    public void onRefresh() {
//        presenter.getCategoryList();
//    }
}
