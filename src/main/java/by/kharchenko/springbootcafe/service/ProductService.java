package by.kharchenko.springbootcafe.service;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductService extends BaseService<Product>{
    List<Product> findNew() throws ServiceException;

    List<Product> findProductsByIdList(List<BigInteger> idList) throws ServiceException;

    List<Product> findByCurrentPage(Integer page) throws ServiceException;

    Long ordersCount();

    Product findByName(String name);
}
