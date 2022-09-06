package by.kharchenko.springbootcafe.service.impl;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Administrator;
import by.kharchenko.springbootcafe.model.Client;
import by.kharchenko.springbootcafe.model.Role;
import by.kharchenko.springbootcafe.model.User;
import by.kharchenko.springbootcafe.repository.AdminRepository;
import by.kharchenko.springbootcafe.repository.ClientRepository;
import by.kharchenko.springbootcafe.repository.UserRepository;
import by.kharchenko.springbootcafe.service.UserService;
import by.kharchenko.springbootcafe.util.email.CustomMailSender;
import by.kharchenko.springbootcafe.util.encryption.EncryptionPassword;
import by.kharchenko.springbootcafe.util.filereadwrite.FileReaderWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String APP_MAIL = "cafe.from.app@mail.ru";
    private static final String REGISTRATION_HEAD_MAIL = "Registration message";
    private static final String REGISTRATION_TEXT_MAIL = "Congratulations on your successful registration in the cafe app";
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final FileReaderWriter readerWriter;
    private final CustomMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FileReaderWriter readerWriter, CustomMailSender mailSender, ClientRepository clientRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.readerWriter = readerWriter;
        this.mailSender = mailSender;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public boolean delete(User user) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean add(User user) throws ServiceException {
        user.setRegistrationTime(new Date());
        boolean isLoginExists = userRepository.idByLogin(user.getLogin()).isPresent();
        if (isLoginExists) {
            return false;
        }
        String encryptionPassword = EncryptionPassword.encryption(user.getPassword());
        user.setPassword(encryptionPassword);
        switch (user.getRole()) {
            case CLIENT -> {
                Client client = new Client();
                client.setUser(user);
                if (clientRepository.save(client).equals(client)) {
                    mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                    return true;
                }
            }
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                administrator.setUser(user);
                if (adminRepository.save(administrator).equals(administrator)) {
                    mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                    return true;
                }
            }
        }
        /*if (userRepository.save(user).equals(user)) {
            mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
            return true;
        }*/
        return false;
    }

    @Override
    @Transactional
    public User findById(BigInteger id) throws ServiceException {
        Optional<User> optionalUser;
        optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPhotoPath() != null) {
                String stringPhoto = readerWriter.readPhoto(user.getPhotoPath());
                user.setStringPhoto(stringPhoto);
            }
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    @Transactional
    public boolean update(User user) throws ServiceException {
        boolean isLoginExists = userRepository.findAnotherIdByLogin(user.getLogin(), user.getIdUser()).isPresent();
        if (isLoginExists) {
            return false;
        }
        Optional<User> dBOptionalUser= userRepository.findById(user.getIdUser());
        if (dBOptionalUser.isPresent()){
            User dBUser = dBOptionalUser.get();
            user.setRegistrationTime(dBUser.getRegistrationTime());
            dBUser.setName(user.getName());
            dBUser.setSurname(user.getSurname());
            dBUser.setPhoneNumber(user.getPhoneNumber());
            dBUser.setLogin(user.getLogin());
            userRepository.save(dBUser);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    @Transactional
    public Optional<Role> authenticate(String login, String password) throws ServiceException {
        String encryptionPassword = EncryptionPassword.encryption(password);
        String dbPassword = userRepository.findPasswordByLogin(login);
        if (dbPassword.equals(encryptionPassword)) {
            return userRepository.findRoleByLogin(login);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<BigInteger> idByLogin(String login) {
        return userRepository.idByLogin(login);
    }
}
