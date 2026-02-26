# Component-to-Code Mapping

## Complete Component Mapping for Sprint 1

### Presentation Layer

#### 1. MainUIController
- **File:** `MainUIController.java`
- **Provides:** None (top-level coordinator)
- **Requires:** IInputHandler, IChartDisplay, IStockDataManager
- **Code Reference:**
  ```java
  // Lines 12-14 show dependencies
  private final IStockDataManager stockDataManager;
  private final IChartDisplay chartDisplay;
  private final IInputHandler inputHandler;
  ```

#### 2. InputHandler
- **File:** `InputHandler.java`
- **Provides:** IInputHandler
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 8: Implements provided interface
  public class InputHandler implements IInputHandler
  ```

#### 3. ChartDisplay
- **File:** `ChartDisplay.java`
- **Provides:** IChartDisplay
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 8: Implements provided interface
  public class ChartDisplay implements IChartDisplay
  ```

---

### Service Layer

#### 4. StockDataManager
- **File:** `StockDataManager.java`
- **Provides:** IStockDataManager
- **Requires:** IRepository, IAPIService, ICacheManager, IPriceAnalyzer
- **Code Reference:**
  ```java
  // Line 15: Implements provided interface
  public class StockDataManager implements IStockDataManager
  
  // Lines 17-20: Shows required dependencies
  private final IRepository repository;
  private final IAPIService apiService;
  private final ICacheManager cacheManager;
  private final IPriceAnalyzer priceAnalyzer;
  ```

#### 5. PriceAnalyzer
- **File:** `PriceAnalyzer.java`
- **Provides:** IPriceAnalyzer
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 9: Implements provided interface
  public class PriceAnalyzer implements IPriceAnalyzer
  ```

#### 6. ComparisonService
- **File:** `ComparisonService.java`
- **Provides:** IComparisonService
- **Requires:** IStockDataManager, IPriceAnalyzer
- **Code Reference:**
  ```java
  // Line 10: Implements provided interface
  public class ComparisonService implements IComparisonService
  
  // Lines 12-13: Shows required dependencies
  private final IStockDataManager stockDataManager;
  private final IPriceAnalyzer priceAnalyzer;
  ```

---

### Data Layer

#### 7. APIService
- **File:** `APIService.java`
- **Provides:** IAPIService
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 13: Implements provided interface
  public class APIService implements IAPIService
  ```

#### 8. CacheManager
- **File:** `CacheManager.java`
- **Provides:** ICacheManager
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 13: Implements provided interface
  public class CacheManager implements ICacheManager
  ```

#### 9. Repository
- **File:** `Repository.java`
- **Provides:** IRepository
- **Requires:** None
- **Code Reference:**
  ```java
  // Line 13: Implements provided interface
  public class Repository implements IRepository
  ```

---

## Interface Definitions

### Service Interfaces
1. **IStockDataManager** - Lines 12-22 in `IStockDataManager.java`
2. **IPriceAnalyzer** - Lines 9-19 in `IPriceAnalyzer.java`
3. **IComparisonService** - Lines 11-20 in `IComparisonService.java`

### Data Interfaces
4. **IAPIService** - Lines 11-20 in `IAPIService.java`
5. **ICacheManager** - Lines 11-19 in `ICacheManager.java`
6. **IRepository** - Lines 13-25 in `IRepository.java`

### Presentation Interfaces
7. **IInputHandler** - Lines 8-18 in `IInputHandler.java`
8. **IChartDisplay** - Lines 9-20 in `IChartDisplay.java`

---

## Dependency Injection Examples

### Example 1: MainUIController Constructor
```java
// File: MainUIController.java, Lines 16-21
public MainUIController(IStockDataManager stockDataManager,
                       IChartDisplay chartDisplay,
                       IInputHandler inputHandler) {
    this.stockDataManager = stockDataManager;
    this.chartDisplay = chartDisplay;
    this.inputHandler = inputHandler;
}
```

### Example 2: StockDataManager Constructor
```java
// File: StockDataManager.java, Lines 22-31
public StockDataManager(IRepository repository,
                       IAPIService apiService,
                       ICacheManager cacheManager,
                       IPriceAnalyzer priceAnalyzer) {
    this.repository = repository;
    this.apiService = apiService;
    this.cacheManager = cacheManager;
    this.priceAnalyzer = priceAnalyzer;
}
```

### Example 3: ComparisonService Constructor
```java
// File: ComparisonService.java, Lines 15-19
public ComparisonService(IStockDataManager stockDataManager,
                        IPriceAnalyzer priceAnalyzer) {
    this.stockDataManager = stockDataManager;
    this.priceAnalyzer = priceAnalyzer;
}
```

---

## Component Assembly in Main.java

See `Main.java` lines 28-60 for complete component instantiation:

```java
// Data Layer
IRepository repository = new Repository();
ICacheManager cacheManager = new CacheManager();
IAPIService apiService = new APIService();

// Service Layer
IPriceAnalyzer priceAnalyzer = new PriceAnalyzer();
IStockDataManager stockDataManager = new StockDataManager(
    repository, apiService, cacheManager, priceAnalyzer
);
IComparisonService comparisonService = new ComparisonService(
    stockDataManager, priceAnalyzer
);

// Presentation Layer
IInputHandler inputHandler = new InputHandler();
IChartDisplay chartDisplay = new ChartDisplay();
MainUIController controller = new MainUIController(
    stockDataManager, chartDisplay, inputHandler
);
```

---

## UML Diagram Symbols Used

| Symbol | Meaning | Example |
|--------|---------|---------|
| Rectangle with fold | Component | `[StockDataManager]` |
| Lollipop (○) | Provided Interface | `IStockDataManager` |
| Socket (◐) | Required Interface | Component needs this |
| Dashed Arrow (-->) | Dependency | A requires B |

---

## Verification Checklist

✓ All 9 components have implementation files  
✓ All 8 interfaces defined  
✓ All dependencies injected via constructors  
✓ Code compiles without errors  
✓ Main.java demonstrates all components  
✓ No circular dependencies  
✓ Clear layer separation maintained  

---

## Questions for Teacher?

**Q: Where is MainUIController?**  
A: `MainUIController.java` in `presentation` package

**Q: What does it depend on?**  
A: Three interfaces - see lines 12-14

**Q: Show me StockDataManager dependencies**  
A: `StockDataManager.java` lines 17-20 show 4 dependencies

**Q: Where's the interface-implementation connection?**  
A: Each component file shows `implements InterfaceName` (search for "implements")

**Q: How are components created?**  
A: `Main.java` lines 28-60 show instantiation with dependency injection
