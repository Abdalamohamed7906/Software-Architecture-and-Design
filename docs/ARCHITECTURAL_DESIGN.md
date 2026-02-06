# Architectural Design Document
## Share Price Comparison Application

---

## 1. Architectural Overview

### 1.1 Architecture Style: Layered Architecture (N-Tier)

The application follows a **4-Layer Architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────┐
│     Presentation Layer (UI)         │  ← User Interaction
├─────────────────────────────────────┤
│     Service Layer (Business Logic)  │  ← Orchestration & Rules
├─────────────────────────────────────┤
│     Repository Layer (Data Access)  │  ← Data Persistence
├─────────────────────────────────────┤
│     Domain Model Layer (Entities)   │  ← Core Business Objects
└─────────────────────────────────────┘
```

### 1.2 Architectural Principles Applied

#### Simple Architecture Principles:
1. **Separation of Concerns**: Each layer has a distinct responsibility
2. **Dependency Inversion**: High-level modules don't depend on low-level modules
3. **Single Responsibility**: Each component has one reason to change
4. **Interface Segregation**: Clients depend on specific interfaces, not implementations
5. **Loose Coupling**: Components interact through well-defined interfaces

---

## 2. Component Specification

### 2.1 Layer Descriptions

#### **Presentation Layer**
**Responsibility**: Handle user interface and user interaction

**Components**:
- `SharePriceView`: Main UI component for displaying graphs
- `ComparisonView`: UI for comparing multiple stocks
- `InputController`: Handles user input validation

**Dependencies**: Service Layer interfaces only

**Key Interfaces**:
```java
public interface View {
    void display();
    void showError(String message);
    void showLoading(boolean isLoading);
}
```

---

#### **Service Layer**
**Responsibility**: Business logic, orchestration, and coordination

**Components**:
- `StockPriceService`: Orchestrates data fetching and caching
- `ComparisonService`: Handles comparison logic between stocks
- `NetworkService`: Manages network connectivity detection
- `CacheStrategy`: Determines when to use cache vs. fetch new data

**Dependencies**: Repository Layer and Domain Model

**Key Interfaces**:
```java
public interface IStockPriceService {
    List<StockPrice> getStockPrices(String symbol, LocalDate start, LocalDate end);
    void refreshData(String symbol);
    boolean isDataAvailable(String symbol, LocalDate start, LocalDate end);
}
```

---

#### **Repository Layer**
**Responsibility**: Data access and persistence abstraction

**Components**:
- `StockPriceRepository`: Abstract interface for data storage
- `SQLiteRepository`: Concrete implementation using SQLite
- `JSONRepository`: Concrete implementation using JSON files
- `YahooFinanceAPIClient`: External API integration
- `DataMapper`: Converts between API responses and domain models

**Dependencies**: Domain Model only

**Key Interfaces**:
```java
public interface IStockRepository {
    void save(List<StockPrice> prices);
    List<StockPrice> findBySymbolAndDateRange(String symbol, LocalDate start, LocalDate end);
    boolean exists(String symbol, LocalDate date);
    void update(StockPrice price);
}

public interface IExternalDataSource {
    List<StockPrice> fetchStockPrices(String symbol, LocalDate start, LocalDate end);
}
```

---

#### **Domain Model Layer**
**Responsibility**: Core business entities and value objects

**Components**:
- `StockPrice`: Entity representing a single day's stock data
- `Symbol`: Value object for stock symbols
- `DateRange`: Value object for date range validation
- `PriceData`: Value object containing OHLCV data

**Dependencies**: None (independent layer)

**Key Classes**:
```java
public class StockPrice {
    private String symbol;
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal adjustedClose;
    private long volume;
}
```

---

## 3. Component Specification Diagram

### 3.1 High-Level Architecture

```
┌────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │ SharePrice   │  │ Comparison   │  │    Input     │        │
│  │    View      │  │    View      │  │ Controller   │        │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘        │
└─────────┼──────────────────┼──────────────────┼────────────────┘
          │                  │                  │
          └──────────────────┼──────────────────┘
                             │
┌────────────────────────────┼────────────────────────────────────┐
│                    SERVICE LAYER          │                     │
│  ┌─────────────────────┐  ┌──────────────────────┐            │
│  │  StockPriceService  │  │  ComparisonService   │            │
│  │   <<Interface>>     │  │    <<Interface>>     │            │
│  └─────────┬───────────┘  └──────────┬───────────┘            │
│            │                          │                         │
│  ┌─────────▼────────────────┐  ┌─────▼────────┐               │
│  │ StockPriceServiceImpl │  │ NetworkService│               │
│  └─────────┬────────────────┘  └──────────────┘               │
└────────────┼────────────────────────────────────────────────────┘
             │
┌────────────┼────────────────────────────────────────────────────┐
│     REPOSITORY LAYER          │                                 │
│  ┌──────────────────────┐  ┌─▼────────────────┐               │
│  │   IStockRepository   │  │IExternalDataSource│               │
│  │    <<Interface>>     │  │  <<Interface>>    │               │
│  └─────┬────────┬───────┘  └─────────┬─────────┘               │
│        │        │                     │                         │
│  ┌─────▼──┐ ┌──▼──────┐    ┌────────▼────────┐                │
│  │SQLite  │ │  JSON   │    │ YahooFinance    │                │
│  │Repo    │ │  Repo   │    │  APIClient      │                │
│  └────────┘ └─────────┘    └─────────────────┘                │
└────────────────────────────────────────────────────────────────┘
             │
┌────────────┼────────────────────────────────────────────────────┐
│       DOMAIN MODEL LAYER                                        │
│  ┌──────────┐  ┌─────────┐  ┌──────────┐  ┌──────────┐        │
│  │StockPrice│  │ Symbol  │  │DateRange │  │PriceData │        │
│  └──────────┘  └─────────┘  └──────────┘  └──────────┘        │
└────────────────────────────────────────────────────────────────┘
```

### 3.2 Component Interaction Diagram

```
User → View → Service → Repository → Data Source
                  ↓
              Domain Model
```

**Data Flow Example**:
1. User inputs stock symbol and date range
2. View validates input and calls Service
3. Service checks Repository for cached data
4. If not cached, Service calls External Data Source
5. Data Source fetches from Yahoo Finance API
6. Data is mapped to Domain Model
7. Repository persists Domain Model
8. Service returns data to View
9. View renders chart

---

## 4. Design Patterns Applied

### 4.1 Repository Pattern
**Purpose**: Abstraction over data access logic
**Benefits**: 
- Decouples business logic from data access
- Enables switching between SQLite and JSON
- Facilitates testing with mock repositories

### 4.2 Strategy Pattern
**Purpose**: Runtime selection of persistence mechanism
**Implementation**: `IStockRepository` with SQLite and JSON strategies

### 4.3 Factory Pattern
**Purpose**: Create appropriate repository instances
**Implementation**: `RepositoryFactory` creates SQLite or JSON repository

### 4.4 Adapter Pattern
**Purpose**: Convert external API responses to domain models
**Implementation**: `DataMapper` adapts Yahoo Finance data to `StockPrice`

### 4.5 Facade Pattern
**Purpose**: Simplified interface to complex subsystem
**Implementation**: Service layer provides simple API to UI

---

## 5. Simple Architecture Principles

### 5.1 Separation of Concerns
Each layer has a distinct, well-defined responsibility:
- **UI**: User interaction only
- **Service**: Business logic only
- **Repository**: Data access only
- **Domain**: Business entities only

### 5.2 Dependency Direction
Dependencies flow inward (toward domain model):
```
Presentation → Service → Repository → Domain
           ↓        ↓         ↓
           Domain   Domain   Domain
```

### 5.3 Abstraction Through Interfaces
All layer communication happens through interfaces:
- Views depend on `IStockPriceService`, not concrete implementations
- Services depend on `IStockRepository`, not concrete repositories
- Enables dependency injection and testing

### 5.4 Single Responsibility Principle (SRP)
Each component has one reason to change:
- `StockPriceService`: Business logic changes
- `SQLiteRepository`: Database schema changes
- `YahooFinanceAPIClient`: API changes
- `StockPrice`: Domain model changes

### 5.5 Open/Closed Principle (OCP)
Open for extension, closed for modification:
- New repositories can be added without modifying existing code
- New data sources can be plugged in via `IExternalDataSource`

---

## 6. Component Responsibilities

### 6.1 Presentation Layer Components

| Component | Responsibility | Dependencies |
|-----------|---------------|--------------|
| SharePriceView | Display single stock chart | IStockPriceService |
| ComparisonView | Display comparison of 2 stocks | IComparisonService |
| InputController | Validate user input | Domain Model |

### 6.2 Service Layer Components

| Component | Responsibility | Dependencies |
|-----------|---------------|--------------|
| IStockPriceService | Define service contract | Domain Model |
| StockPriceServiceImpl | Orchestrate data operations | IStockRepository, IExternalDataSource |
| IComparisonService | Define comparison contract | Domain Model |
| ComparisonServiceImpl | Compare stock performance | IStockPriceService |
| NetworkService | Detect connectivity | None |

### 6.3 Repository Layer Components

| Component | Responsibility | Dependencies |
|-----------|---------------|--------------|
| IStockRepository | Define persistence contract | Domain Model |
| SQLiteRepository | SQLite implementation | Domain Model, JDBC |
| JSONRepository | JSON file implementation | Domain Model, Jackson |
| IExternalDataSource | Define data source contract | Domain Model |
| YahooFinanceAPIClient | Fetch from Yahoo Finance | Domain Model, HTTP Client |
| DataMapper | Map API data to domain | Domain Model |

### 6.4 Domain Model Components

| Component | Responsibility | Dependencies |
|-----------|---------------|--------------|
| StockPrice | Stock price entity | None |
| Symbol | Stock symbol value object | None |
| DateRange | Date range value object | None |
| PriceData | OHLCV value object | None |

---

## 7. Interface Specifications

### 7.1 IStockPriceService Interface

```java
public interface IStockPriceService {
    /**
     * Retrieves stock prices for a symbol within a date range
     * Checks cache first, fetches from API if necessary
     */
    List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Forces refresh of data from external source
     */
    void refreshData(String symbol);
    
    /**
     * Checks if data is available locally
     */
    boolean isDataAvailable(String symbol, LocalDate startDate, LocalDate endDate);
}
```

### 7.2 IStockRepository Interface

```java
public interface IStockRepository {
    /**
     * Saves a collection of stock prices
     */
    void save(List<StockPrice> prices);
    
    /**
     * Finds stock prices by symbol and date range
     */
    List<StockPrice> findBySymbolAndDateRange(String symbol, LocalDate start, LocalDate end);
    
    /**
     * Checks if a specific date exists for a symbol
     */
    boolean exists(String symbol, LocalDate date);
    
    /**
     * Updates an existing stock price record
     */
    void update(StockPrice price);
    
    /**
     * Deletes data for a symbol
     */
    void deleteBySymbol(String symbol);
}
```

### 7.3 IExternalDataSource Interface

```java
public interface IExternalDataSource {
    /**
     * Fetches stock prices from external API
     */
    List<StockPrice> fetchStockPrices(String symbol, LocalDate start, LocalDate end) 
        throws DataSourceException;
    
    /**
     * Validates if a symbol exists
     */
    boolean validateSymbol(String symbol);
}
```

---

## 8. Data Flow Scenarios

### 8.1 Scenario 1: First-Time Data Fetch (Online)

```
1. User enters "AAPL", 2024-01-01 to 2024-12-31
2. View validates input
3. View calls StockPriceService.getStockPrices("AAPL", ...)
4. Service checks Repository.exists("AAPL", dates)
5. Repository returns false (no cached data)
6. Service calls YahooFinanceAPI.fetchStockPrices("AAPL", ...)
7. API returns raw data
8. DataMapper converts to List<StockPrice>
9. Service calls Repository.save(stockPrices)
10. Service returns data to View
11. View renders chart
```

### 8.2 Scenario 2: Cached Data Retrieval (Offline)

```
1. User enters "GOOGL", 2024-06-01 to 2024-08-31
2. View validates input
3. View calls StockPriceService.getStockPrices("GOOGL", ...)
4. Service checks Repository.exists("GOOGL", dates)
5. Repository returns true (data cached)
6. Service calls Repository.findBySymbolAndDateRange("GOOGL", ...)
7. Repository returns cached List<StockPrice>
8. Service returns data to View
9. View renders chart
```

### 8.3 Scenario 3: Comparison of Two Stocks

```
1. User enters "AAPL" and "MSFT", 2024-01-01 to 2024-12-31
2. View calls ComparisonService.compareStocks("AAPL", "MSFT", ...)
3. ComparisonService calls StockPriceService.getStockPrices("AAPL", ...)
4. ComparisonService calls StockPriceService.getStockPrices("MSFT", ...)
5. ComparisonService normalizes and aligns data
6. ComparisonService returns comparison result
7. View renders dual-line chart
```

---

## 9. Technology Stack Rationale

| Technology | Purpose | Rationale |
|------------|---------|-----------|
| Java 11+ | Core language | Robust, platform-independent, strong typing |
| Maven/Gradle | Build tool | Dependency management, standardized build |
| SQLite | Primary persistence | Lightweight, embedded, no server required |
| Jackson | JSON processing | Fast, widely-used, handles complex objects |
| JFreeChart | Charting | Mature, feature-rich, Java-native |
| Apache HttpClient | HTTP requests | Reliable, configurable, connection pooling |
| JUnit 5 | Testing | Modern, annotations, assertions |

---

## 10. Quality Attributes

### 10.1 Modifiability
- **New data sources**: Implement `IExternalDataSource`
- **New storage**: Implement `IStockRepository`
- **New charts**: Extend presentation layer

### 10.2 Testability
- All dependencies are interfaces
- Allows easy mocking and unit testing
- Service layer can be tested independently

### 10.3 Scalability
- Stateless service layer
- Repository pattern supports caching strategies
- Can add connection pooling for databases

### 10.4 Maintainability
- Clear separation of concerns
- Each component has single responsibility
- Well-documented interfaces

---

## 11. Rationale for Simple Architecture

### Why Layered Architecture?
1. **Proven Pattern**: Well-understood, widely-used in enterprise
2. **Clear Boundaries**: Each layer has explicit contracts
3. **Team Collaboration**: Different team members can work on different layers
4. **Future Expansion**: Easy to add features within existing structure

### Why Not Microservices?
- Overhead too high for coursework scope
- Single application simplifies deployment
- No need for distributed systems complexity

### Why Not MVC?
- MVC mixes business logic in controllers
- Layered architecture better separates concerns
- Easier to test business logic independently

---

## 12. Sprint 1 Implementation Focus

For Sprint 1, we implement **abstract representations** of:

1. **Domain Model**: Complete `StockPrice` class with all fields
2. **Interfaces**: Define all service and repository interfaces
3. **Mock Implementations**: Basic implementations that return dummy data
4. **Factory Pattern**: Repository factory for strategy selection

**Not Yet Implemented**:
- Actual database connections
- Real API integration
- Full UI rendering
- Complete error handling

These will be developed in Sprints 2 and 3.

---

**Document Version**: 1.0  
**Last Updated**: February 6, 2026  
**Authors**: Software Architecture Team  
**Next Review**: Sprint 2 Planning
