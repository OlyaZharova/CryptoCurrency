package com.id_finance.test_project.model;

public class UserInfo {
    private String username;
    private String symbol;
    private Double price;

    public UserInfo(String username, String symbol, Double price) {
        this.username = username;
        this.symbol = symbol;
        this.price = price;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
