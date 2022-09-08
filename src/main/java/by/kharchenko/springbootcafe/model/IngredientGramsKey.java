package by.kharchenko.springbootcafe.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientGramsKey implements Serializable {

    @Column(name = ID_PRODUCT)
   private BigInteger idProduct;

    @Column(name = ID_INGREDIENT)
   private BigInteger idIngredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientGramsKey that = (IngredientGramsKey) o;
        return Objects.equals(idProduct, that.idProduct) && Objects.equals(idIngredient, that.idIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, idIngredient);
    }
}
