package com.example.product.repository;

import com.example.product.DTO.ProductDTO;
import com.example.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product getById(String id);

    List<Product>getByUserId(String id);
}
