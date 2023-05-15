package com.test.exercise.bookdepository.service.get_services;

import java.util.List;

public interface GetEntity<T> {
    T getById(Long id);
    T getByName(String name);
    List<T> getAll();
}
