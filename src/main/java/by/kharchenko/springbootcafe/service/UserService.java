package by.kharchenko.springbootcafe.service;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Role;
import by.kharchenko.springbootcafe.model.User;

import java.math.BigInteger;
import java.util.Optional;

public interface UserService extends BaseService<User> {
    Optional<Role> authenticate(String login, String password) throws ServiceException;

    Optional<BigInteger> idByLogin(String login);
}
