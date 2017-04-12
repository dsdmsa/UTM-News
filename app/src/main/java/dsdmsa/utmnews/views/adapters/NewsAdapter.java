package dsdmsa.utmnews.views.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.utils.Constants;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<SimplePost> newsList = new ArrayList<>();
    private NewsInteract interact;

    public NewsAdapter(NewsInteract interact) {
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

        if (newsList.get(position).isBookmarked()) {
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
        holder.newsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onDetailsClick(newsList.get(position));
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

    public void removeItem(SimplePost post) {
        newsList.remove(post);
        notifyDataSetChanged();
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
        private final AppCompatImageView newsDetail;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.news_thombnail);
            share = (AppCompatImageView) itemView.findViewById(R.id.iv_share);
            bookmark = (AppCompatImageView) itemView.findViewById(R.id.iv_bookmark);
            newsDetail = (AppCompatImageView) itemView.findViewById(R.id.iv_details);
        }
    }

}


