package by.kharchenko.springbootcafe.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Embeddable
public class IngredientGramsKey implements Serializable {

    @Column(name = ID_PRODUCT)
   private BigInteger idProduct;

    @Column(name = ID_INGREDIENT)
   private BigInteger idIngredient;

    public IngredientGramsKey(BigInteger idProduct, BigInteger idIngredient) {
        this.idProduct = idProduct;
        this.idIngredient = idIngredient;
    }

    public IngredientGramsKey() {
    }

    public BigInteger getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(BigInteger idProduct) {
        this.idProduct = idProduct;
    }

    public BigInteger getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(BigInteger idIngredient) {
        this.idIngredient = idIngredient;
    }

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
