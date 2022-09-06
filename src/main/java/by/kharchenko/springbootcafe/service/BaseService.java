package by.kharchenko.springbootcafe.service;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.AbstractEntity;

import java.math.BigInteger;
import java.util.List;

public interface BaseService<T extends AbstractEntity> {
    boolean delete(T t) throws ServiceException;

    boolean delete(BigInteger id) throws ServiceException;

    boolean add(T userData) throws ServiceException;

    T findById(BigInteger id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    boolean update(T t) throws ServiceException;
}
