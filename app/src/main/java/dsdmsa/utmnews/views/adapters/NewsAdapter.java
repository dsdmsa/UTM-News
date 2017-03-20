package dsdmsa.utmnews.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.utils.Navigator;

import static dsdmsa.utmnews.This.appComponent;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    @Inject
    Navigator navigator;
    private Context mContext;
    private List<Post> newsList = new ArrayList<>();

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
        appComponent.inject(this);
    }

    public void setNewses(List<Post> orderDTOs) {
        newsList.clear();
        newsList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    public void addNewses(List<Post> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(mContext).load("http://utm.md/wp-content/uploads/2017/01/VBostan_Omul-anului-2016.jpg").into
//                (holder.thumbnail);
        holder.title.setText(newsList.get(position).getTitle().rendered);
        String description;

        String text = Jsoup.parse(newsList.get(position).getContent().rendered).text();
        if (text.length() > 200) {
            description = text.substring(0, 200) + "...";
        } else {
            description = text;
        }
        holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
//            thumbnail = (ImageView) itemView.findViewById(R.id.news_thombnail);
            title = (TextView) itemView.findViewById(R.id.name_time);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigator.startNewsActivity(newsList.get(getAdapterPosition()));
                }
            });
        }
    }

}
