# Initial System Architecture - Sprint 2

## Stock-Compare Application

**Date:** February 27, 2026  
**Author:** Anwar  
**Sprint:** 2

---

## Overview

This document describes the initial system architecture for the Stock-Compare application, showing how components are organized, how they interact, and why this architecture was chosen.

---

## Architectural Style

**Primary Style:** **Layered Architecture** (3-Tier)

**Why Layered Architecture?**
- ✅ Clear separation of concerns
- ✅ Each layer has specific responsibility
- ✅ Dependencies flow in one direction (downward)
- ✅ Easy to understand and maintain
- ✅ Supports independent development
- ✅ Enables unit testing

---

## Layer Overview

```
┌─────────────────────────────────────────────────────┐
│           PRESENTATION LAYER                        │
│  (User Interface & Input/Output)                    │
│                                                      │
│  Components: MainUIController, InputHandler,        │
│              ChartDisplay                           │
│                                                      │
│  Responsibility: Handle user interaction            │
└──────────────────┬──────────────────────────────────┘
                   │ calls
                   ▼
┌─────────────────────────────────────────────────────┐
│             SERVICE LAYER                           │
│  (Business Logic & Orchestration)                   │
│                                                      │
│  Components: StockDataManager, PriceAnalyzer,       │
│              ComparisonService                      │
│                                                      │
│  Responsibility: Implement business rules           │
└──────────────────┬──────────────────────────────────┘
                   │ calls
                   ▼
┌─────────────────────────────────────────────────────┐
│              DATA LAYER                             │
│  (Data Access & External Services)                  │
│                                                      │
│  Components: Repository, CacheManager, APIService   │
│                                                      │
│  Responsibility: Manage data persistence & APIs     │
└─────────────────────────────────────────────────────┘
```

---

## Layer 1: Presentation Layer

### Purpose
Handle all user interactions and display logic.

### Components

#### **1. MainUIController**
- **Role:** Orchestrator of user workflows
- **Responsibilities:**
  - Coordinates between InputHandler, ChartDisplay, and StockDataManager
  - Handles main application flow
  - Translates user actions into service calls

- **Dependencies:**
  - IInputHandler (requires)
  - IChartDisplay (requires)
  - IStockDataManager (requires)

- **Key Methods:**
```java
public void handleFetchStockData() throws StockDataException
public void handleCompareStocks() throws StockDataException
```

#### **2. InputHandler**
- **Role:** User input capture and validation
- **Provides:** IInputHandler
- **Responsibilities:**
  - Get stock symbol from user
  - Get date range from user
  - Validate user input

- **Key Methods:**
```java
public String getStockSymbol()
public DateRange getDateRange()
public boolean validateInput(String input)
```

#### **3. ChartDisplay**
- **Role:** Data visualization
- **Provides:** IChartDisplay
- **Responsibilities:**
  - Render price charts (ASCII art in Sprint 1)
  - Display comparison charts
  - Format data for display

- **Key Methods:**
```java
public void renderChart(List<StockData> data, String symbol)
public void renderComparisonChart(List<StockData> stock1, 
                                  List<StockData> stock2,
                                  String symbol1, String symbol2)
```

### Layer Characteristics
- **No business logic** - only presentation
- **Thin layer** - delegates to service layer
- **Stateless** - doesn't store data
- **Interface-based** - all interactions through interfaces

---

## Layer 2: Service Layer

### Purpose
Implement business logic, orchestrate data operations, and enforce business rules.

### Components

#### **1. StockDataManager**
- **Role:** Core data management and orchestration
- **Provides:** IStockDataManager
- **Responsibilities:**
  - Fetch stock data (with caching strategy)
  - Coordinate between Repository, Cache, and API
  - Validate stock symbols
  - Enforce business rules

- **Dependencies:**
  - IRepository (requires)
  - IAPIService (requires)
  - ICacheManager (requires)
  - IPriceAnalyzer (requires)

- **Data Flow:**
```
Request → Check Cache → Check Repository → Fetch from API
                ↓             ↓                    ↓
              Return        Return            Save & Return
```

- **Key Methods:**
```java
public List<StockData> fetchStockData(String symbol, DateRange range)
public boolean validateSymbol(String symbol)
public boolean isDataAvailable(String symbol, DateRange range)
```

#### **2. PriceAnalyzer**
- **Role:** Perform calculations on price data
- **Provides:** IPriceAnalyzer
- **Responsibilities:**
  - Calculate price changes
  - Compute averages
  - Find high/low prices
  - Calculate returns

- **Key Methods:**
```java
public double calculatePriceChange(List<StockData> data)
public double calculateAveragePrice(List<StockData> data)
public double[] findHighLow(List<StockData> data)
public double calculateReturn(List<StockData> data)
```

#### **3. ComparisonService**
- **Role:** Compare two stocks
- **Provides:** IComparisonService
- **Responsibilities:**
  - Fetch data for both stocks
  - Align data by dates
  - Calculate comparison metrics
  - Produce comparison results

- **Dependencies:**
  - IStockDataManager (requires)
  - IPriceAnalyzer (requires)

- **Key Methods:**
```java
public ComparisonResult compareStocks(String symbol1, 
                                     String symbol2,
                                     DateRange range)
public PerformanceMetrics calculateRelativePerformance(
    List<StockData> stock1,
    List<StockData> stock2)
```

### Layer Characteristics
- **Contains ALL business logic**
- **Coordinates data operations**
- **Enforces business rules**
- **Stateless services**
- **Depends on data layer, NOT presentation**

---

## Layer 3: Data Layer

### Purpose
Manage data persistence, external API access, and caching.

### Components

#### **1. Repository**
- **Role:** Persistent storage management
- **Provides:** IRepository
- **Responsibilities:**
  - Save stock data
  - Retrieve stock data
  - Check data existence

- **Implementation:** In-memory (Sprint 1), Database (future)

- **Key Methods:**
```java
public void saveAll(List<StockData> dataList)
public List<StockData> retrieve(String symbol, DateRange range)
public boolean exists(String symbol, DateRange range)
```

#### **2. CacheManager**
- **Role:** In-memory caching
- **Provides:** ICacheManager
- **Responsibilities:**
  - Fast temporary storage
  - Reduce API calls
  - Store recent queries

- **Key Methods:**
```java
public void put(String key, List<StockData> data)
public List<StockData> get(String key)
public void clear()
```

#### **3. APIService**
- **Role:** External API integration
- **Provides:** IAPIService
- **Responsibilities:**
  - Fetch data from Yahoo Finance API
  - Handle API errors
  - Transform API response to domain objects

- **Implementation:** Mock data (Sprint 1), Real API (future)

- **Key Methods:**
```java
public List<StockData> fetchHistoricalData(String symbol, 
                                           DateRange range)
```

### Layer Characteristics
- **External dependencies** (APIs, databases)
- **Technical infrastructure**
- **No business logic**
- **Can be swapped** (e.g., different API providers)

---

## Architectural Principles

### 1. **Dependency Rule**
Dependencies flow DOWNWARD only:
```
Presentation → Service → Data
```

**Never:**
```
Data → Service ❌
Service → Presentation ❌
```

**Why?**
- Prevents circular dependencies
- Makes testing easier
- Allows independent changes

---

### 2. **Interface Segregation**
Every component provides an interface:

```java
// Component provides interface
public class StockDataManager implements IStockDataManager {
    // Implementation
}

// Consumers depend on interface
public class MainUIController {
    private final IStockDataManager stockDataManager;
    
    public MainUIController(IStockDataManager stockDataManager) {
        this.stockDataManager = stockDataManager;
    }
}
```

**Benefits:**
- Loose coupling
- Easy mocking for tests
- Implementation can change

---

### 3. **Dependency Injection**
All dependencies injected through constructors:

```java
// Service layer component
public StockDataManager(
    IRepository repository,
    IAPIService apiService,
    ICacheManager cacheManager,
    IPriceAnalyzer priceAnalyzer
) {
    this.repository = repository;
    this.apiService = apiService;
    this.cacheManager = cacheManager;
    this.priceAnalyzer = priceAnalyzer;
}
```

**Benefits:**
- Explicit dependencies
- Easy to test
- No hidden dependencies
- Clear component relationships

---

### 4. **Single Responsibility**
Each component has ONE clear purpose:
- ✅ MainUIController - User workflow orchestration
- ✅ StockDataManager - Data management
- ✅ PriceAnalyzer - Price calculations
- ✅ Repository - Data persistence

---

## Data Flow Examples

### Example 1: Fetch Stock Data

```
User
  ↓ clicks "Get Stock Data"
MainUIController
  ↓ calls
InputHandler.getStockSymbol() → returns "AAPL"
InputHandler.getDateRange() → returns DateRange
  ↓
MainUIController
  ↓ calls
StockDataManager.fetchStockData("AAPL", range)
  ↓
StockDataManager
  ↓ checks
CacheManager.get("AAPL:...") → null (cache miss)
  ↓
StockDataManager
  ↓ checks
Repository.retrieve("AAPL", range) → [] (not found)
  ↓
StockDataManager
  ↓ calls
APIService.fetchHistoricalData("AAPL", range)
  ↓ returns List<StockData>
StockDataManager
  ↓ saves to
Repository.saveAll(data)
CacheManager.put("AAPL:...", data)
  ↓ returns to
MainUIController
  ↓ displays via
ChartDisplay.renderChart(data, "AAPL")
  ↓
User sees chart
```

### Example 2: Compare Two Stocks

```
User
  ↓ requests comparison
MainUIController
  ↓ calls
ComparisonService.compareStocks("AAPL", "GOOGL", range)
  ↓
ComparisonService
  ↓ calls
StockDataManager.fetchStockData("AAPL", range)
StockDataManager.fetchStockData("GOOGL", range)
  ↓ returns both datasets
ComparisonService
  ↓ creates
ComparisonResult(appleData, googleData)
  ↓ calls
PriceAnalyzer.calculateReturn(appleData)
PriceAnalyzer.calculateReturn(googleData)
  ↓ creates
PerformanceMetrics(return1, return2)
  ↓ returns to
MainUIController
  ↓ displays via
ChartDisplay.renderComparisonChart(...)
  ↓
User sees comparison
```

---

## Component Interactions

### Interface Usage Map

| Component | Provides Interface | Requires Interfaces |
|-----------|-------------------|---------------------|
| **MainUIController** | - | IInputHandler, IChartDisplay, IStockDataManager |
| **InputHandler** | IInputHandler | - |
| **ChartDisplay** | IChartDisplay | - |
| **StockDataManager** | IStockDataManager | IRepository, IAPIService, ICacheManager, IPriceAnalyzer |
| **PriceAnalyzer** | IPriceAnalyzer | - |
| **ComparisonService** | IComparisonService | IStockDataManager, IPriceAnalyzer |
| **Repository** | IRepository | - |
| **CacheManager** | ICacheManager | - |
| **APIService** | IAPIService | - |

---

## Architecture Benefits

### 1. **Maintainability**
- Changes isolated to single layer
- Clear component boundaries
- Easy to locate code

### 2. **Testability**
- Each component testable independently
- Mock interfaces for unit tests
- No external dependencies in tests

### 3. **Scalability**
- Can add components to each layer
- Can replace implementations
- Can add new data sources

### 4. **Flexibility**
- Swap API providers (Yahoo → Alpha Vantage)
- Change storage (In-memory → Database)
- Update UI (Console → Web)

---

## Technology Stack

### Current (Sprint 1-2)
- **Language:** Java 11+
- **Data Storage:** In-memory (HashMap)
- **External API:** Mock data
- **UI:** Console (ASCII)

### Future (Sprint 3+)
- **Data Storage:** SQLite / PostgreSQL
- **External API:** Yahoo Finance API
- **UI:** JavaFX / Web interface
- **Testing:** JUnit 5 + Mockito

---

## Architecture Evolution

### Sprint 1
✅ Component-based architecture  
✅ Interface-driven design  
✅ Layered organization  

### Sprint 2 (Current)
🔄 Document architecture decisions  
🔄 Clean Architecture principles  
🔄 Formal models and diagrams  

### Sprint 3 (Future)
⏳ N-Tier architecture  
⏳ SOA principles  
⏳ Compound components  
⏳ Real API integration  

---

## Design Trade-offs

### Trade-off 1: Simplicity vs Flexibility
**Decision:** Interface-based design  
**Cost:** More files, more complexity  
**Benefit:** Easy testing, swappable implementations  
**Verdict:** ✅ Worth it for long-term maintenance  

### Trade-off 2: Performance vs Clean Code
**Decision:** Three-tier caching (Cache → Repository → API)  
**Cost:** Extra code, slight overhead  
**Benefit:** Faster data access, offline capability  
**Verdict:** ✅ Worth it for user experience  

### Trade-off 3: Mock Data vs Real API
**Decision:** Mock data in Sprint 1  
**Cost:** Not production-ready  
**Benefit:** Focus on architecture, no API dependencies  
**Verdict:** ✅ Correct for learning project  

---

## Conclusion

The Stock-Compare architecture successfully implements a clean, layered design that:

- ✅ Separates concerns into three distinct layers
- ✅ Uses interfaces to achieve loose coupling
- ✅ Implements dependency injection for testability
- ✅ Enforces one-way dependencies
- ✅ Supports future evolution and enhancement

This architecture provides a solid foundation for Sprint 3 enhancements while remaining simple enough for a learning project.

---

**Document Status:** ✅ Complete  
**Implementation Status:** ✅ Fully implemented in Sprint 1  
**Architecture Validation:** ✅ All principles demonstrated in code  

**Next Steps:**
- Abdala: Create Use Case Model
- Meshari: Refactor to Clean Architecture
- Omran: Create test plan based on this architecture
