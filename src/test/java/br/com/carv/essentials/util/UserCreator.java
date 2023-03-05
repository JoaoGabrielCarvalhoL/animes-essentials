package br.com.carv.essentials.util;

import br.com.carv.essentials.config.SecurityConfig;
import br.com.carv.essentials.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserCreator {

    public static User createUserAllRoles() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = new User("Batman", encoder.encode("BruceWayne"), "27.joaogabriel@gmail.com", "ROLE_ADMIN, ROLE_USER");
        return user;
    }
}
