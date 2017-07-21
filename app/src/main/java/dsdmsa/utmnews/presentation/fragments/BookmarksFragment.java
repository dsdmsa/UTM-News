package dsdmsa.utmnews.presentation.fragments;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.presentation.mvp.BookmarksFragmentVP;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class BookmarksFragment extends BaseFragment implements
        BookmarksFragmentVP.View{

//    @BindView(R.id.recycle_view)
//    RecyclerView recyclerView;
//
//    @InjectPresenter
//    BookmarksFragmentPresenter presenter;
//
//    @Inject
//    PostRepository repository;
//
//    private BookmarkNewsAdapter newsAdapter;
//    private LinearLayoutManager layoutManager;
//
    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void showInfoMessage(String s) {

    }

//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        App.getAppComponent().inject(this);
//        navigationPresenter.setTitle(getTitle());
//        repository.querry(new GetAllRealmPostsSpecification(this));
//    }
//
//    @Override
//    public void showInfoMessage(String errorMsg) {
//        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onShareClick(String url) {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
//        sendIntent.setType(Constants.TEXT_PLAIN);
//        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)));
//    }
//
//    @Override
//    public void onDetailsClick(SimplePost post) {
//        new ChromeTab(getActivity(), post.getLink());
//    }
//
//    @Override
//    public String getTitle() {
//        return App.getAppComponent().getApp().getString(R.string.bokmarks_title);
//    }
//
//    @Override
//    public void retry() {
//
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        navigationPresenter.setTitle(getTitle());
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        navigationPresenter.setTitle(getTitle());
//    }
//
//    @Override
//    public void onSuccess(RealmResults<SimplePost> response) {
//        layoutManager = new LinearLayoutManager(getContext());
//        newsAdapter = new BookmarkNewsAdapter(getContext(), response, true, true, null, this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(newsAdapter);
//    }
//
//    @Override
//    public void onError(String errorMsg) {
//        showInfoMessage(errorMsg);
//    }

}
