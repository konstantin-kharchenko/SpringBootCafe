package by.kharchenko.springbootcafe.dto;

import by.kharchenko.springbootcafe.model.Client;
import by.kharchenko.springbootcafe.model.PaymentType;
import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.validator.annotation.CustomFutureOrPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;
import static by.kharchenko.springbootcafe.controllers.DbColumn.ID_PRODUCT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @JsonProperty("idOrder")
    private BigInteger idOrder;

    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    @JsonProperty("name")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date must not be null")
    @CustomFutureOrPresent
    @JsonProperty("dateOfReceiving")
    private Date dateOfReceiving;

    @JsonProperty("price")
    private BigDecimal price = new BigDecimal("0");

    @ManyToOne
    @JoinColumn(name = ID_CLIENT)
    @JsonProperty("client")
    private ClientDTO client;

    @NotNull(message = "Payment type must not be empty")
    @JsonProperty("paymentType")
    private PaymentType paymentType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ORDERS_PRODUCTS, joinColumns = @JoinColumn(name = ID_ORDER), inverseJoinColumns = @JoinColumn(name = ID_PRODUCT))
    @JsonProperty("products")
    private Set<ProductDTO> products;
}
