package com.academia.plataformaAcademica.config;

import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordAutoHashConfig {

    @Bean
    public org.springframework.boot.CommandLineRunner autoHashPasswords(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            for (UsuarioPlataforma u : usuarioRepository.findAll()) {

                String pwd = u.getPassword();

                // Si no tiene contraseña, lo omitimos
                if (pwd == null || pwd.isBlank()) {
                    continue;
                }

                // Si la contraseña NO está en formato BCrypt la encriptamos
                if (!pwd.startsWith("$2a$") &&
                    !pwd.startsWith("$2b$") &&
                    !pwd.startsWith("$2y$")) {

                    String hashed = passwordEncoder.encode(pwd);
                    u.setPassword(hashed);
                    usuarioRepository.save(u);

                    System.out.println("Contraseña autocifrada para usuario: " + u.getEmail());
                }
            }
        };
    }
}
