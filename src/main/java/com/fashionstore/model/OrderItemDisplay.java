package com.fashionstore.model;

public class OrderItemDisplay {

private String productName;
private String brand;
private String size;
private String imageUrl;

private int quantity;

private double price;

private double subtotal;

public String getProductName() {
    return productName;
}

public void setProductName(String productName) {
    this.productName = productName;
}

public String getBrand() {
    return brand;
}

public void setBrand(String brand) {
    this.brand = brand;
}

public String getSize() {
    return size;
}

public void setSize(String size) {
    this.size = size;
}

public String getImageUrl() {
    return imageUrl;
}

public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
}

public int getQuantity() {
    return quantity;
}

public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public double getPrice() {
    return price;
}

public void setPrice(double price) {
    this.price = price;
}

public double getSubtotal() {
    return subtotal;
}

public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
}

}
