package dsdmsa.utmnews.presentation.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.SimplePost;

import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_PER_PAGE;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<SimplePost> newsList = new ArrayList<>();
    private Listener listener;

    public NewsAdapter(Listener listener) {
        this.listener = listener;
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
    public void onBindViewHolder(MyViewHolder holder, int pos) {

        final int position = holder.getAdapterPosition();

        Glide.with(holder.image.getContext())
                .load(newsList.get(position).getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(holder.image);

        holder.tvTitle.setText(newsList.get(position).getTitle().getRendered());
        holder.tvDescription.setText(newsList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPostClick(newsList.get(position));
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

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface Listener {
        void onPostClick(SimplePost post);
    }
}


