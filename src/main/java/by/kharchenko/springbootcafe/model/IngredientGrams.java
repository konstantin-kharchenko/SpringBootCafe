package by.kharchenko.springbootcafe.model;

import javax.persistence.*;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = PRODUCTS_INGREDIENTS)
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


    public IngredientGrams(IngredientGramsKey ingredientGramsKey, Ingredient ingredient, Product product, double grams) {
        this.ingredientGramsKey = ingredientGramsKey;
        this.ingredient = ingredient;
        this.product = product;
        this.grams = grams;
    }

    public IngredientGrams() {
    }

    public IngredientGramsKey getIngredientGramsKey() {
        return ingredientGramsKey;
    }

    public void setIngredientGramsKey(IngredientGramsKey ingredientGramsKey) {
        this.ingredientGramsKey = ingredientGramsKey;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }
}
