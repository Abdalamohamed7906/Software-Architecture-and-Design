# Stock-Compare Application - Sprint 1

## Project Overview
Stock-Compare is a stock price comparison application built using component-based architecture. This project demonstrates UML2 component design principles with clean separation of concerns across three architectural layers.

## Sprint 1 Deliverables
✓ **9 Components Implemented**  
✓ **8 Interface Definitions**  
✓ **UML2 Component Diagram**  
✓ **Domain Models with Business Rules**  
✓ **Complete Working Demo**

---

## Architecture

### Component Summary

**Presentation Layer (3 components):**
- MainUIController - Orchestrates user interactions
- InputHandler - Captures user input
- ChartDisplay - Displays stock charts

**Service Layer (3 components):**
- StockDataManager - Manages stock data fetching
- PriceAnalyzer - Analyzes stock prices
- ComparisonService - Compares multiple stocks

**Data Layer (3 components):**
- APIService - External API integration (mock)
- CacheManager - In-memory caching
- Repository - Local data persistence

**Domain Models:**
- StockData - Stock price entity
- DateRange - Date range with 2-year business rule
- StockDataException - Custom exception

---

## Component Connections

```
MainUIController
  ├─→ IInputHandler (from InputHandler)
  ├─→ IChartDisplay (from ChartDisplay)
  └─→ IStockDataManager (from StockDataManager)

StockDataManager
  ├─→ IRepository (from Repository)
  ├─→ IAPIService (from APIService)
  ├─→ ICacheManager (from CacheManager)
  └─→ IPriceAnalyzer (from PriceAnalyzer)

ComparisonService
  ├─→ IStockDataManager (from StockDataManager)
  └─→ IPriceAnalyzer (from PriceAnalyzer)
```

---

## How to Compile and Run

### Compile:
```bash
cd src
javac com/stockcompare/**/*.java
```

### Run:
```bash
java com.stockcompare.Main
```

### Expected Output:
- Component creation confirmation
- Stock data fetching demonstration
- Price analysis results
- Stock comparison example

---

## Key Design Principles

### 1. Interface-Based Design
All components communicate through interfaces:
- **Substitutability:** Components can be swapped
- **Testability:** Mock implementations for testing
- **Modularity:** Clear boundaries

### 2. Dependency Injection
All dependencies injected through constructors:
```java
public StockDataManager(IRepository repository,
                       IAPIService apiService,
                       ICacheManager cacheManager,
                       IPriceAnalyzer priceAnalyzer) {
    // Dependencies injected
}
```

### 3. Layered Architecture
Three distinct layers with one-way dependencies:
```
Presentation → Service → Data
```

### 4. Domain-Driven Design
Business rules enforced in domain models:
- **DateRange:** Maximum 2-year range validation
- **StockData:** Immutable data entity

---

## Business Rules

1. **Date Range Validation**
   - Maximum 2 years (730 days)
   - Start date must be before end date

2. **Symbol Validation**
   - 1-5 uppercase letters only
   - Pattern: `^[A-Z]{1,5}$`

3. **Data Fetching Strategy**
   - Check cache first
   - Check local repository
   - Fetch from API as last resort

---

## Project Structure

```
src/
└── com/
    └── stockcompare/
        ├── Main.java                    # Entry point
        ├── domain/                      # Domain models
        │   ├── StockData.java
        │   └── DateRange.java
        ├── service/                     # Service layer
        │   ├── IStockDataManager.java
        │   ├── StockDataManager.java
        │   ├── IPriceAnalyzer.java
        │   ├── PriceAnalyzer.java
        │   ├── IComparisonService.java
        │   ├── ComparisonService.java
        │   └── StockDataException.java
        ├── data/                        # Data layer
        │   ├── IAPIService.java
        │   ├── APIService.java
        │   ├── ICacheManager.java
        │   ├── CacheManager.java
        │   ├── IRepository.java
        │   └── Repository.java
        └── presentation/                # Presentation layer
            ├── MainUIController.java
            ├── IInputHandler.java
            ├── InputHandler.java
            ├── IChartDisplay.java
            └── ChartDisplay.java
```

---

## Technologies Used

- **Language:** Java 11+
- **Architecture:** Component-Based (UML2)
- **Design Patterns:** Dependency Injection, Repository Pattern, Template Method
- **Testing:** Manual demonstration (Unit tests in Sprint 2)

---

## Future Enhancements (Sprint 2+)

- Real API integration (Yahoo Finance)
- Persistent database (SQLite/MySQL)
- GUI implementation (JavaFX)
- Unit tests with JUnit
- Advanced charting capabilities
- Multi-stock comparison

---

## Team

- Software Architecture & Design Coursework
- Sprint 1: Component Architecture Foundation
- Status: ✓ Complete

---

## License

Educational project for university coursework.
