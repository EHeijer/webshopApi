package com.edheijer.webshopapi.models.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;

public interface EntityMapper <D, E>{
	E toEntity(D dto);
	
	D toDto(E entity);
	
	List<E> toEntity(List<D> dtoList);
	
	@IterableMapping(qualifiedByName = "useMe")
	List<D> toDto(List<E> entityList);
}
