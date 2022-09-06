package by.kharchenko.springbootcafe.repository;


import by.kharchenko.springbootcafe.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ClientRepository extends JpaRepository<Client, BigInteger> {
}
