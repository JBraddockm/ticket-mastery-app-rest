package org.example.service.impl;

import org.example.service.CommonService;
import org.modelmapper.ModelMapper;

public class AbstractCommonService<E, D> implements CommonService<E, D> {
  protected final ModelMapper modelMapper;
  private final Class<E> entityClass;
  private final Class<D> dtoClass;

  public AbstractCommonService(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
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
