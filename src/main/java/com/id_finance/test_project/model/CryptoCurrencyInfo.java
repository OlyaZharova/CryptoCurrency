package com.id_finance.test_project.model;

public class CryptoCurrencyInfo {
    private int id;
    private String symbol;
    private Double price;

    public CryptoCurrencyInfo(int id, String symbol, Double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

    public CryptoCurrencyInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
