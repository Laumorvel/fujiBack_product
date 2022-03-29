package com.example.product;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	/**
	 * Necesario crear el bean para registrar el objeto restTemplate
	 * @return nueva instancia del objeto
	 */
	@Bean
	public RestTemplate registerRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	CommandLineRunner initData (ProductRepository productRepo){
		productRepo.deleteAll();
		Product prod1 = new Product("Potato", "BestPotatoesEver", 300, 25.0);
		Product prod2 = new Product("Hammer", "Ham-mer", 23, 50.0);
		Product prod3 = new Product("Mug", "MrMugger", 30, 26.0);

		return(args) -> {
			productRepo.saveAll(Arrays.asList(prod1, prod2, prod3));
		};
	}

}
