package dsdmsa.utmnews.presentation.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList = new ArrayList<>();
    private CategoryInteract interact;

    public CategoryAdapter(CategoryInteract interact) {
        this.interact = interact;
    }

    public void addNewses(List<Category> orderDTOs) {
        categoryList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(categoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.onShareClick(categoryList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void clearData() {
        categoryList.clear();
    }

    public interface CategoryInteract {
        void onShareClick(Integer categoryId);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}


