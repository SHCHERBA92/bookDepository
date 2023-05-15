package com.test.exercise.bookdepository.service.add_services;

import java.util.List;

public interface AddEntity<V> {
    void add(V v);
    void addAll(List<V> list);
}
