package dsdmsa.utmnews.domain.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Tag {
    @PrimaryKey
    public Integer id;
    public Integer count;
    public String description;
    public String link;
    public String name;
    public String slug;
    public String taxonomy;
}