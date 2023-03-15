package com.example.inventory;

public class Gizmo implements Comparable<Gizmo> {
    private String productName;
    private int productNumber;
    private int year;
    private int quantity;
    private double price;

    public Gizmo(String productName, int productNumber, int year, int quantity, double price) {
        this.productName = productName;
        this.productNumber = productNumber;
        this.year = year;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public int getYear() {
        return year;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Gizmo otherGizmo) {
        return Integer.compare(year, otherGizmo.getYear());
    }

    @Override
    public String toString() {
        return String.format("%-10s %10d %10d %10d %10.2f", productName, productNumber, year, quantity, price);
    }
}

