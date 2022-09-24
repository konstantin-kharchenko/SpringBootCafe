package by.kharchenko.springbootcafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;


@Entity
@Table(name = INGREDIENTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
