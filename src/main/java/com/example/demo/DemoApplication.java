package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			if (!userRepo.existsByUsername("admin1")) {
				User admin = new User();
				admin.setName("Admin");
				admin.setEmail("admin@email.com");
				admin.setUsername("admin1");
				admin.setPassword(passwordEncoder.encode("adminpass")); // hash the password!
				admin.setAuthMethod("email");
				admin.setRole("ADMIN");
				userRepo.save(admin);
			}
		};
	}
}
