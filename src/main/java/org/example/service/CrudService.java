package org.example.service;

import java.util.List;
import java.util.Map;

public interface CrudService<T, ID> {
    T save(T t);

    void saveAll(Map<ID, T> map);

    List<T> findAll();

    T findById(ID id);

    void deleteById(ID id);

    void update(T object);

}
