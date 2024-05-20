package com.example.servicoderemessauser;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ServicoDeRemessaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoDeRemessaUserApplication.class, args);
	}

}
