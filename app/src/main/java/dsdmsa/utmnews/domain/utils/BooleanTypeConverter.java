package dsdmsa.utmnews.domain.utils;

import android.arch.persistence.room.TypeConverter;

public class BooleanTypeConverter {

    @TypeConverter
    public static boolean toDate(int value) {
        return value == 1;
    }

    @TypeConverter
    public static int toLong(boolean value) {
        return value ? 1 : 0;
    }
}