package ru.itmo.lab1.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.lab1.common.validation.ValidationLimits;
import ru.itmo.lab1.user.AppUser;
import ru.itmo.lab1.user.UserRepository;

@Component
public class InitialUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String login;
    private final String password;

    public InitialUserInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${INITIAL_USER_LOGIN}") String login,
            @Value("${INITIAL_USER_PASSWORD}") String password
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.login = login;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments arguments) {
        if (login.isBlank() || login.length() > ValidationLimits.LOGIN_MAX_LENGTH) {
            throw new IllegalStateException(
                    "INITIAL_USER_LOGIN must not be blank and must not exceed 100 characters"
            );
        }
        if (!BcryptPasswordPolicy.supports(password)) {
            throw new IllegalStateException(
                    "INITIAL_USER_PASSWORD must not be blank and must not exceed 72 UTF-8 bytes"
            );
        }

        if (userRepository.findByLogin(login).isEmpty()) {
            userRepository.save(new AppUser(login, passwordEncoder.encode(password)));
        }
    }
}
