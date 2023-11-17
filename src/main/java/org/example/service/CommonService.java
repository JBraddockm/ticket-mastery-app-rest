package org.example.service;

public interface CommonService<E, D> {

    E mapToEntity(D type);

    D mapToDTO(E type);
}
