package dsdmsa.utmnews.utils;

import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.models.SimplePost;

public class SimplePostAdapter {

    private SimplePostAdapter() {
    }

    public static SimplePost getSimplePost(Post post) {
        SimplePost simplePost = new SimplePost();
        simplePost.setBookmarked(false);
        simplePost.setDate(post.getDate());
        simplePost.setDescription(post.getContent().getDescription());
        simplePost.setId(post.getId());
        simplePost.setImageUrl(post.getContent().getUrl());
        simplePost.setLink(post.getLink());
        simplePost.setTitle(post.getTitle());
        return simplePost;
    }
}