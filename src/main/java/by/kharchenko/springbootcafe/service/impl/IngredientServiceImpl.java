package by.kharchenko.springbootcafe.service.impl;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Ingredient;
import by.kharchenko.springbootcafe.service.IngredientService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Override
    public boolean delete(Ingredient ingredient) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Ingredient userData) throws ServiceException {
        return false;
    }

    @Override
    public Ingredient findById(BigInteger id) throws ServiceException {
        return null;
    }

    @Override
    public List<Ingredient> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Ingredient ingredient) throws ServiceException {
        return false;
    }
}
