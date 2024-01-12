package com.joaoneto.parkinglot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingLotApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ParkingLotApplication.class);

	public static void main(String[] args) {
		LOG.info("Starting application");
		SpringApplication.run(ParkingLotApplication.class, args);
		LOG.info("Application started");
	}
}
