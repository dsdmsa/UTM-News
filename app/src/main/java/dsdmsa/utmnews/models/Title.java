package dsdmsa.utmnews.models;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class Title {

    private String rendered;

    public String getRendered() {
        return  Jsoup.clean(rendered,new Whitelist());
    }
}