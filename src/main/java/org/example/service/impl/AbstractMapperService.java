package org.example.service.impl;

import org.example.service.MapperService;
import org.modelmapper.ModelMapper;

public class AbstractMapperService<E, D> implements MapperService<E, D> {
  private final ModelMapper modelMapper;
  private final Class<E> entityClass;
  private final Class<D> dtoClass;

  public AbstractMapperService(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
    this.modelMapper = modelMapper;
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;
  }

  @Override
  public E mapToEntity(D type) {
    return modelMapper.map(type, entityClass);
  }

  @Override
  public D mapToDTO(E type) {
    return modelMapper.map(type, dtoClass);
  }
}
