package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StocksPortfolio{

    private final IStockmarketService stockMarket;
    private final List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockMarket) {
        this.stocks = new ArrayList<>();
        this.stockMarket = stockMarket;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double totalValue() {
        double total = 0.0;

        for (Stock stk : this.stocks) {
            total += stk.getQuantity() * this.stockMarket.lookUpPrice(stk.getLabel());
        }
        return total;
    }

    public List<Stock> mostValuableStocks(int n) {
        if (n <= 0) return Collections.emptyList();
        List<Stock> mvs = this.stocks.stream()
                .sorted((first, second) -> Double.compare(
                        this.stockMarket.lookUpPrice(second.getLabel()) * second.getQuantity(),
                        this.stockMarket.lookUpPrice(first.getLabel()) * first.getQuantity()
                ))
                .limit(n)
                .toList();
        return mvs;
    }
}
