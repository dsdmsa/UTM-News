package dsdmsa.utmnews.views.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.utils.Utils;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static dsdmsa.utmnews.utils.Constants.ITEMS_TO_ANIMATE_IN_ADAPTER;
import static dsdmsa.utmnews.utils.Constants.LIST_ITEMS_ANIMATION_DURATION;
import static dsdmsa.utmnews.utils.Constants.TIME_TO_WAIT;

public class BookmarkNewsAdapter extends RealmBasedRecyclerViewAdapter<SimplePost, BookmarkNewsAdapter.MyViewHolder> {


    @Inject
    PostRepository repository;

    //    private SimplePost removeSimplePost;
    //
//    private List<SimplePost> newsList = Specification.DATABASE.where(SimplePost.class).findAll();
    private NewsInteract interact;
    private long time = 0;
    private int lastAnimatedPosition = -1;

    public BookmarkNewsAdapter(Context context, RealmResults<SimplePost> realmResults, boolean automaticUpdate,
                               boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);

        App.getAppComponent().inject(this);

    }

    @Override
    public MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindRealmViewHolder(MyViewHolder myViewHolder, final int position) {

        final SimplePost removeSimplePost = realmResults.get(position);

//        runEnterAnimation(myViewHolder.itemView, position);

        Glide.with(myViewHolder.itemView.getContext())
                .load(realmResults.get(position).getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(myViewHolder.imageView);

        myViewHolder.title.setText(realmResults.get(position).getTitle().getRendered());
        myViewHolder.description.setText(realmResults.get(position).getDescription());
        myViewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                notifyItemRemoved(position);
                repository.delete(removeSimplePost);

//                final SimplePost post = realmResults.get(position);
//
//                Specification.DATABASE.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        if (post.isValid()){
//
//                            notifyItemRemoved(position);
//                            SimplePost simplePost = realm.where(SimplePost.class).equalTo("id",post.getId())
// .findFirst();
//                            simplePost.deleteFromRealm();
//
//
//
////                            notifyDataSetChanged();
//                        }
////                            post.deleteFromRealm();
//
//                    }
//                });


//                repository.delete(realmResults.get(position));


//                SimplePost post = new SimplePost();
//                post.setId(newsList.get(position).getId());

//                newsList.remove(position);
//                repository.delete(newsList.get(position));
//                newsList.remove(position);
//                notifyDataSetChanged();


//                interact.onBookmarkClick(newsList.get(position),position);
            }
        });

        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                interact.onShareClick(newsList.get(position).getLink());
            }
        });

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < System.currentTimeMillis()) {
                    time = System.currentTimeMillis() + TIME_TO_WAIT;
//                    interact.onDetailsClick(newsList.get(position));
                }
            }
        });

    }

    //
//    public BookmarkNewsAdapter(NewsInteract interact) {
//        App.getAppComponent().inject(this);
//        this.interact = interact;
////        repository.querry(new GetAllRealmPostsSpecification(this));
//    }
//
////    public void addNewses(List<SimplePost> orderDTOs) {
////        newsList.addAll(orderDTOs);
////        notifyItemRangeInserted(newsList.size() - ITEMS_PER_PAGE + 1, newsList.size());
////    }
//
//    @Override
//    public MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
//        return null;
//    }
//
//    @Override
//    public void onBindRealmViewHolder(MyViewHolder myViewHolder, int i) {
//
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//
//        runEnterAnimation(holder.itemView, position);
//
////        if (repository.exists(newsList.get(position))) {
////            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs);
////        } else {
////            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs_white);
////        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return newsList.size();
//    }
//
////    public void clearData() {
////        newsList.clear();
//////        notifyDataSetChanged();
//////    }
////
    private void runEnterAnimation(View view, int position) {
        if (position < ITEMS_TO_ANIMATE_IN_ADAPTER)
            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utils.getScreenHeight(App.getAppComponent().getContext()));
                view.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(LIST_ITEMS_ANIMATION_DURATION)
                        .start();
            }
    }

    ////
//    @Override
//    public void onSuccess(List<SimplePost> response) {
//        newsList.addAll(response);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void onError(String errorMsg) {
//
//    }
//
//    public void removeItem(SimplePost post) {
////        repository.delete(post);
//        notifyDataSetChanged();
//    }
//
////    public boolean hasNewses() {
////        return !newsList.isEmpty();
////    }
//
////    static class MyViewHolder extends RecyclerView.ViewHolder {
//        private final TextView title;
//        private final TextView description;
//        private final AppCompatImageView imageView;
//        private final AppCompatImageView share;
//        private final AppCompatImageView bookmark;
//
//        MyViewHolder(View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.tv_title);
//            description = (TextView) itemView.findViewById(R.id.tv_description);
//            imageView = (AppCompatImageView) itemView.findViewById(R.id.news_thombnail);
//            share = (AppCompatImageView) itemView.findViewById(R.id.iv_share);
//            bookmark = (AppCompatImageView) itemView.findViewById(R.id.iv_bookmark);
//        }
////    }
//
    public interface NewsInteract {
        void onShareClick(String url);

        void onBookmarkClick(SimplePost post, int position);

        void onDetailsClick(SimplePost post);
    }

    public class MyViewHolder extends RealmViewHolder {


        private final TextView title;
        private final TextView description;
        private final AppCompatImageView imageView;
        private final AppCompatImageView share;
        private final AppCompatImageView bookmark;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.news_thombnail);
            share = (AppCompatImageView) itemView.findViewById(R.id.iv_share);
            bookmark = (AppCompatImageView) itemView.findViewById(R.id.iv_bookmark);
        }

//        public MyViewHolder(View itemView) {
//            super(itemView);
//        }
    }

}


