package com.example.gestion_produit.model;

public class ProductCategory {
    Integer productid;
    String productName;

    public ProductCategory(Integer productid, String productName) {
        this.productid = productid;
        this.productName = productName;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
