package com.zzk.springboot.annotation.generic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;

@SpringBootApplication
public class CustomConfiguration {
	@Bean
	@CarQualifier
	public Car getMercedes() {
		return new Car("E280", "Mercedes", "Diesel");
	}

	@Bean
	@CarQualifier
	public Car getBmw() {
		return new Car("M5", "BMW", "Petrol");
	}

	@Bean
	public Motorcycle getSuzuki() {
		return new Motorcycle("Yamaguchi", "Suzuki", true);
	}

	public static void main(String[] args) throws NoSuchFieldException {
		ConfigurableApplicationContext context = SpringApplication.run(CustomConfiguration.class, args);
		CarHandler carHandler = context.getBean(CarHandler.class);

		ResolvableType vehiclesType = ResolvableType.forField(carHandler.getClass().getDeclaredField("vehicles"));
		System.out.println(vehiclesType);
		ResolvableType type = vehiclesType.getGeneric();
		System.out.println(type);
		Class<?> aClass = type.resolve();
		System.out.println(aClass);

		carHandler.getVehicles().forEach(System.out::println);
	}
}
