package com.example.zalla.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String productsId;
    private String productsCode;
    private String productName;
    private String productImage;
    private String categoryName;
    private String subCategoryName;
    private int quantity;
    private int price;
    private float weight;
    private String desc;

    public Products(String productsId, String productsCode, String productName,
                    String productImage, String categoryName, String subCategoryName,
                    int quantity, int price, float weight, String desc){
        this.productsId = productsId;
        this.productsCode = productsCode;
        this.productName = productName;
        this.productImage = productImage;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.quantity = quantity;
        this.price = price;
        this.weight = weight;
        this.desc = desc;
    }


    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getProductsCode() {
        return productsCode;
    }

    public void setProductsCode(String productsCode) {
        this.productsCode = productsCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
