package by.kharchenko.springbootcafe.model.dto;

import by.kharchenko.springbootcafe.model.Order;
import by.kharchenko.springbootcafe.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;
import static by.kharchenko.springbootcafe.controllers.DbColumn.ID_USER;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @JsonProperty("idClient")
    private BigInteger idClient;

    @JsonProperty("loyaltyPoints")
    private int loyaltyPoints;

    @JsonProperty("block")
    private boolean block;

    @JsonProperty("clientAccount")
    private BigDecimal clientAccount = new BigDecimal("0");

    @JsonProperty("orders")
    private Set<Order> orders;
}
