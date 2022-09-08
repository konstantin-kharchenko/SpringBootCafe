package by.kharchenko.springbootcafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = CLIENTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_CLIENT)
    private BigInteger idClient;

    @Column(name = LOYALTY_POINTS)
    private int loyaltyPoints;

    @Column(name = BLOCK)
    private boolean block;

    @Column(name = CLIENT_ACCOUNT)
    private BigDecimal clientAccount = new BigDecimal("0");

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = ID_USER, referencedColumnName = ID_USER)
    protected User user;
    @OneToMany(mappedBy = CLIENT, fetch = FetchType.LAZY)
    private Set<Order> orders;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return loyaltyPoints == client.loyaltyPoints && block == client.block && Objects.equals(idClient, client.idClient) && Objects.equals(clientAccount, client.clientAccount) && Objects.equals(orders, client.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, loyaltyPoints, block, clientAccount, orders);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", loyaltyPoints=" + loyaltyPoints +
                ", block=" + block +
                ", clientAccount=" + clientAccount +
                '}';
    }
}
