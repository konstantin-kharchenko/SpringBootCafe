package by.kharchenko.springbootcafe.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;


@Entity
@Table(name = INGREDIENTS)
public class Ingredient extends AbstractEntity implements Serializable, Comparable<Ingredient> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_INGREDIENT)
    private BigInteger idIngredient;

    @Column(name = NAME)
    private String name;

    @Column(name = SHELF_LIFE)
    private LocalDate shelfLife;

    @OneToMany(mappedBy = "ingredient")
    Set<IngredientGrams> grams;

    @ManyToMany
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = ID_INGREDIENT), inverseJoinColumns = @JoinColumn(name = ID_PRODUCT))
    private Set<Product> products;


    public BigInteger getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(BigInteger idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public IngredientGrams getGrams() {
        return grams.stream().toList().get(0);
    }

    public void setGrams(Set<IngredientGrams> grams) {
        this.grams = grams;
    }

    @Override
    public int compareTo(Ingredient o) {
        return this.shelfLife.compareTo(o.shelfLife);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(idIngredient, that.idIngredient) && Objects.equals(name, that.name) && Objects.equals(shelfLife, that.shelfLife) && Objects.equals(grams, that.grams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, name, shelfLife, grams);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", name='" + name + '\'' +
                ", shelfLife=" + shelfLife +
                ", grams=" + grams +
                '}';
    }
}
