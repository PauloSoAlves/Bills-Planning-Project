package com.billsplanning.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BillsPlanningApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillsPlanningApplication.class, args);

	}

}
