package dsdmsa.utmnews.views.adapters;

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

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.utils.Utils;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    @Inject
    PostRepository repository;

    private List<SimplePost> newsList = new ArrayList<>();
    private NewsInteract interact;
    private long time = 0;
    private int lastAnimatedPosition = -1;

    public NewsAdapter(NewsInteract interact) {
        App.getAppComponent().inject(this);
        this.interact = interact;
    }

    public void addNewses(List<SimplePost> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyItemRangeInserted(newsList.size() - Constants.ITEMS_PER_PAGE + 1, newsList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        runEnterAnimation(holder.itemView, position);

        if (repository.exists(newsList.get(position))) {
            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs);
        } else {
            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs_white);
        }

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
                interact.onBookmarkClick(newsList.get(position));
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
                    time = System.currentTimeMillis() + Constants.TIME_TO_WAIT;
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
        if (position < 3)
            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utils.getScreenHeight(App.getAppComponent().getContext()));
                view.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(Constants.LIST_ITEMS_ANIMATION_DURATION)
                        .start();
            }
    }

    public interface NewsInteract {
        void onShareClick(String url);

        void onBookmarkClick(SimplePost post);

        void onDetailsClick(SimplePost post);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final AppCompatImageView imageView;
        private final AppCompatImageView share;
        private final AppCompatImageView bookmark;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.news_thombnail);
            share = (AppCompatImageView) itemView.findViewById(R.id.iv_share);
            bookmark = (AppCompatImageView) itemView.findViewById(R.id.iv_bookmark);
        }
    }

//    private fun runEnterAnimation(view: View, position: Int) {
//        if (position <=  5) {
//            if (position > lastAnimatedPosition) {
//                lastAnimatedPosition = position
//                view.translationY = 1400f
//                view.alpha = 0f
//                view.animate()
//                        .translationY(0f)
//                        .alpha(1f)
//                        .setInterpolator(DecelerateInterpolator(3f))
//                        .setDuration(700)
//                        .setStartDelay(position * 100L)
//                        .start()
//            }
//        }
//    }

}


