package by.kharchenko.springbootcafe.service.impl;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Administrator;
import by.kharchenko.springbootcafe.service.AdminService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean delete(Administrator administrator) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Administrator userData) throws ServiceException {
        return false;
    }

    @Override
    public Administrator findById(BigInteger id) throws ServiceException {
        return null;
    }

    @Override
    public List<Administrator> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Administrator administrator) throws ServiceException {
        return false;
    }
}
