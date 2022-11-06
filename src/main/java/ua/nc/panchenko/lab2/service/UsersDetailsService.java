package ua.nc.panchenko.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.repository.UsersRepository;
import ua.nc.panchenko.lab2.security.UsersDetails;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UsersDetails(user.get());
    }

    public Optional<User> loadUserByUsernameForValidation(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user;
    }
}
