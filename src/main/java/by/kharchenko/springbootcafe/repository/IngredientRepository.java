package by.kharchenko.springbootcafe.repository;


import by.kharchenko.springbootcafe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IngredientRepository extends JpaRepository<Ingredient, BigInteger> {

}
