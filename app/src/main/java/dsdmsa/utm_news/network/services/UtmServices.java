package dsdmsa.utm_news.network.services;


import java.util.List;

import dsdmsa.utm_news.models.Category;
import dsdmsa.utm_news.models.News;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface UtmServices {
    /**
     * @param page
     * @param limit
     * @param top
     * @param categoryId
     * @param newsId
     * @return list of news
     */
    @GET
    Call<List<News>> getNews(@Body Integer page,
                             @Body Integer limit,
                             @Body Boolean top,
                             @Body List<String> categoryId,
                             @Body List<String> newsId);

    /**
     * returns all categories
     *
     * @return
     */
    @GET
    Call<List<Category>> getCategories();

    /**
     * @param keyWord
     * @param categoryId
     * @param startDate
     * @param endDate
     * @return all searched news
     */
    @GET
    Call<List<News>> searchNews(@Body String keyWord,
                                @Body List<String> categoryId,
                                @Body Double startDate,
                                @Body Double endDate);
}
