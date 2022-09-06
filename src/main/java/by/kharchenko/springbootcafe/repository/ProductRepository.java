package by.kharchenko.springbootcafe.repository;


import by.kharchenko.springbootcafe.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {

    @Query(value = "select p from Product p order by p.registrationTime")
    List<Product> findNew(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.idProduct IN ?1")
    List<Product> findProductsByIdList(List<BigInteger> idList);

    @Query(value = "select p from Product p order by p.validityDate desc")
    List<Product> findByCurrentPage(Pageable pageable);

    @Query("FROM Product WHERE name = ?1")
    Optional<Product> findByName(String name);
}
