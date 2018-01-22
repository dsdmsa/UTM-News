package dsdmsa.utmnews.presentation.views.adapters.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;

public class PostsDiffCallback extends DiffUtil.Callback {

    private final List<SimplePost> oldList;
    private final List<SimplePost> newList;

    public PostsDiffCallback(List<SimplePost> oldList, List<SimplePost> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final SimplePost oldItem = oldList.get(oldItemPosition);
        final SimplePost newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).isBookmarked() == newList.get(newItemPosition).isBookmarked();
    }

}