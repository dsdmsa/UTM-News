package dsdmsa.utmnews.repository;


import io.realm.Realm;

public interface Specification {
    void querry();
    Realm DATABASE = Realm.getDefaultInstance();
}
