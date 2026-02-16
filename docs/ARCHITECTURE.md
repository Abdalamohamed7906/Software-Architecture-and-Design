# Architectural Design Document - Stock-Compare
**Sprint 1 Deliverable**

## Team Members
- Anwar
- Abdala
- Meshari
- Omran

---

## 1. Architectural Overview

### 1.1 Simple Architecture Principles
This application follows **Simple Architecture** principles which emphasize:
- Clear separation of concerns
- Component-based design
- Well-defined interfaces between components
- Minimizing dependencies
- Single responsibility for each component

### 1.2 Architectural Goals
- **Modularity**: Each component has a specific, well-defined purpose
- **Maintainability**: Easy to understand, modify, and extend
- **Testability**: Components can be tested independently
- **Scalability**: Architecture supports future enhancements
- **Reusability**: Components can be used in different contexts

---

## 2. Component Specification

### 2.1 High-Level Architecture Diagram

**See detailed diagrams in:** `docs/diagrams/`
- `high-level-architecture.mermaid` - Interactive version
- `component-specification.mermaid` - Detailed component diagram
- `TEXT_DIAGRAMS.md` - Easy-to-draw versions
- `DIAGRAM_GUIDE.md` - Complete explanation

```
┌─────────────────────────────────────────────────────────┐
│                   Presentation Layer                    │
│  ┌────────────┐  ┌──────────────┐  ┌────────────────┐  │
│  │ Main UI    │  │ Chart Display│  │ Input Form     │  │
│  │ Controller │  │ Component    │  │ Component      │  │
│  └────────────┘  └──────────────┘  └────────────────┘  │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                    Business Logic Layer                 │
│  ┌────────────┐  ┌──────────────┐  ┌────────────────┐  │
│  │ Stock Data │  │ Price        │  │ Comparison     │  │
│  │ Manager    │  │ Analyzer     │  │ Service        │  │
│  └────────────┘  └──────────────┘  └────────────────┘  │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                    Data Access Layer                    │
│  ┌────────────┐  ┌──────────────┐  ┌────────────────┐  │
│  │ Local      │  │ API          │  │ Cache          │  │
│  │ Repository │  │ Service      │  │ Manager        │  │
│  └────────────┘  └──────────────┘  └────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

## 3. Component Descriptions

### 3.1 Presentation Layer Components

#### Component: MainUIController
**Responsibility**: Orchestrates user interactions and coordinates between UI components

**Interfaces**:
- `initialize()`: Set up the user interface
- `handleUserInput(input: UserInput)`: Process user actions
- `displayError(message: String)`: Show error messages
- `updateView(data: StockData)`: Refresh display with new data

**Dependencies**:
- ChartDisplayComponent
- InputFormComponent
- StockDataManager (Business Layer)

---

#### Component: ChartDisplayComponent
**Responsibility**: Renders stock price charts

**Interfaces**:
- `renderChart(data: StockData)`: Display single stock chart
- `renderComparisonChart(data1: StockData, data2: StockData)`: Display comparison
- `updateDateRange(start: Date, end: Date)`: Adjust time range
- `clearChart()`: Reset the display

**Dependencies**:
- Charting library (external)

---

#### Component: InputFormComponent
**Responsibility**: Captures user input for stock queries

**Interfaces**:
- `getStockSymbol(): String`: Retrieve entered symbol
- `getDateRange(): DateRange`: Get start and end dates
- `validateInput(): Boolean`: Check input validity
- `displayValidationError(field: String, message: String)`: Show errors

**Dependencies**: None (pure UI component)

---

### 3.2 Business Logic Layer Components

#### Component: StockDataManager
**Responsibility**: Core business logic for managing stock data operations

**Interfaces**:
- `fetchStockData(symbol: String, start: Date, end: Date): StockData`
- `getStoredData(symbol: String, start: Date, end: Date): StockData`
- `compareStocks(symbol1: String, symbol2: String, dateRange: DateRange): ComparisonResult`
- `isDataAvailable(symbol: String, dateRange: DateRange): Boolean`

**Dependencies**:
- LocalRepository (Data Layer)
- APIService (Data Layer)
- CacheManager (Data Layer)

**Business Rules**:
- Date range cannot exceed 2 years
- Always check local storage before fetching from API
- Validate stock symbols before processing

---

#### Component: PriceAnalyzer
**Responsibility**: Analyze and process stock price data

**Interfaces**:
- `calculatePriceChange(data: StockData): Double`
- `findHighLow(data: StockData): PriceRange`
- `normalizeData(data: StockData): NormalizedData`
- `formatForDisplay(data: StockData): DisplayData`

**Dependencies**: None (pure business logic)

---

#### Component: ComparisonService
**Responsibility**: Handle comparison logic between multiple stocks

**Interfaces**:
- `alignDataRanges(data1: StockData, data2: StockData): AlignedData`
- `calculateRelativePerformance(data1: StockData, data2: StockData): PerformanceMetrics`
- `prepareComparisonChart(data1: StockData, data2: StockData): ChartData`

**Dependencies**:
- PriceAnalyzer

---

### 3.3 Data Access Layer Components

#### Component: LocalRepository
**Responsibility**: Manage persistent storage of stock data

**Interfaces**:
- `save(stockData: StockData): Boolean`
- `retrieve(symbol: String, start: Date, end: Date): StockData`
- `exists(symbol: String, date: Date): Boolean`
- `delete(symbol: String): Boolean`
- `getAll(): List<StockData>`

**Dependencies**:
- Database connection (SQLite) or File system (JSON)

**Storage Format**:
```json
{
  "symbol": "AAPL",
  "date": "2024-01-15",
  "open": 185.50,
  "high": 188.20,
  "low": 184.90,
  "close": 187.30,
  "volume": 52000000
}
```

---

#### Component: APIService
**Responsibility**: Fetch stock data from external API

**Interfaces**:
- `fetchHistoricalData(symbol: String, start: Date, end: Date): StockData`
- `validateSymbol(symbol: String): Boolean`
- `isConnectionAvailable(): Boolean`
- `handleAPIError(error: APIError): ErrorResponse`

**Dependencies**:
- External API (Yahoo Finance or similar)

**Error Handling**:
- Network timeouts
- Invalid symbols
- Rate limiting
- API unavailability

---

#### Component: CacheManager
**Responsibility**: Manage in-memory cache for frequently accessed data

**Interfaces**:
- `put(key: String, data: StockData, ttl: Duration): void`
- `get(key: String): StockData`
- `invalidate(key: String): void`
- `clear(): void`
- `isExpired(key: String): Boolean`

**Dependencies**: None

**Caching Strategy**:
- Cache recently fetched data for 1 hour
- Limit cache size to 100 entries
- Use LRU (Least Recently Used) eviction policy

---

## 4. Interface Allocation

### Provided and Required Interfaces

In component-based architecture, we specify:
- **PROVIDED Interfaces** (○): Services the component offers to others
- **REQUIRED Interfaces** (◐): Services the component needs from others

### Interface Summary Table

| Component | Provided (○) | Required (◐) |
|-----------|--------------|--------------|
| **MainUIController** | - | IStockDataManager<br/>IChartDisplay<br/>IInputHandler |
| **ChartDisplay** | IChartDisplay | - |
| **InputForm** | IInputHandler | - |
| **StockDataManager** | IStockDataManager | IRepository<br/>IAPIService<br/>ICacheManager |
| **PriceAnalyzer** | IPriceAnalyzer | - |
| **ComparisonService** | IComparisonService | IStockDataManager<br/>IPriceAnalyzer |
| **LocalRepository** | IRepository | - |
| **APIService** | IAPIService | - |
| **CacheManager** | ICacheManager | - |

### Why This Approach?

**Loose Coupling**: Components depend only on interfaces, not implementations. We can swap any component as long as it provides/requires the correct interfaces.

**Clear Contracts**: Each interface defines an explicit contract between components.

**Testability**: We can create mock implementations for required interfaces to test components in isolation.

**Flexibility**: New implementations can be added without changing existing components.

### Example: StockDataManager Dependencies

**StockDataManager** PROVIDES: IStockDataManager interface
- Used by: MainUIController, ComparisonService

**StockDataManager** REQUIRES:
- IRepository - for local storage (provided by LocalRepository)
- IAPIService - for external data (provided by APIService)  
- ICacheManager - for caching (provided by CacheManager)

This means StockDataManager can work with ANY component that provides these interfaces, not just our specific implementations.

**See detailed specification:** `docs/diagrams/PROVIDED-REQUIRED-SPECIFICATION.md`

---

## 5. Component Interaction Diagram

```
User Action Flow:
1. User enters stock symbol and date range in InputFormComponent
2. InputFormComponent validates and passes to MainUIController
3. MainUIController calls StockDataManager.fetchStockData()
4. StockDataManager checks CacheManager first
5. If not in cache, checks LocalRepository
6. If not in local storage, calls APIService
7. APIService fetches from external API
8. Data flows back up: APIService → StockDataManager → LocalRepository (saves)
9. StockDataManager returns data to MainUIController
10. MainUIController passes data to ChartDisplayComponent
11. ChartDisplayComponent renders the chart
```

---

## 6. Design Decisions & Rationale

### Decision 1: Three-Layer Architecture
**Rationale**: Separates concerns clearly - UI, business logic, and data access are independent. Makes testing easier and allows components to be modified without affecting others.

### Decision 2: Repository Pattern for Data Access
**Rationale**: Abstracts data source details. Whether we use SQLite or JSON files, the business layer doesn't need to know. Easy to swap implementations.

### Decision 3: Cache Manager as Separate Component
**Rationale**: Improves performance for frequently accessed data. Reduces API calls and database queries. Can be configured independently.

### Decision 4: Separate Comparison Service
**Rationale**: Comparison logic is complex enough to warrant its own component. Makes it reusable and testable independently.

### Decision 5: API Service Abstraction
**Rationale**: If Yahoo Finance API changes or we want to support multiple APIs, only the APIService component needs modification.

---

## 7. Technology Stack

| Layer | Technology | Justification |
|-------|-----------|---------------|
| Presentation | JavaFX or Swing | Standard Java UI frameworks |
| Business Logic | Pure Java | No framework needed for business rules |
| Data Access | JDBC (SQLite) or Gson (JSON) | Lightweight, embedded database |
| Charting | JFreeChart or XChart | Popular Java charting libraries |
| Testing | JUnit 5 | Standard Java testing framework |

---

## 8. Quality Attributes

### Modifiability
- Components communicate through interfaces
- Easy to swap implementations (e.g., change database)
- New features can be added without modifying existing components

### Testability
- Each component can be unit tested independently
- Mock implementations can be created for interfaces
- Business logic is separate from UI and data access

### Performance
- Caching reduces redundant API calls
- Local storage enables offline functionality
- Efficient data structures for large datasets

---

## 9. Architecture Constraints

1. **Programming Language**: Must use Java
2. **Data Source**: Limited to free APIs (Yahoo Finance)
3. **Date Range**: Maximum 2 years of historical data
4. **Storage**: Must support offline operation
5. **Deployment**: Desktop application (initially)

---

## 10. Future Extensions (Sprint 2 & 3)

- Clean Architecture principles (Sprint 2)
- Domain-Independent Styles (Sprint 3)
- Compound Components (Sprint 3)
- SOA Principles (Sprint 3)
- Additional architectural patterns (MVC, Layered, etc.)

---

## Notes for Team

**Action Items**:
- [ ] Review and approve component design
- [ ] Assign components to team members
- [ ] Create detailed interface specifications
- [ ] Begin abstract Java implementation
- [ ] Update diagram based on team feedback

**Questions to Resolve**:
1. SQLite or JSON for storage?
2. Which charting library to use?
3. Which stock API to use?
4. Desktop UI framework preference?

---

*Document Version: 1.0*  
*Last Updated: [Date]*  
*Status: Draft*
