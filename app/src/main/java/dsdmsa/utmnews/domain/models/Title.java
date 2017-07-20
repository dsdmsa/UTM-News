package dsdmsa.utmnews.domain.models;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class Title {

    private String rendered;

    public String getRendered() {
        return Jsoup.clean(rendered, new Whitelist());
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}