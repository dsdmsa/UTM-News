package dsdmsa.utmnews.views.adapters;

import dsdmsa.utmnews.models.Post;

interface NewsInteract {
    void onShareCLick(Post post);

    void onBookmarkCLick(Post post);
}