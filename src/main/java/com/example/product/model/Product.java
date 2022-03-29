package com.example.product.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String brand;
    private Integer units;
    private Double price;
    //Atributo que contiene el id de los usuarios que tienen este producto.
    private String userId;

    public Product(String name, String brand, Integer units, Double price) {
        this.name = name;
        this.brand = brand;
        this.units = units;
        this.price = price;
    }

    public Product(String name, String brand, Integer units, Double price, String idUser) {
        this.name = name;
        this.brand = brand;
        this.units = units;
        this.price = price;
        this.userId = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
