package by.kharchenko.springbootcafe.dto;

import by.kharchenko.springbootcafe.model.Role;
import by.kharchenko.springbootcafe.validator.annotation.Birthday;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

import static by.kharchenko.springbootcafe.controllers.DbColumn.USER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("idUser")
    private BigInteger idUser;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 4, max = 16, message = "name must be between 4 and 16 characters")
    @Pattern(regexp = "^[A-Z][a-z]+", message = "Invalid")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "surname must not be empty")
    @Size(min = 4, max = 16, message = "surname must be between 4 and 16 characters")
    @Pattern(regexp = "^[A-Z](([a-z]{1,}(['-]*)[a-z]*)|(['-][a-z]+))", message = "Invalid")
    @JsonProperty("surname")
    private String surname;

    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    @JsonProperty("login")
    private String login;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthday must not be null or empty")
    @Birthday(year = 12, message = "year more then 12")
    @JsonProperty("birthday")
    private Date birthday;

    @Pattern(regexp = "\\+375(33|29|25|44)\\d{7}", message = "Invalid")
    @NotEmpty(message = "phone number must not be empty")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be valid")
    @JsonProperty("email")
    private String email;

    @JsonProperty("photoPath")
    private String photoPath;

    @JsonProperty("stringPhoto")
    private String stringPhoto;

    @JsonProperty("role")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = USER)
    @JsonProperty("administrator")
    private AdministratorDTO administrator;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = USER)
    @JsonProperty("client")
    private ClientDTO client;
}
