package com.vehicle.identification.vehicleidsystem.dao;

import java.util.List;

/**
 * Generic interface for Polymorphism.
 * All DAO classes will implement this, allowing us to treat them uniformly.
 */
public interface BaseDAO<T> {
    void create(T entity);
    List<T> findAll();
    T findById(int id);
    boolean update(T entity);
    boolean delete(int id);
}