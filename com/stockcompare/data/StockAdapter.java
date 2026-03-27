package com.stockcompare.data;

import com.stockcompare.domain.StockData;

public class StockAdapter {

    public static StockData adaptRawData(String symbol, double price, String date) {
        StockData stock = new StockData();
        stock.setSymbol(symbol);
        stock.setPrice(price);
        stock.setDate(date);
        return stock;
    }
}