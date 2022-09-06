package by.kharchenko.springbootcafe.service;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Client;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ClientService extends BaseService<Client>{
    boolean updatePhoto(MultipartFile file, BigInteger id) throws ServiceException;

    Client findByUserId(BigInteger id) throws ServiceException;

    boolean addClientAccount(BigDecimal clientAccount, BigInteger idUser);
}
