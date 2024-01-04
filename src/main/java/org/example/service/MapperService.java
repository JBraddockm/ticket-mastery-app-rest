package org.example.service;

public interface MapperService<E, D> {

    E mapToEntity(D type);

    D mapToDTO(E type);
}
