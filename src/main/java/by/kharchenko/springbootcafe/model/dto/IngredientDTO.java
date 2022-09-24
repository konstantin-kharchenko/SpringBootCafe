package by.kharchenko.springbootcafe.model.dto;

import by.kharchenko.springbootcafe.model.IngredientGrams;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;
import static by.kharchenko.springbootcafe.controllers.DbColumn.ID_PRODUCT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    @JsonProperty("idIngredient")
    private BigInteger idIngredient;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shelfLife")
    private LocalDate shelfLife;

    @JsonProperty("grams")
    Set<IngredientGrams> grams;

    @JsonProperty("products")
    private Set<ProductDTO> products;
}
