package com.example.product.error;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("Product not found");
    }
}
