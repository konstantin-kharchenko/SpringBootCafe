package by.kharchenko.springbootcafe.service.impl;

import by.kharchenko.springbootcafe.exception.DaoException;
import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.repository.IngredientRepository;
import by.kharchenko.springbootcafe.repository.ProductRepository;
import by.kharchenko.springbootcafe.service.ProductService;
import by.kharchenko.springbootcafe.util.filereadwrite.FileReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileReaderWriter readerWriter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, FileReaderWriter readerWriter) {
        this.productRepository = productRepository;
        this.readerWriter = readerWriter;
    }

    @Override
    public boolean delete(Product product) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Product userData) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public Product findById(BigInteger id) throws ServiceException {
        Product product;
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            product.setStringPhoto(readerWriter.readPhoto(product.getPhotoPath()));
            return product;
        }
        return null;
    }

    @Override
    public List<Product> findAll(){
        return null;
    }

    @Override
    public boolean update(Product product) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public List<Product> findNew() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = productRepository.findNew(pageable);
        for (Product product : products) {
            String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
            product.setStringPhoto(stringPhoto);
        }
        return products;
    }

    @Override
    @Transactional
    public List<Product> findProductsByIdList(List<BigInteger> idList) throws ServiceException {
        List<Product> products = productRepository.findProductsByIdList(idList);
        for (Product product : products) {
            product.setStringPhoto(readerWriter.readPhoto(product.getPhotoPath()));
        }
        return products;
    }

    @Override
    @Transactional
    public List<Product> findByCurrentPage(Integer page) throws ServiceException {
        int offSet = (page - 1) * 10;
        Pageable pageable = PageRequest.of(offSet, 5);
        List<Product> products = productRepository.findByCurrentPage(pageable);
        for (Product product : products) {
            String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
            product.setStringPhoto(stringPhoto);
        }
        return products;
    }

    @Override
    @Transactional
    public Long ordersCount() {
        return productRepository.count();
    }

    @Override
    @Transactional
    public Product findByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        return product.orElse(null);
    }
}
