package ru.alliedar.pokaznoi.web.mappers;

public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDto(E Entity);
}
