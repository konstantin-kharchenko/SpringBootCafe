package by.kharchenko.springbootcafe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

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

    @JsonProperty("stringPhoto")
    private String stringPhoto;

    @JsonProperty("orders")
    private Set<OrderDTO> orders;

    @JsonProperty("ingredients")
    private Set<IngredientDTO> ingredients;
}
