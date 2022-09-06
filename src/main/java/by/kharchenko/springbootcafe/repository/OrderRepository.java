package by.kharchenko.springbootcafe.repository;



import by.kharchenko.springbootcafe.model.Client;
import by.kharchenko.springbootcafe.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, BigInteger> {

    @Query(value = "select o from Order o where o.client = ?1 and o.received = false order by o.dateOfReceiving")
    List<Order> findQuickToReceive(Client client);

    @Query(value = "select o from Order o where o.received = false order by o.dateOfReceiving desc")
    List<Order> findByCurrentPage(Pageable pageable);

}
