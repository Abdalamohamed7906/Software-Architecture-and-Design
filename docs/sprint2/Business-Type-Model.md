# Business Type Model - Sprint 2

## Stock-Compare Application

**Date:** February 27, 2026  
**Author:** Anwar  
**Sprint:** 2

---

## Overview

The Business Type Model translates the business concepts identified in the Business Concept Model into concrete data types and class structures. This model shows how business domain concepts map to implementation types in Java.

---

## Mapping: Business Concept → Business Type

### 1. User
**Business Concept:** Person using the application

**Business Type:** 
```
NOT IMPLEMENTED AS A CLASS (Sprint 1)
```

**Rationale:** 
- Sprint 1 focuses on core stock analysis functionality
- User authentication/management deferred to future sprints
- User interactions handled through UI components

**Future Consideration:**
```java
class User {
    private String userId;
    private String username;
    private List<String> favoriteSymbols;
    private SearchHistory searchHistory;
}
```

---

### 2. Stock Symbol
**Business Concept:** Unique identifier for a stock

**Business Type:**
```java
Type: String

Examples:
String symbol = "AAPL";
String symbol = "GOOGL";
```

**Validation Rules:**
```java
// Implemented in: StockDataManager.validateSymbol()
boolean validateSymbol(String symbol) {
    if (symbol == null || symbol.trim().isEmpty()) {
        return false;
    }
    return symbol.matches("^[A-Z]{1,5}$");
}
```

**Why String?**
- Simple and widely understood
- No behavior needed (just identification)
- Easy to validate
- Efficient for comparison

---

### 3. Stock Price Data
**Business Concept:** Price information for a stock on a specific date

**Business Type:**
```java
// File: src/com/stockcompare/domain/StockData.java

public class StockData {
    private final String symbol;
    private final LocalDate date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final long volume;
    
    public StockData(String symbol, LocalDate date, 
                     double open, double high, 
                     double low, double close, 
                     long volume) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    // Getters...
    public String getSymbol() { return symbol; }
    public LocalDate getDate() { return date; }
    public double getClose() { return close; }
    // etc...
}
```

**Design Decisions:**
- **Immutable:** All fields are `final` (thread-safe, predictable)
- **Value Object:** Represents a snapshot in time
- **Primitive types:** `double` for prices, `long` for volume
- **LocalDate:** Java 8+ date/time API (no timezone needed)

**Business Rules Enforced:**
- Constructor validates positive prices
- Volume must be non-negative
- Date cannot be null

---

### 4. Date Range
**Business Concept:** Time period for analysis

**Business Type:**
```java
// File: src/com/stockcompare/domain/DateRange.java

public class DateRange {
    private final LocalDate start;
    private final LocalDate end;
    
    public DateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        
        long days = ChronoUnit.DAYS.between(start, end);
        if (days > 730) { // 2 years maximum
            throw new IllegalArgumentException(
                "Date range cannot exceed 2 years (730 days)"
            );
        }
        
        this.start = start;
        this.end = end;
    }
    
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
    
    public long getDays() {
        return ChronoUnit.DAYS.between(start, end);
    }
}
```

**Design Decisions:**
- **Immutable:** Fields are final
- **Validation in Constructor:** Enforces business rules immediately
- **Domain Logic:** `getDays()` method provides business value
- **Fail Fast:** Throws exceptions for invalid ranges

**Business Rules Enforced:**
- ✅ End ≥ Start
- ✅ Maximum 2 years (730 days)
- ✅ No null dates

---

### 5. Stock Comparison
**Business Concept:** Side-by-side analysis of two stocks

**Business Type:**
```java
// File: src/com/stockcompare/service/ComparisonResult.java

public class ComparisonResult {
    private final List<StockData> stock1Data;
    private final List<StockData> stock2Data;
    
    public ComparisonResult(List<StockData> stock1Data, 
                           List<StockData> stock2Data) {
        this.stock1Data = stock1Data;
        this.stock2Data = stock2Data;
    }
    
    public List<StockData> getStock1Data() { 
        return stock1Data; 
    }
    
    public List<StockData> getStock2Data() { 
        return stock2Data; 
    }
}
```

**Design Decisions:**
- **Simple Container:** Holds both datasets
- **No calculation logic:** Separated into PerformanceMetrics
- **Immutable references:** Final fields

---

### 6. Performance Metrics
**Business Concept:** Calculated comparison metrics

**Business Type:**
```java
// File: src/com/stockcompare/service/PerformanceMetrics.java

public class PerformanceMetrics {
    private final double stock1Return;
    private final double stock2Return;
    private final double relativeDifference;
    
    public PerformanceMetrics(double stock1Return, 
                             double stock2Return) {
        this.stock1Return = stock1Return;
        this.stock2Return = stock2Return;
        this.relativeDifference = stock1Return - stock2Return;
    }
    
    public double getStock1Return() { 
        return stock1Return; 
    }
    
    public double getStock2Return() { 
        return stock2Return; 
    }
    
    public double getRelativeDifference() { 
        return relativeDifference; 
    }
    
    public String getBetterPerformer() {
        return stock1Return > stock2Return ? "Stock 1" : "Stock 2";
    }
}
```

**Design Decisions:**
- **Calculated Value Object:** Immutable calculation results
- **Derived Data:** `relativeDifference` calculated in constructor
- **Business Logic:** `getBetterPerformer()` provides business insight

---

### 7. Analysis Results
**Business Concept:** Output of price analysis

**Business Type:**
```java
// Not a separate class - returned as primitive types

Examples:
double priceChange = priceAnalyzer.calculatePriceChange(data);
double avgPrice = priceAnalyzer.calculateAveragePrice(data);
double[] highLow = priceAnalyzer.findHighLow(data);
```

**Design Decision:**
- **Simple Return Types:** No wrapper needed for single values
- **Array for Multiple Values:** `double[]` for high/low pair
- **Service Methods:** Analysis logic in IPriceAnalyzer

**Could be Enhanced:**
```java
class AnalysisResult {
    private double priceChange;
    private double averagePrice;
    private double highPrice;
    private double lowPrice;
    private double volatility;
}
```

---

### 8. Chart/Visualization
**Business Concept:** Graphical representation of data

**Business Type:**
```java
// Represented as Display Actions, not a data type

// IChartDisplay interface methods:
void renderChart(List<StockData> data, String symbol);
void renderComparisonChart(List<StockData> stock1, 
                          List<StockData> stock2,
                          String symbol1, 
                          String symbol2);
```

**Design Decision:**
- **Behavioral Interface:** Charts are actions, not data
- **Console-based (Sprint 1):** ASCII art representation
- **Future Enhancement:** Could create `ChartData` type

**Future Type:**
```java
class ChartData {
    private ChartType type; // LINE, CANDLESTICK, BAR
    private List<DataPoint> dataPoints;
    private ChartConfiguration config;
}

class DataPoint {
    private LocalDate date;
    private double value;
}
```

---

### 9. Data Source
**Business Concept:** Origin of stock data

**Business Type:**
```java
// Represented as Service Interfaces, not data types

IAPIService      // External API (Yahoo Finance)
ICacheManager    // In-memory cache
IRepository      // Persistent storage
```

**Design Decision:**
- **Interface-based:** Data sources are behaviors
- **Strategy Pattern:** Interchangeable implementations
- **No Data Type Needed:** Sources provide StockData objects

---

### 10. Exceptions
**Business Concept:** Error conditions

**Business Type:**
```java
// File: src/com/stockcompare/service/StockDataException.java

public class StockDataException extends Exception {
    public StockDataException(String message) {
        super(message);
    }
    
    public StockDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

**Design Decisions:**
- **Checked Exception:** Forces error handling
- **Domain-Specific:** Represents business rule violations
- **Informative Messages:** Helps debugging

**Usage:**
```java
throw new StockDataException("Invalid stock symbol: " + symbol);
throw new StockDataException("Date range exceeds 2 years");
```

---

## Complete Type Mapping Summary

| Business Concept | Business Type | File Location | Mutability |
|-----------------|---------------|---------------|------------|
| User | *(Not implemented)* | N/A | N/A |
| Stock Symbol | `String` | Built-in | Immutable |
| Stock Price Data | `StockData` | domain/StockData.java | Immutable |
| Date Range | `DateRange` | domain/DateRange.java | Immutable |
| Stock Comparison | `ComparisonResult` | service/ComparisonResult.java | Immutable |
| Performance Metrics | `PerformanceMetrics` | service/PerformanceMetrics.java | Immutable |
| Analysis Results | `double`, `double[]` | Built-in | Immutable |
| Chart | *(Behavioral)* | IChartDisplay interface | N/A |
| Data Source | *(Behavioral)* | Multiple interfaces | N/A |
| Exceptions | `StockDataException` | service/StockDataException.java | Immutable |

---

## Type Design Principles Applied

### 1. **Immutability**
**Why:** Thread-safe, predictable, prevents bugs

**Applied to:**
- All domain objects (StockData, DateRange)
- All value objects (ComparisonResult, PerformanceMetrics)

**How:**
```java
private final String symbol;  // Final field
// No setters, only getters
```

---

### 2. **Value Objects**
**Definition:** Objects defined by their values, not identity

**Examples:**
- `StockData` - Two StockData objects with same values are equal
- `DateRange` - Defined by start and end dates

**Benefits:**
- Can be compared by value
- Can be safely shared
- Reduce bugs from mutation

---

### 3. **Primitive Obsession Avoidance**
**Bad:**
```java
void fetchData(String symbol, String start, String end)
```

**Good:**
```java
void fetchData(String symbol, DateRange dateRange)
```

**Why Better:**
- DateRange encapsulates validation
- Cannot create invalid date range
- More expressive code

---

### 4. **Single Responsibility**
Each type has one clear purpose:
- `StockData` - Holds price information
- `DateRange` - Validates and holds date range
- `ComparisonResult` - Contains comparison data
- `PerformanceMetrics` - Calculates comparison metrics

---

### 5. **Fail Fast**
Validation in constructors:
```java
public DateRange(LocalDate start, LocalDate end) {
    if (end.isBefore(start)) {
        throw new IllegalArgumentException(...);
    }
    // More validation...
}
```

**Benefits:**
- Invalid objects never created
- Errors caught early
- Easier debugging

---

## Type Relationships

```
StockData (domain object)
    ↓ used by
PriceAnalyzer (service)
    ↓ produces
PerformanceMetrics (value object)

DateRange (domain object)
    ↓ used in
IStockDataManager.fetchStockData()
    ↓ returns
List<StockData>

ComparisonResult (value object)
    ↓ contains
List<StockData> (for stock 1)
List<StockData> (for stock 2)
    ↓ analyzed by
PerformanceMetrics
```

---

## Future Type Enhancements

### Sprint 3+ Possibilities:

**User Management:**
```java
class User {
    private String id;
    private Portfolio portfolio;
    private List<Alert> priceAlerts;
}
```

**Portfolio Tracking:**
```java
class Portfolio {
    private Map<String, Position> positions;
    private double totalValue;
}

class Position {
    private String symbol;
    private int shares;
    private double averageCost;
}
```

**Advanced Charts:**
```java
class ChartData {
    private ChartType type;
    private List<Indicator> indicators; // RSI, MACD
}
```

---

## Conclusion

The Business Type Model successfully maps all key business concepts to concrete, well-designed Java types. The types are:

- ✅ **Immutable** - Safe and predictable
- ✅ **Validated** - Enforce business rules
- ✅ **Focused** - Single responsibility
- ✅ **Expressive** - Clear business intent

These types form the foundation of the domain model and ensure business logic is properly encapsulated.

---

**Document Status:** ✅ Complete  
**Implementation Status:** ✅ All types implemented in Sprint 1  
**Next Document:** Initial System Architecture
