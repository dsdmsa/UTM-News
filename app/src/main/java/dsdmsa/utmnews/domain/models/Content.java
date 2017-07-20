package dsdmsa.utmnews.domain.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dsdmsa.utmnews.domain.utils.DefoultImagesUrl;

import static dsdmsa.utmnews.domain.utils.Constants.MAX_DESC_LEN;

public class Content {
    private String rendered;
    private String description = "";
    private String url;

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public String getDescription() {
        String text = Jsoup.parse(getRendered()).text();
        if (text.length() > MAX_DESC_LEN) {
            description = text.substring(0, MAX_DESC_LEN) + "...";
        } else {
            description = text;
        }
        return description;
    }

    public String getUrl() {
        url = DefoultImagesUrl.getImage();
        Document document = Jsoup.parse(getRendered());
        Element image = document.select("img").first();
        if (image != null) {
            url = image.absUrl("src");
        }
        return url;
    }
}