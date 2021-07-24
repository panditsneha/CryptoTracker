package com.example.cryptotracker;

public class CoinModel {
    public String name;
    public String symbol;
    public Double price_usd;

    public CoinModel() {

    }

    public CoinModel(String name, String symbol, Double price_usd) {
        this.name = name;
        this.symbol = symbol;
        this.price_usd = price_usd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_used) {
        this.price_usd = price_usd;
    }
}
