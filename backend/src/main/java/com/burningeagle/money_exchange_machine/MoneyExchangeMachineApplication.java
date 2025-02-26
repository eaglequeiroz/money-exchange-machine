package com.burningeagle.money_exchange_machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MoneyExchangeMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyExchangeMachineApplication.class, args);
	}

}
