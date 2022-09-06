package by.kharchenko.springbootcafe.repository;


import by.kharchenko.springbootcafe.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AdminRepository extends JpaRepository<Administrator, BigInteger> {
}
