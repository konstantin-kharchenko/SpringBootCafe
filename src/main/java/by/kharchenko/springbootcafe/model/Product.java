package by.kharchenko.springbootcafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = PRODUCTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity implements Serializable, Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ID_PRODUCT)
    private BigInteger idProduct;

    @Column(name = NAME)
    private String name;

    @Column(name = VALIDITY_DATE)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date validityDate;

    @Temporal(TemporalType.DATE)
    @Column(name = REGISTRATION_TIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date registrationTime;

    @Column(name = PRICE)
    private BigDecimal price;

    @Column(name = PHOTO_PATH)
    private String photoPath;

    @OneToMany(mappedBy = "product")
    Set<IngredientGrams> grams;

    transient private String stringPhoto;

    @ManyToMany(mappedBy = PRODUCTS)
    private Set<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = ID_PRODUCT), inverseJoinColumns = @JoinColumn(name = ID_INGREDIENT))
    private Set<Ingredient> ingredients;

    @Override
    public int compareTo(Product o) {
        return this.registrationTime.compareTo(o.registrationTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(name, product.name) && Objects.equals(validityDate, product.validityDate) && Objects.equals(registrationTime, product.registrationTime) && Objects.equals(price, product.price) && Objects.equals(photoPath, product.photoPath) && Objects.equals(grams, product.grams) && Objects.equals(stringPhoto, product.stringPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, name, validityDate, registrationTime, price, photoPath, grams, stringPhoto);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idProduct = " + idProduct + ", " +
                "name = " + name + ", " +
                "validityDate = " + validityDate + ", " +
                "registrationTime = " + registrationTime + ", " +
                "price = " + price + ", " +
                "photoPath = " + photoPath + ", " +
                "stringPhoto = " + stringPhoto + ")";
    }
}
