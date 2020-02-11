package com.tili.acquaproducts.app.productManagement;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Could not find the product " + id);
    }
}
