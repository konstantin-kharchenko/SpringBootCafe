package by.kharchenko.springbootcafe.model.dto;

import by.kharchenko.springbootcafe.model.Status;
import by.kharchenko.springbootcafe.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

import static by.kharchenko.springbootcafe.controllers.DbColumn.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDTO {

    @JsonProperty("idAdministrator")
    private BigInteger idAdministrator;

    @JsonProperty("experience")
    private double experience;

    @JsonProperty("status")
    private Status status = Status.WAITING;
}
