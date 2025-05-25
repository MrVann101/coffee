package com.example.project4.repositories;

public class ProductRepositorySingleton {
    private static final ProductRepository INSTANCE = new ProductRepository();

    public static ProductRepository getInstance() {
        return INSTANCE;
    }
}
