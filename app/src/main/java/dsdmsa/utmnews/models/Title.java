package dsdmsa.utmnews.models;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import io.realm.RealmObject;

public class Title  extends RealmObject {

    private String rendered;

    public String getRendered() {
        return  Jsoup.clean(rendered,new Whitelist());
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}