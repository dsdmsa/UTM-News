package dsdmsa.utmnews.presentation.fragments;


import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsFragmentVP;

public class LatestNewsFragment extends BaseFragment implements
        NewsFragmentVP.View,
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
    public void addNewses(List<SimplePost> newses) {

    }

    @Override
    public void refreshDatas(List<SimplePost> response) {

    }

    @Override
    public void setupRecyclerView() {

    }


    @Override
    protected int getLayout() {
        return 0;
    }

//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout refreshLayout;
//
//    @InjectPresenter(type = PresenterType.GLOBAL)
//    NewsPresenter presenter;
//
//    private NewsAdapter newsAdapter;
//    private LinearLayoutManager layoutManager;
//    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
//
//    public static LatestNewsFragment newInstance() {
//        Bundle args = new Bundle();
//        LatestNewsFragment fragment = new LatestNewsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayout() {
//        return R.layout.fragment_news_list;
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        layoutManager = new LinearLayoutManager(getContext());
//        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                presenter.loadNewsOnPage(current_page);
//            }
//        };
//        presenter.setupRecyclerView();
//        presenter.loadNewsOnPage(INITIAL_PAGE);
//    }
//
//    @Override
//    public void setupRecyclerView() {
//        newsAdapter = new NewsAdapter(this);
//        refreshLayout.setOnRefreshListener(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(newsAdapter);
//        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
//    }
//
//    @Override
//    public void addNewses(List<SimplePost> newses) {
//        newsAdapter.addNewses(newses);
//    }
//
//    @Override
//    public void refreshDatas(List<SimplePost> response) {
//        setupRecyclerView();
//        newsAdapter.clearData();
//        newsAdapter.addNewses(response);
//    }
//
//    @Override
//    public void onRefresh() {
//        presenter.loadNewsOnPage(INITIAL_PAGE);
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
//    public void onShareClick(String url) {
//        presenter.shareString(url);
//    }
//
//    @Override
//    public void onBookmarkClick(SimplePost post, int position) {
//        if (post.isBookmarked()) {
//            post.setBookmarked(false);
//            presenter.removeBookmarkPost(post);
//            newsAdapter.notifyItemChanged(position);
//        } else {
//            post.setBookmarked(true);
//            presenter.bookmarkPost(post);
//            newsAdapter.notifyItemChanged(position);
//        }
//    }
//
//    @Override
//    public void onDetailsClick(final SimplePost post) {
//        new ChromeTab(getActivity(), post.getLink());
//    }
//
//    @Override
//    public String getTitle() {
//        return App.getAppComponent().getApp().getString(R.string.news_lastest_title);
//    }
//
//    @Override
//    public void retry() {
//        presenter.loadNewsOnPage(INITIAL_PAGE);
//    }

}
