package by.kharchenko.springbootcafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = PRODUCTS_INGREDIENTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientGrams {

    @EmbeddedId
    IngredientGramsKey ingredientGramsKey;

    @ManyToOne
    @MapsId("idIngredient")
    @JoinColumn(name = ID_INGREDIENT)
    private Ingredient ingredient;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = ID_PRODUCT)
    private Product product;

    @Column(name = "grams")
    private double grams;
}
