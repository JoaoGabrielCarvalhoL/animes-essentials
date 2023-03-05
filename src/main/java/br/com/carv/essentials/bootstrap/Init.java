package br.com.carv.essentials.bootstrap;

import br.com.carv.essentials.config.SecurityConfig;
import br.com.carv.essentials.domain.User;
import br.com.carv.essentials.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Init implements CommandLineRunner  {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    Logger logger = Logger.getLogger(Init.class.getSimpleName());

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        User user = new User("Batman", securityConfig.encoder().encode("BruceWayne"), "27.joaogabriel@gmail.com", "ROLE_ADMIN, ROLE_USER");
        userRepository.save(user);
        logger.info("User: Batman");



    }
}
