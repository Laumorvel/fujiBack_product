package com.example.product.controller;

import com.example.product.DTO.ProductDTO;
import com.example.product.error.ApiError;
import com.example.product.error.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * Consigue los productos guardados
     *
     * @return lista de productos DTOProduct
     */
    @GetMapping("/product")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }


    /**
     * Modifica un producto concreto
     *
     * @param id
     * @param product
     * @return producto modificado DTOProduct
     */
    @PutMapping("/product/{id}")
    public ProductDTO modifyProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.modifyProduct(id, product);
    }

    /**
     * Añade el id de usuarios a los productos
     *
     * @return lista de productos modificados DTOProduct
     */
    @PutMapping("/products")
    public List<ProductDTO> addUsersIdsToProduct() {
        return productService.addUsersIdsToProducts();
    }

    /**
     * Añade un nuevo producto a la bbdd
     *
     * @param product
     * @return copia del producto añadido
     */
    @PostMapping("/product")
    public ProductDTO addProduct(@RequestBody ProductDTO product) {
        return productService.addProduct(product);
    }

    /**
     * Elimina un producto
     *
     * @param id
     */
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    /**
     * Consigue una lista de productos de un usuario concreto
     *
     * @param userId
     * @return lista de tipo ProductDTO
     */
    @GetMapping("/product/{userId}")
    public List<ProductDTO> getProductsOfClient(@PathVariable String userId) {
        return productService.getProductsUser(userId);
    }

    //CONTROL DE EXCEPCIONES-----------------------------------------------

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductoNoEncontrado(ProductNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setEstado(HttpStatus.NOT_FOUND);
        apiError.setFecha(LocalDateTime.now());
        apiError.setMensaje(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

}
