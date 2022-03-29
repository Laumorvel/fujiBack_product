package com.example.product.service;

import com.example.product.DTO.ProductDTO;
import com.example.product.error.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    /**
     * Inyecta el restTemplate para poder comunicar los microservicios a través de la url que indiquemos
     */
    @Autowired
    private RestTemplate restTemplate;

    //Microservicio de person
    static final String URL_BASE = "http://localhost:9000/";


    /**
     * Consigue los productos guardados
     *
     * @return lista de tipo DAOProduct con todos los productos
     */
    public List<ProductDTO> getProducts() {
        //Obtengo los productos del repo
        List<Product> productos = productRepository.findAll();
        //Creo la lista para devolverla
        List<ProductDTO> productsList = new ArrayList<>();
        //Los recorro y genero su réplicado de tipo DAOProduct para devolver la lista creada
        productos.forEach(product -> {
            productsList.add(new ProductDTO(product.getId(), product.getName(), product.getBrand(), product.getUnits(), product.getPrice(), product.getUserId()));
        });
        return productsList;
    }

    /**
     * Modifica el producto indicado.
     *
     * @param id
     * @param product
     * @return devuelve una copia del producto de tipo DTOProduct habiendo guardado previamente el Product en la bbdd
     */
    public ProductDTO modifyProduct(String id, Product product) {
        Product p;
        try {
            p = productRepository.getById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
        //Guardo los cambios modificando el producto
        product.setId(id);
        productRepository.save(p);
        //Devuelvo la copia del producto
        return new ProductDTO(product.getId(), product.getName(), product.getBrand(), product.getUnits(), product.getPrice(), product.getUserId());
    }

    /**
     * Añade un id de usuario a los productos.
     * Para ello, consigue los ids a través del restTemplate inyectado y se modifican los productos añadiendo dicho dato.
     *
     * @return copia de los productos modificados (DTOProduct)
     */
    public List<ProductDTO> addUsersIdsToProducts() {
        //Consigo la lista de ids de los usuarios para añadírselos a los productos
        //Lo transformo en lista
        String[] ids = restTemplate.getForObject(URL_BASE + "usersId", String[].class);
        List<String> listIds = Arrays.asList(ids);

        //Modifico los productos (añado usersIds) y los guardo
        List<Product> productos = productRepository.findAll();
        productos.get(0).setUserId(listIds.get(0));
        productos.get(1).setUserId(listIds.get(1));
        productos.get(2).setUserId(listIds.get(2));
        productRepository.saveAll(productos);

        //Creo la lista para devolverla
        List<ProductDTO> productsList = new ArrayList<>();

        //Los recorro y genero su réplica de tipo DAOProduct para devolver la lista creada
        productos.forEach(product -> {
            productsList.add(new ProductDTO(product.getId(), product.getName(), product.getBrand(), product.getUnits(), product.getPrice(), product.getUserId()));
        });
        return productsList;
    }

    /**
     * Crea y guarda un nuevo producto
     *
     * @param product
     * @return copia del producto
     */
    public ProductDTO addProduct(ProductDTO product) {
        Product p = new Product(product.getId(), product.getName(), product.getBrand(), product.getUnits(), product.getPrice(), product.getUserId());
        productRepository.save(p);
        product.setId(product.getId());
        return product;
    }

    /**
     * Elimina el producto de la bbdd
     *
     * @param productId
     */
    public void deleteProduct(String productId) {
        //Consigo el producto que se quiere eliminar
        try {
            Product p = productRepository.getById(productId);
            productRepository.delete(p);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    /**
     * Consigue la lista de productos que pertenece a un usuario en concreto
     * @param id
     * @return list ProductDTO
     */
    public List<ProductDTO> getProductsUser(String id) {
        List<ProductDTO> productsList = new ArrayList<>();
        List<Product> products = productRepository.getByUserId(id);
        products.forEach(product -> {
            productsList.add(new ProductDTO(product.getId(), product.getName(), product.getBrand(), product.getUnits(), product.getPrice(), product.getUserId()));
        });
        return productsList;
    }

}
