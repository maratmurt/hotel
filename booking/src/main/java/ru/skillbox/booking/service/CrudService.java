package ru.skillbox.booking.service;

import java.util.List;

public interface CrudService<T> {

    List<T> findAll(Integer page, Integer size);

    T findById(Long id);

    T create(T item);

    T update (T item);

    void deleteById(Long id);

}
