package by.kharchenko.springbootcafe.service.impl;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Order;
import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.model.User;
import by.kharchenko.springbootcafe.repository.IngredientRepository;
import by.kharchenko.springbootcafe.repository.OrderRepository;
import by.kharchenko.springbootcafe.repository.ProductRepository;
import by.kharchenko.springbootcafe.service.OrderService;
import by.kharchenko.springbootcafe.util.filereadwrite.FileReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final FileReaderWriter readerWriter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, IngredientRepository ingredientRepository, FileReaderWriter readerWriter, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.readerWriter = readerWriter;
        this.productRepository = productRepository;
    }


    @Override
    public boolean delete(Order order) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) throws ServiceException {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean add(Order order) throws ServiceException {
        orderRepository.save(order);
        return true;
    }

    @Override
    @Transactional
    public Order findById(BigInteger id) throws ServiceException {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Order order) throws ServiceException {
        Optional<Order> optionalDBOrder = orderRepository.findById(order.getIdOrder());
        if (optionalDBOrder.isPresent()){
            Order dBOrder = optionalDBOrder.get();
            dBOrder.setPaymentType(order.getPaymentType());
            dBOrder.setName(order.getName());
            dBOrder.setDateOfReceiving(order.getDateOfReceiving());
            orderRepository.save(dBOrder);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Order> findQuickToReceive(User user) throws ServiceException {
        List<Order> orders = orderRepository.findQuickToReceive(user.getClient());
        for (Order order : orders) {
            List<Product> products = order.getProducts().stream().toList();
            for (Product product : products) {
                String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                product.setStringPhoto(stringPhoto);
            }
        }
        return orders;
    }

    @Override
    @Transactional
    public void addProductsInOrder(BigInteger id, Set<BigInteger> toList) throws ServiceException {
        Order order;
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            Set<Product> oldProducts = order.getProducts();
            List<Product> products = productRepository.findProductsByIdList(toList.stream().toList());
            oldProducts.addAll(products);
            BigDecimal sum = new BigDecimal("0");
            for (Product product : oldProducts) {
                sum = sum.add(product.getPrice());
            }
            order.setPrice(sum);
            order.setProducts(oldProducts);
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public void deleteProductFromOrderById(BigInteger idOrder, BigInteger idProduct) throws ServiceException {
        Order order;
        Optional<Order> optionalOrder = orderRepository.findById(idOrder);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            order.getProducts().removeIf(o -> Objects.equals(o.getIdProduct(), idProduct));
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public List<Order> findByCurrentPage(Integer page) throws ServiceException {
        int offSet = (page - 1) * 10;
        Pageable pageable = PageRequest.of(offSet, 5);
        List<Order> orders = orderRepository.findByCurrentPage(pageable);
        for (Order order : orders) {
            List<Product> products = order.getProducts().stream().toList();
            for (Product product : products) {
                String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                product.setStringPhoto(stringPhoto);
            }
        }
        return orders;
    }

    @Override
    @Transactional
    public Long ordersCount() {
        return orderRepository.count();
    }
}
