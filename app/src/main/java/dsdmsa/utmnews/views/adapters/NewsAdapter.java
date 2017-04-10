package dsdmsa.utmnews.views.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.repository.Specification;
import dsdmsa.utmnews.utils.Constants;
import io.realm.Realm;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Post> newsList = new ArrayList<>();
    private NewsInteract interact;

    public NewsAdapter(NewsInteract interact) {
        this.interact = interact;
    }

    public void addNewses(List<Post> orderDTOs) {
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
//            holder.bookmark.setImageDrawable(ContextCompat.getDrawable(holder.bookmark.getContext(), R.drawable
//                    .ic_bookmarcs));
        } else {
            holder.bookmark.setImageResource(R.drawable.ic_bookmarcs_white);
//            holder.bookmark.setImageDrawable(ContextCompat.getDrawable(holder.bookmark.getContext(), R.drawable
//                    .ic_bookmarcs_white));
        }

        if (newsList.get(position).isExpanded()) {
            holder.gradient.setBackgroundDrawable(ContextCompat.getDrawable(holder.imageView.getContext(), R.drawable
                    .gradient_primary_dark));
            holder.details.setVisibility(View.VISIBLE);
        } else {
            holder.gradient.setBackgroundDrawable(ContextCompat.getDrawable(holder.imageView.getContext(), R.drawable
                    .gradient_primary_d));
            holder.details.setVisibility(View.GONE);
        }

        Glide.with(holder.imageView.getContext())
                .load(newsList.get(position).getContent().getUrl())
                .asBitmap()
                .centerCrop()
                .into(holder.imageView);

        holder.title.setText(newsList.get(position).getTitle().getRendered());
        holder.description.setText(newsList.get(position).getContent().getDescription());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Specification.DATABASE.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        newsList.get(position).setExpanded(!newsList.get(position).isExpanded());
                        notifyItemChanged(position);
                    }
                });
            }
        });
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

    public void removeItem(Post post) {
        newsList.remove(post);
        notifyDataSetChanged();
    }

    public interface NewsInteract {
        void onShareClick(String url);

        void onBookmarkClick(Post post);

        void onDetailsClick(Post post);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final FrameLayout details;
        private final AppCompatImageView imageView;
        private final ImageView share;
        private final ImageView bookmark;
        private final ImageView newsDetail;
        private final View gradient;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            details = (FrameLayout) itemView.findViewById(R.id.view_details);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.news_thombnail);
            share = (ImageView) itemView.findViewById(R.id.iv_share);
            bookmark = (ImageView) itemView.findViewById(R.id.iv_bookmark);
            newsDetail = (ImageView) itemView.findViewById(R.id.iv_details);
            gradient = itemView.findViewById(R.id.gradient);
        }
    }

}


