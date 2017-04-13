package dsdmsa.utmnews.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dsdmsa.utmnews.utils.DefoultImagesUrl;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

import static dsdmsa.utmnews.utils.Constants.MAX_DESC_LEN;

public class Content extends RealmObject {
    private String rendered;
    @Ignore
    private String description = "";
    @Ignore
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