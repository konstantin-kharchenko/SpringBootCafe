package by.kharchenko.springbootcafe.model;

import by.kharchenko.springbootcafe.validator.annotation.CustomFutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = ORDERS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity implements Serializable, Comparable<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_ORDER)
    private BigInteger idOrder;

    @Column(name = NAME)
    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    private String name;


    @Temporal(TemporalType.DATE)
    @Column(name = DATE_OF_RECEIVING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date must not be null")
    @CustomFutureOrPresent
    private Date dateOfReceiving;

    @Column(name = PRICE)
    private BigDecimal price = new BigDecimal("0");

    @Column(name = RECEIVED)
    private boolean received;

    @ManyToOne
    @JoinColumn(name = ID_CLIENT)
    private Client client;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = ID_PAYMENT_TYPE)
    @NotNull(message = "Payment type must not be empty")
    private PaymentType paymentType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ORDERS_PRODUCTS, joinColumns = @JoinColumn(name = ID_ORDER), inverseJoinColumns = @JoinColumn(name = ID_PRODUCT))
    private Set<Product> products;

    @Override
    public int compareTo(Order o) {
        return this.dateOfReceiving.compareTo(o.dateOfReceiving);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return received == order.received && Objects.equals(idOrder, order.idOrder) && Objects.equals(name, order.name) && Objects.equals(dateOfReceiving, order.dateOfReceiving) && Objects.equals(price, order.price)  && Objects.equals(paymentType, order.paymentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, name, dateOfReceiving, price, received, paymentType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", name='" + name + '\'' +
                ", dateOfReceiving=" + dateOfReceiving +
                ", price=" + price +
                ", received=" + received +
                ", paymentType=" + paymentType +
                '}';
    }


}
