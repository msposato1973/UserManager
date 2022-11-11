package com.demo;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

 
import com.demo.entity.User;
import com.demo.repository.UserRepository;


@SpringBootApplication
@EnableAutoConfiguration
public class DemoUserManagerApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoUserManagerApplication.class);

	public static void main(String[] args) {
		logger.info("main - DemoUserManagerApplication.run: begin");
		SpringApplication.run(DemoUserManagerApplication.class, args);
		System.out.println( "DemoUserManagerApplication");
	}

	@Bean
	CommandLineRunner initializeData(@Autowired UserRepository userRepository ) {
		 logger.info("main - DemoUserManagerApplication.initializeData: begin");
		 return args -> {
		 
			 

			 Collection<User> listUser =  Arrays.asList(
					 new User("Max", "Sposato", "maxp73.uk@gmail.com"), 
					 new User("Mary", "Teamseal","MaryTeamseal@gmail.com"),
					 new User("Mary", "Wolf","Mary.uk@gmail.com"), 
					 new User("Mary", "Gallagher","Mary73.Gallagher@gmail.com"), 
					 new User("Marrie", "Smith","Marrie73.uk@gmail.com"), 
					 new User("Bill", "Billy", "Bill.Billy@gmail.com"), 
					 new User("Marien", "Teams","Mary5.uk@gmail.com"), 
					 new User("Marryl", "Wolf","Maryll.uk@gmail.com"), 
					 new User("Mark", "Gallagher","MarkG.uk@gmail.com"), 
					 new User("Marrie2", "Teamseal","Marrie2.Teamseal@gmail.com"),
					 new User("Max3", "Sposato", "maxp73.uk@gmail.com"), 
					 new User("Mary3", "Teamseal","Mary3.uk@gmail.com"), 
					 new User("Mary4", "Wolf","Mary4.Wolf@gmail.com"), 
					 new User("Mary3", "Gallagher","Mary3.Gallagher@gmail.com"), 
					 new User("Marrie3", "Smith","Marrie73.uk@gmail.com"), 
					 new User("Bill3", "Billy", "Bill3.uk@gmail.com"), 
					 new User("Marien3", "Teams","Mary5.uk@gmail.com"), 
					 new User("Marry3l", "Wolf","Marry3l.uk@gmail.com"), 
					 new User("Mark3", "Gallagher","Mark3.uk@gmail.com"), 
					 new User("Marrie3", "Teamseal","Marrie3.Teamseal.uk@gmail.com")
					 
			 );
		 
			 
		 
			 userRepository.saveAll(listUser);
	     };
	}
}
