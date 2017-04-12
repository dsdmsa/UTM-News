package dsdmsa.utmnews.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Tag;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<Tag> tagList = new ArrayList<>();
    private TagInteract interact;

    public TagAdapter(TagInteract interact) {
        this.interact = interact;
    }

    public void addNewses(List<Tag> orderDTOs) {
        tagList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(tagList.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onShareClick(tagList.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public interface TagInteract {
        void onShareClick(Integer categoryId);
    }

}

