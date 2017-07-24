package dsdmsa.utmnews.presentation.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Tag;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<Tag> tagList = new ArrayList<>();
    private TagInteract interact;

    public TagAdapter(TagInteract interact) {
        this.interact = interact;
    }

    public void addTags(List<Tag> orderDTOs) {
        tagList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        holder.name.setText(tagList.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onTagClicked(tagList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public void clearData() {
        tagList.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        public TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface TagInteract {
        void onTagClicked(Tag tag);
    }

}


