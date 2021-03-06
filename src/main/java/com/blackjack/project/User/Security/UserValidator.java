package com.blackjack.project.User.Security;

import com.blackjack.project.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final String REQUIRED = "required";
    private static final String USERNAME_SIZE = "username_size";
    private static final String USERNAME_DUPLICATE = "username_duplicate";
    private static final String PASSWORD_SIZE = "password_size";
    private static final String PASSWORD_MATCH = "password_match";


    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String username = user.getUsername();
        String password = user.getPassword();
        String passwordConfirm = user.getPasswordConfirm();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();


        //Username validation
        if (!StringUtils.hasLength(username)) {
            errors.rejectValue("username", REQUIRED);
        }
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", USERNAME_SIZE, USERNAME_SIZE);
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", USERNAME_DUPLICATE, USERNAME_DUPLICATE);
        }

        //Password validation
        if (!StringUtils.hasLength(password)) {
            errors.rejectValue("password", REQUIRED, REQUIRED);
        }
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", PASSWORD_SIZE, PASSWORD_SIZE);
        }

        if (!StringUtils.hasLength(password)) {
            errors.rejectValue("passwordConfirm", REQUIRED, REQUIRED);
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", PASSWORD_MATCH, PASSWORD_MATCH);
        }


        //First name validation
        if (!StringUtils.hasLength(firstName)) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        //Last name validation
        if (!StringUtils.hasLength(lastName)) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }

        //Email validation
        if (!StringUtils.hasLength(email)) {
            errors.rejectValue("email", REQUIRED, REQUIRED);
        }

        // birth date validation
        if (user.getBirthday() == null) {
            errors.rejectValue("birthday", REQUIRED, REQUIRED);
        }

    }
}
