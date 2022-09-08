package by.kharchenko.springbootcafe.dto;

import by.kharchenko.springbootcafe.model.Ingredient;
import by.kharchenko.springbootcafe.model.IngredientGrams;
import by.kharchenko.springbootcafe.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;
import static by.kharchenko.springbootcafe.controllers.DbColumn.ID_INGREDIENT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @JsonProperty("idProduct")
    private BigInteger idProduct;

    @JsonProperty("name")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @JsonProperty("validityDate")
    private Date validityDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @JsonProperty("registrationTime")
    private Date registrationTime;

    @JsonProperty("price")
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    @JsonProperty("grams")
    Set<IngredientGrams> grams;

    @JsonProperty("stringPhoto")
    private String stringPhoto;

    @ManyToMany(mappedBy = PRODUCTS)
    @JsonProperty("orders")
    private Set<OrderDTO> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = ID_PRODUCT), inverseJoinColumns = @JoinColumn(name = ID_INGREDIENT))
    @JsonProperty("ingredients")
    private Set<IngredientDTO> ingredients;
}
