package com.stockcompare.domain.model;

/**
 * ShareDetail — matches ShareDetail[] return type in your ISearchShare interface diagram.
 * Used by SearchService → StockAPI → fetchSymbols().
 */
public class ShareDetail {
    public final String symbol;
    public final String companyName;
    public final String exchange;

    public ShareDetail(String symbol, String companyName, String exchange) {
        this.symbol      = symbol.toUpperCase();
        this.companyName = companyName;
        this.exchange    = exchange;
    }

    public ShareDetail(String symbol, String companyName) {
        this(symbol, companyName, "");
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-40s | %s", symbol, companyName, exchange);
    }
}
