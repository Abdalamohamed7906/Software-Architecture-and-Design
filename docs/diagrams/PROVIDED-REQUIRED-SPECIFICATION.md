# Component Specification - Provided and Required Interfaces

## Component Diagram Legend

**○ (Lollipop)** = PROVIDED Interface (component offers this service)  
**◐ (Socket)** = REQUIRED Interface (component needs this service)

---

## PRESENTATION LAYER Components

### Component 1: MainUIController

**PROVIDED Interfaces:** None (top-level coordinator)

**REQUIRED Interfaces:**
- ◐ **IStockDataManager** - to fetch and manage stock data
- ◐ **IChartDisplay** - to display charts
- ◐ **IInputHandler** - to get user input

**Responsibilities:**
- Orchestrates user interactions
- Coordinates between UI components
- Handles application flow

---

### Component 2: ChartDisplayComponent

**PROVIDED Interfaces:**
- ○ **IChartDisplay**
  - renderChart(data: List<StockData>)
  - renderComparisonChart(data1: List<StockData>, data2: List<StockData>, symbol1: String, symbol2: String)
  - clearChart()
  - updateChart(data: List<StockData>)
  - setChartTitle(title: String)

**REQUIRED Interfaces:** None

**Responsibilities:**
- Renders stock price charts
- Displays comparison charts
- Manages chart visualization

---

### Component 3: InputFormComponent

**PROVIDED Interfaces:**
- ○ **IInputHandler**
  - getStockSymbol(): String
  - getDateRange(): DateRange
  - validateInput(): boolean
  - displayValidationError(field: String, message: String)
  - clearInputs()

**REQUIRED Interfaces:** None

**Responsibilities:**
- Captures user input
- Validates input data
- Displays validation errors

---

## BUSINESS LOGIC LAYER Components

### Component 4: StockDataManager

**PROVIDED Interfaces:**
- ○ **IStockDataManager**
  - fetchStockData(symbol: String, dateRange: DateRange): List<StockData>
  - getStoredData(symbol: String, dateRange: DateRange): List<StockData>
  - isDataAvailable(symbol: String, dateRange: DateRange): boolean
  - validateSymbol(symbol: String): boolean
  - clearCache(symbol: String)

**REQUIRED Interfaces:**
- ◐ **IRepository** - for local data persistence
- ◐ **IAPIService** - for external data fetching
- ◐ **ICacheManager** - for performance caching
- ◐ **IAnalyzer** - for data analysis

**Responsibilities:**
- Core business logic for stock data operations
- Orchestrates data fetching from multiple sources
- Implements caching strategy
- Validates business rules (2-year maximum)

---

### Component 5: PriceAnalyzer

**PROVIDED Interfaces:**
- ○ **IAnalyzer**
  - calculatePriceChange(data: List<StockData>): double
  - findHighLow(data: List<StockData>): double[]
  - calculateAveragePrice(data: List<StockData>): double
  - normalizeData(data: List<StockData>): List<StockData>
  - formatForDisplay(data: List<StockData>): DisplayData

**REQUIRED Interfaces:** None

**Responsibilities:**
- Analyzes stock price data
- Performs calculations on price data
- Normalizes data for comparison

---

### Component 6: ComparisonService

**PROVIDED Interfaces:**
- ○ **IComparisonService**
  - compareStocks(symbol1: String, symbol2: String, dateRange: DateRange): ComparisonResult
  - alignDataRanges(data1: List<StockData>, data2: List<StockData>): ComparisonResult
  - calculateRelativePerformance(data1: List<StockData>, data2: List<StockData>): PerformanceMetrics

**REQUIRED Interfaces:**
- ◐ **IStockDataManager** - to fetch stock data for comparison
- ◐ **IAnalyzer** - to analyze comparison results

**Responsibilities:**
- Compares multiple stocks
- Aligns data for comparison
- Calculates relative performance

---

## DATA ACCESS LAYER Components

### Component 7: LocalRepository

**PROVIDED Interfaces:**
- ○ **IRepository**
  - save(stockData: StockData): boolean
  - saveAll(stockDataList: List<StockData>): int
  - retrieve(symbol: String, dateRange: DateRange): List<StockData>
  - exists(symbol: String, date: LocalDate): boolean
  - delete(symbol: String): boolean
  - getAllSymbols(): List<String>
  - clearAll()

**REQUIRED Interfaces:** None (interacts with database/file system)

**Responsibilities:**
- Manages persistent local storage
- Implements SQLite or JSON storage
- Provides offline data access

---

### Component 8: APIService

**PROVIDED Interfaces:**
- ○ **IAPIService**
  - fetchHistoricalData(symbol: String, dateRange: DateRange): List<StockData>
  - validateSymbol(symbol: String): boolean
  - isConnectionAvailable(): boolean
  - getRateLimitRemaining(): int

**REQUIRED Interfaces:** None (interacts with external Yahoo Finance API)

**Responsibilities:**
- Fetches data from external API
- Handles API communication
- Manages API rate limiting
- Validates stock symbols

---

### Component 9: CacheManager

**PROVIDED Interfaces:**
- ○ **ICacheManager**
  - put(key: String, data: List<StockData>, ttl: Duration)
  - get(key: String): List<StockData>
  - invalidate(key: String)
  - clear()
  - isExpired(key: String): boolean
  - generateKey(symbol: String, startDate: String, endDate: String): String

**REQUIRED Interfaces:** None (uses in-memory storage)

**Responsibilities:**
- Manages in-memory cache
- Implements LRU cache eviction
- Optimizes performance by reducing redundant fetches

---

## Interface Connection Matrix

| Component | Provides | Required By | Requires | Provided By |
|-----------|----------|-------------|----------|-------------|
| MainUIController | - | - | IStockDataManager<br/>IChartDisplay<br/>IInputHandler | StockDataManager<br/>ChartDisplay<br/>InputForm |
| ChartDisplay | IChartDisplay | MainUIController | - | - |
| InputForm | IInputHandler | MainUIController | - | - |
| StockDataManager | IStockDataManager | MainUIController<br/>ComparisonService | IRepository<br/>IAPIService<br/>ICacheManager<br/>IAnalyzer | LocalRepository<br/>APIService<br/>CacheManager<br/>PriceAnalyzer |
| PriceAnalyzer | IAnalyzer | StockDataManager<br/>ComparisonService | - | - |
| ComparisonService | IComparisonService | - | IStockDataManager<br/>IAnalyzer | StockDataManager<br/>PriceAnalyzer |
| LocalRepository | IRepository | StockDataManager | - | - |
| APIService | IAPIService | StockDataManager | - | - |
| CacheManager | ICacheManager | StockDataManager | - | - |

---

## Data Flow Example

**User Fetches Stock Data:**

1. User enters symbol and dates → **InputForm** (PROVIDES IInputHandler)
2. **MainUIController** (REQUIRES IInputHandler) gets the input
3. **MainUIController** (REQUIRES IStockDataManager) calls StockDataManager
4. **StockDataManager** (REQUIRES ICacheManager) checks cache first
5. **CacheManager** (PROVIDES ICacheManager) returns null (not cached)
6. **StockDataManager** (REQUIRES IRepository) checks local storage
7. **LocalRepository** (PROVIDES IRepository) returns null (not stored)
8. **StockDataManager** (REQUIRES IAPIService) fetches from external API
9. **APIService** (PROVIDES IAPIService) fetches from Yahoo Finance
10. Data flows back through StockDataManager → stored in Repository and Cache
11. **StockDataManager** (REQUIRES IAnalyzer) analyzes the data
12. **PriceAnalyzer** (PROVIDES IAnalyzer) processes the data
13. **MainUIController** (REQUIRES IChartDisplay) displays result
14. **ChartDisplay** (PROVIDES IChartDisplay) renders the chart

---

## Key Points for Code Review

✅ **Clear Separation**: Each component has well-defined provided and required interfaces  
✅ **Loose Coupling**: Components only depend on interfaces, not implementations  
✅ **Single Responsibility**: Each component provides one cohesive set of services  
✅ **Substitutability**: Any component can be replaced as long as it implements the interface  
✅ **Testability**: Components can be tested with mock implementations of required interfaces  

---

## Architecture Benefits

1. **Modularity**: Components are independent and replaceable
2. **Scalability**: New components can be added without modifying existing ones
3. **Maintainability**: Clear contracts make changes predictable
4. **Testability**: Mock implementations can be injected for testing
5. **Reusability**: Components can be used in different contexts

---

*This specification follows component-based architecture principles with clear interface contracts.*
