package com.chargepoint.authservice.db.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.chargepoint.authorization.db.domain.DriverAuthorization;
import com.chargepoint.authorization.repo.DriverAuthorizationRepository;

@Configuration
public class DataLoader {

	 @Bean
	    CommandLineRunner initDatabase(DriverAuthorizationRepository repository) {
	        return args -> {
	            repository.save(new DriverAuthorization("valid-id-12345678901234567890", true));
	            repository.save(new DriverAuthorization("rejected-id-12345678901234567890", false));
	        };
	    }
}
