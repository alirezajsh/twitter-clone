package com.example.demo5.repository;

import java.util.List;

public interface IRepository<T> {
    boolean add(T entity);
    boolean delete(int id);
    boolean update(T entity);
    T findById(int id);
    List<T> findAll();
}