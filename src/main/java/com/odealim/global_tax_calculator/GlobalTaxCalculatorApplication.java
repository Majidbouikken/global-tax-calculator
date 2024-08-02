package com.odealim.global_tax_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.odealim.global_tax_calculator")
public class GlobalTaxCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalTaxCalculatorApplication.class, args);
	}
}
