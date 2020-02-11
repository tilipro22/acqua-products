package com.tili.acquaproducts.app.productManagement;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private String model;
    private String color;
    private Integer stock;
    private Double price;
    private Integer discount;
    private Character gender;
    private String size;
    private String description;

    public Product() {
    }

    public Product(String name, String type, String model,  String color, Integer stock, Double price, Integer discount, Character gender, String size) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.color = color;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
        this.gender = gender;
        this.size = size;
        this.description = "";
    }

    public Product(String name, String type, String model, String color, Integer stock, Double price, Integer discount, Character gender, String size, String description) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.color = color;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
        this.gender = gender;
        this.size = size;
        this.description = description;
    }
}
