package dsdmsa.utmnews.models;

import io.realm.RealmObject;

public class Guid extends RealmObject {

    private String rendered;

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}