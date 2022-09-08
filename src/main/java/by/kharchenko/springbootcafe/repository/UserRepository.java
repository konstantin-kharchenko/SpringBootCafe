package by.kharchenko.springbootcafe.repository;

import by.kharchenko.springbootcafe.model.Role;
import by.kharchenko.springbootcafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {

    @Query("select u.password from User u where u.login = ?1")
    String findPasswordByLogin(String login);

    @Query("from User u where u.login = ?1")
    Optional<User> findByLogin(String login);

    @Query("select u.role from User u where u.login = ?1")
    Optional<Role> findRoleByLogin(String login);

    @Query("select u.idUser from User u where u.login = ?1")
    Optional<BigInteger> findIdByLogin(String login);

    @Modifying
    @Query("update User SET photoPath = ?1 where idUser = ?2")
    void updatePhoto(String path, BigInteger id);

    @Query("SELECT u.idUser from User u WHERE u.login = ?1 AND u.idUser <> ?2")
    Optional<BigInteger> findAnotherIdByLogin(String login, BigInteger idUser);

}
