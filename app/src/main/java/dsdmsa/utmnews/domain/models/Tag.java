package dsdmsa.utmnews.domain.models;

import java.util.List;

public class Tag {
    public Integer id;
    public Integer count;
    public String description;
    public String link;
    public String name;
    public String slug;
    public String taxonomy;
    public List<Object> meta = null;
    public Links links;
}