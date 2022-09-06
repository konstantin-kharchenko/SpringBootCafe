package by.kharchenko.springbootcafe.service;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Order;
import by.kharchenko.springbootcafe.model.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface OrderService extends BaseService<Order>{
    List<Order> findQuickToReceive(User user) throws ServiceException;

    void addProductsInOrder(BigInteger id, Set<BigInteger> toList) throws ServiceException;

    void deleteProductFromOrderById(BigInteger idOrder, BigInteger idProduct) throws ServiceException;

    List<Order> findByCurrentPage(Integer page) throws ServiceException;

    Long ordersCount();
}
