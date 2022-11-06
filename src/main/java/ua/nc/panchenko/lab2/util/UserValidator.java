package ua.nc.panchenko.lab2.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.nc.panchenko.lab2.models.User;
import ua.nc.panchenko.lab2.service.UsersDetailsService;

@Component
public class UserValidator implements Validator {
    private final UsersDetailsService usersDetailsService;

    @Autowired
    public UserValidator(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (usersDetailsService.loadUserByUsernameForValidation(user.getUsername()).isPresent())
            errors.rejectValue("username", "", "A person with this username already exists");
    }
}
