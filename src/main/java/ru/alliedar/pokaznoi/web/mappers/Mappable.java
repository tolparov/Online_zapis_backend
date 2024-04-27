package ru.alliedar.pokaznoi.web.mappers;

import java.util.List;

public interface Mappable<E, D> {

    E toEntity(D dto);

    List<D> toDto(List<E> entity);

    D toDto(E entity);

    List<E> toEntity(List<D> dto);
}
