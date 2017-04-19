package dsdmsa.utmnews.views.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.repository.PostRepository;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static dsdmsa.utmnews.utils.Constants.TIME_TO_WAIT;

public class BookmarkNewsAdapter extends RealmBasedRecyclerViewAdapter<SimplePost, BookmarkNewsAdapter.MyViewHolder> {

    @Inject
    PostRepository repository;

    private NewsInteract interact;
    private long time = 0;

    public BookmarkNewsAdapter(Context context, RealmResults<SimplePost> realmResults, boolean automaticUpdate,
                               boolean animateResults, String animateExtraColumnName, NewsInteract interact) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
        this.interact = interact;
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
                repository.delete(removeSimplePost);
            }
        });

        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onShareClick(removeSimplePost.getLink());
            }
        });

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < System.currentTimeMillis()) {
                    time = System.currentTimeMillis() + TIME_TO_WAIT;
                    interact.onDetailsClick(removeSimplePost);
                }
            }
        });

    }

    public interface NewsInteract {
        void onShareClick(String url);

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

    }

}


