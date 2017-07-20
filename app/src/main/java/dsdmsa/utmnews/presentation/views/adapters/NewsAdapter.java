package dsdmsa.utmnews.presentation.views.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Utils;

import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_PER_PAGE;
import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_TO_ANIMATE_IN_ADAPTER;
import static dsdmsa.utmnews.domain.utils.Constants.LIST_ITEMS_ANIMATION_DURATION;
import static dsdmsa.utmnews.domain.utils.Constants.TIME_TO_WAIT;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    private List<SimplePost> newsList = new ArrayList<>();
    private NewsInteract interact;
    private long time = 0;
    private int lastAnimatedPosition = -1;

    public NewsAdapter(NewsInteract interact) {
//        App.getAppComponent().inject(this);
        this.interact = interact;
    }

    public void addNewses(List<SimplePost> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyItemRangeInserted(newsList.size() - ITEMS_PER_PAGE + 1, newsList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        runEnterAnimation(holder.itemView, position);

//        if (repository.exists(newsList.get(position))) {
//            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs);
//            newsList.get(position).setBookmarked(true);
//        } else {
//            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs_white);
//        }

        Glide.with(holder.imageView.getContext())
                .load(newsList.get(position).getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(holder.imageView);

        holder.title.setText(newsList.get(position).getTitle().getRendered());
        holder.description.setText(newsList.get(position).getDescription());
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onBookmarkClick(newsList.get(position),position);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onShareClick(newsList.get(position).getLink());
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time < System.currentTimeMillis()) {
                    time = System.currentTimeMillis() + TIME_TO_WAIT;
                    interact.onDetailsClick(newsList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void clearData() {
        newsList.clear();
        notifyDataSetChanged();
    }

    private void runEnterAnimation(View view, int position) {
        if (position < ITEMS_TO_ANIMATE_IN_ADAPTER)
            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utils.getScreenHeight(App.getAppComponent().getApp()));
                view.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(LIST_ITEMS_ANIMATION_DURATION)
                        .start();
            }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
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

    public interface NewsInteract {
        void onShareClick(String url);

        void onBookmarkClick(SimplePost post, int position);

        void onDetailsClick(SimplePost post);
    }

}


