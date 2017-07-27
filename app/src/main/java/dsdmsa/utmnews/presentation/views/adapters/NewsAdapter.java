package dsdmsa.utmnews.presentation.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.models.SimplePost;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<SimplePost> newsList = new ArrayList<>();
    private Listener listener;

    @Inject
    protected Context mContext;

    @Inject
    protected AppDb appDb;

    public NewsAdapter(Listener listener) {
        App.getAppComponent().inject(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.listener = listener;
        appDb.getPostDao().getAllPosts().observeForever(this::updateBookmarkIcon);
    }

    private void updateBookmarkIcon(List<SimplePost> simplePosts) {
        for (int i = 0; i < newsList.size(); i++) {
            newsList.get(i).setBookmarked(simplePosts.contains( newsList.get(i)));
        }
        notifyDataSetChanged();
    }

    public void addNewses(List<SimplePost> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyItemRangeInserted(newsList.size() - 1, orderDTOs.size() - 1);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int pos) {

        final int position = holder.getAdapterPosition();

        Glide.with(holder.image.getContext())
                .load(newsList.get(position).getImageUrl())
                .centerCrop()
                .into(holder.image);

        if (newsList.get(position).isBookmarked()) {
            holder.ivBookmark.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmarcs));
        } else {
            holder.ivBookmark.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmarcs_white));
        }

        holder.tvTime.setText(newsList.get(position).getDate());
        holder.tvTitle.setText(newsList.get(position).getTitle().getRendered());
        holder.tvDescription.setText(newsList.get(position).getDescription());
        holder.tvCategory.setText(newsList.get(position).getCategory());

        holder.itemView.setOnClickListener(view -> listener.onPostClick(newsList.get(position)));
        holder.ivShare.setOnClickListener(view -> listener.onShareClick(newsList.get(position)));
        holder.ivBookmark.setOnClickListener(view -> {
            listener.onBookmark(newsList.get(position));
            notifyItemChanged(position);
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

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.iv_share)
        ImageView ivShare;
        @BindView(R.id.iv_bookmark)
        ImageView ivBookmark;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Listener {
        void onPostClick(SimplePost post);

        void onShareClick(SimplePost post);

        void onBookmark(SimplePost post);
    }

}


