package com.ws.db;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ws.dm.Employee;
import com.ws.rep.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class LoadDatabase {

	private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args -> {
			logger.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
			logger.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
		};
	}

}
