package com.itechart.javalab.webservice.rest.dao;

import com.itechart.javalab.webservice.rest.model.Product;
import com.itechart.javalab.webservice.rest.model.Review;
import com.itechart.javalab.webservice.rest.util.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductsStorage {
    private static Map<Integer, Product> products = initProducts();

    private static Map<Integer, Product> initProducts() {
        Map<Integer, Product> initialProducts = new HashMap<>();
        Product product1 = new Product("t-shirt", "red", "clothes", BigDecimal.valueOf(13), 10);
        Product product2 = new Product("lego", "technic", "toys", BigDecimal.valueOf(150), 2);
        Product product3 = new Product("The Lord of the Rings", "part 1", "books", BigDecimal.valueOf(30), 15);
        initialProducts.put(product1.getId(), product1);
        initialProducts.put(product2.getId(), product2);
        initialProducts.put(product3.getId(), product3);
        return initialProducts;


    }

    public static Product findById(Integer id) {
        return products.get(id);
    }

    public static Set<Product> search(String searchText, BigDecimal maxPrice) {
        return products.values().stream().filter(product -> {
            boolean filter = true;
            if (searchText != null) {
                filter = product.getTitle().contains(searchText) || product.getDescription().contains(searchText);
            }
            if (maxPrice != null) {
                filter = filter && product.getPrice().compareTo(maxPrice) < 0;
            }
            return filter && product.getAmount() > 0;
        }).collect(Collectors.toSet());
    }

    public static Product addProduct(Product product) {
        Integer id = IdGenerator.nextId();
        product.setId(id);
        product.setModifiedDateTime(LocalDateTime.now());
        products.put(id, product);
        return product;
    }

    public static Product updateProduct(Integer id, Product product) {
        Product existingProduct = products.get(id);
        if (existingProduct != null) {
            existingProduct.setTitle(product.getTitle());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setAmount(product.getAmount());
            existingProduct.setModifiedDateTime(LocalDateTime.now());
        }
        return existingProduct;
    }

    public static Product deleteProduct(Integer id) {
        return products.remove(id);
    }

    public static Product addReview(Integer id, Review review) {
        Product product = products.get(id);
        if (product != null) {
            product.getReviews().add(review);
        }
        return product;
    }


}
