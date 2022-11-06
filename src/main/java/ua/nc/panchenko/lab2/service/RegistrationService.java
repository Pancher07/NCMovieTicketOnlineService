package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nc.panchenko.lab2.models.Basket;
import ua.nc.panchenko.lab2.models.Role;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.repository.UsersRepository;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUsername().equals("admin")) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_USER);
        }
        user.setBasket(new Basket(user));
        usersRepository.save(user);
    }
}
