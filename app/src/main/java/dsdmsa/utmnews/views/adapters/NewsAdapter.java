package dsdmsa.utmnews.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.utils.Constants;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements NewsInteract {

    private List<Post> newsList = new ArrayList<>();
    private Context mContext;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addNewses(List<Post> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyItemRangeInserted(newsList.size() - Constants.PAGE_ITEMS + 1, newsList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
                .load(newsList.get(position).getContent().getImageUrl())
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(newsList.get(position).getTitle().rendered);
//        holder.description.setText(newsList.get(position).getContent().getDescription());
//        holder.bookmark.setOnClickListener(new BookmarkClick(newsList.get(position), this));
//        holder.share.setOnClickListener(new ShareClick(newsList.get(position), this));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onShareCLick(Post post) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, post.getLink());
        sendIntent.setType(Constants.TEXT_PLAIN);
        mContext.startActivity(Intent.createChooser(sendIntent, "News"));
    }

    @Override
    public void onBookmarkCLick(Post post) {
// save on repository
    }

    public void clearData() {
        newsList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        //        private final TextView description;
        private final ImageView imageView;
//        private final ImageView bookmark;
//        private final ImageView share;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
//            description = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (ImageView) itemView.findViewById(R.id.news_thombnail);
//            bookmark = (ImageView) itemView.findViewById(R.id.iv_bookmarc);
//            share = (ImageView) itemView.findViewById(R.id.iv_share);
        }
    }

    static class ShareClick implements View.OnClickListener {
        private Post post;
        private NewsInteract newsInteract;

        public ShareClick(Post post, NewsInteract newsInteract) {
            this.post = post;
            this.newsInteract = newsInteract;
        }

        @Override
        public void onClick(View v) {
            newsInteract.onShareCLick(post);
        }
    }

    static class BookmarkClick implements View.OnClickListener {
        private Post post;
        private NewsInteract newsInteract;

        public BookmarkClick(Post post, NewsInteract newsInteract) {
            this.post = post;
            this.newsInteract = newsInteract;
        }

        @Override
        public void onClick(View v) {
            newsInteract.onBookmarkCLick(post);
        }
    }
}


