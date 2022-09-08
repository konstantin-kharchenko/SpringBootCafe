package by.kharchenko.springbootcafe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Entity
@Table(name = ADMINISTRATORS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_ADMINISTRATOR)
    private BigInteger idAdministrator;

    @Column(name = EXPERIENCE)
    private double experience;

    @OneToOne
    @JoinColumn(name = ID_USER, referencedColumnName = ID_USER)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = ID_STATUS)
    private Status status = Status.WAITING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Double.compare(that.experience, experience) == 0 && Objects.equals(idAdministrator, that.idAdministrator) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdministrator, experience, status);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "idAdministrator=" + idAdministrator +
                ", experience=" + experience +
                ", status=" + status +
                '}';
    }
}
