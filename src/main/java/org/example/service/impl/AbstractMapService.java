package org.example.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService<T, ID> {
    protected Map<ID, T>  map = new HashMap<>();

    T save(ID id, T object) {
        return map.put(id, object);
    }

    void saveAll(Map<ID, T> t){
        map.putAll(t);
    }

    List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    void deleteById(ID id) {
        map.remove(id);
    }
}
