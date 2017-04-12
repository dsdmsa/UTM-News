package dsdmsa.utmnews.repository;


public interface Repository<T> {
    void add(T t);

    void delete(T t);

    void querry(Specification specification);
}
