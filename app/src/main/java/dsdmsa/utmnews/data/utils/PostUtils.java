package dsdmsa.utmnews.data.utils;


import java.text.DateFormatSymbols;
import java.util.regex.Pattern;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;

public class PostUtils {

    private PostUtils() {
    }

    public static SimplePost toSimplePost(Post post) {
        SimplePost simplePost = new SimplePost();
        simplePost.setDate(post.getDate());
        if (post.getContent() != null && post.getContent().getDescription() != null)
            simplePost.setDescription(post.getContent().getDescription());
        simplePost.setId(post.getId());
        if (post.getContent() != null)
            simplePost.setImageUrl(post.getContent().getUrl());
        simplePost.setLink(post.getLink());
        simplePost.setTitle(post.getTitle());
        simplePost.setBookmarked(false);
        if (post.getCategories() != null && !post.getCategories().isEmpty())
            simplePost.setCategoryId(post.getCategories().get(0));
        return simplePost;
    }

    public static SimplePost setCategory(SimplePost post) {
        AppDb appDb = App.getAppComponent().getAppDb();
        post.setCategory(appDb.getCategoryDao().getCategory(post.getCategoryId()).getName());
        return post;
    }

    public static SimplePost setBookmarked(SimplePost post) {
        AppDb appDb = App.getAppComponent().getAppDb();
        post.setBookmarked(appDb.getPostDao().getPostById(post.getId()) != null);
        return post;
    }

    public static SimplePost setDate(SimplePost post) {
        String date = post.getDate();
        if (post.getDate().contains("T"))
            date = post.getDate().substring(0, post.getDate().indexOf('T'));
        date = date.replace('-', ' ');
        String[] dates = date.split(Pattern.quote(" "));
        if (date.length() == 3) {
            date = String.format("%s %s %s", dates[2], getMonth(Integer.valueOf(dates[1])), dates[0]);
        }
        post.setDate(date);
        return post;
    }

    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

}
