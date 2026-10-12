package ru.itmo.lab1.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.lab1.user.AppUser;
import ru.itmo.lab1.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public String login(String login, String password) {
        AppUser user = userRepository.findByLogin(login)
                .orElseThrow(InvalidCredentialsException::new);

        if (!BcryptPasswordPolicy.supports(password)
                || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.issueToken(user.getLogin());
    }
}
