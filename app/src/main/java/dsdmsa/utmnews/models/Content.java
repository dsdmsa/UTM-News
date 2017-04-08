package dsdmsa.utmnews.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static dsdmsa.utmnews.utils.Constants.DEFAULT_IMAGE_URL;
import static dsdmsa.utmnews.utils.Constants.MAX_DESC_LEN;

public class Content {
    public String rendered;
    public Boolean _protected;

    public String getRendered() {
        return rendered;
    }

    public String getDescription() {
        String description = "";
        String text = Jsoup.parse(getRendered()).text();
        if (text.length() > MAX_DESC_LEN) {
            description = text.substring(0, MAX_DESC_LEN) + "...";
        } else {
            description = text;
        }
        return description;
    }

    public String getImageUrl() {
        Document document = Jsoup.parse(getRendered());
        Element image = document.select("img").first();
        String url = DEFAULT_IMAGE_URL;
        if (image != null) {
            url = image.absUrl("src");
        }
        return url;
    }
}