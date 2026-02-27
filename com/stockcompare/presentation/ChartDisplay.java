package com.stockcompare.presentation;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * COMPONENT: ChartDisplay
 * PROVIDED INTERFACE: IChartDisplay
 * REQUIRED INTERFACES: None
 */
public class ChartDisplay implements IChartDisplay {
    
    @Override
    public void renderChart(List<StockData> data) {
        System.out.println("\n┌─ Stock Price Chart ───────────────────────┐");
        
        if (data == null || data.isEmpty()) {
            System.out.println("│ No data to display                        │");
            System.out.println("└───────────────────────────────────────────┘\n");
            return;
        }
        
        System.out.printf("│ %-12s %12s %10s │%n", "Date", "Close", "Volume");
        System.out.println("├───────────────────────────────────────────┤");
        
        int count = Math.min(10, data.size());
        for (int i = 0; i < count; i++) {
            StockData stock = data.get(i);
            System.out.printf("│ %-12s $%11.2f %,10d │%n",
                stock.getDate(),
                stock.getClose(),
                stock.getVolume()
            );
        }
        
        if (data.size() > 10) {
            System.out.printf("│ ... and %d more records                   │%n", data.size() - 10);
        }
        
        System.out.println("└───────────────────────────────────────────┘\n");
    }
    
    @Override
    public void renderComparisonChart(List<StockData> data1, List<StockData> data2, 
                                       String symbol1, String symbol2) {
        System.out.println("\n┌─ Stock Comparison ────────────────────────┐");
        System.out.printf("│ %s vs %s%n", symbol1, symbol2);
        System.out.println("├───────────────────────────────────────────┤");
        
        int count = Math.min(5, Math.min(data1.size(), data2.size()));
        
        for (int i = 0; i < count; i++) {
            System.out.printf("│ %s: $%-8.2f  %s: $%-8.2f │%n",
                symbol1, data1.get(i).getClose(),
                symbol2, data2.get(i).getClose()
            );
        }
        
        System.out.println("└───────────────────────────────────────────┘\n");
    }
    
    @Override
    public void clearChart() {
        System.out.println("[Chart cleared]");
    }
    
    @Override
    public void updateChart(List<StockData> data) {
        renderChart(data);
    }
    
    @Override
    public void setChartTitle(String title) {
        System.out.println("=== " + title + " ===");
    }
    
    @Override
    public void configureChart(boolean showGrid, boolean showLegend) {
        // No-op for console display
    }
}
