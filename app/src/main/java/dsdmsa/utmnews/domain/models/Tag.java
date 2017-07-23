package dsdmsa.utmnews.domain.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Tag implements Parcelable {
    @PrimaryKey
    public Integer id;
    public Integer count;
    public String description;
    public String link;
    public String name;
    public String slug;
    public String taxonomy;

    public Tag() {
    }

    @Ignore
    protected Tag(Parcel in) {
        description = in.readString();
        link = in.readString();
        name = in.readString();
        slug = in.readString();
        taxonomy = in.readString();
    }

    @Ignore
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(link);
        parcel.writeString(name);
        parcel.writeString(slug);
        parcel.writeString(taxonomy);
    }
}