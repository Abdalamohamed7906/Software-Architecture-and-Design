# Architecture Diagrams - Stock-Compare

This document explains the two main architectural diagrams for Sprint 1.

---

## Diagram 1: High-Level Architecture

**File**: `high-level-architecture.mermaid`

### Purpose
Shows the three-layer architecture and how the layers communicate.

### Layers

1. **Presentation Layer (Blue)**
   - User interface components
   - Handles user input and display
   - Components: Main UI Controller, Chart Display, Input Form

2. **Business Logic Layer (Yellow)**
   - Core business rules and processing
   - No knowledge of UI or data storage details
   - Components: Stock Data Manager, Price Analyzer, Comparison Service

3. **Data Access Layer (Purple)**
   - Handles data persistence and external APIs
   - Abstracts data sources
   - Components: Local Repository, API Service, Cache Manager

### Key Points for Code Review
- Clear separation of concerns
- Each layer has specific responsibility
- Dependencies flow downward (Presentation → Business → Data)
- Business logic is independent and testable

---

## Diagram 2: Detailed Component Specification

**File**: `component-specification.mermaid`

### Purpose
Shows all 9 components with their interfaces and relationships.

### Components Detail

#### Presentation Layer Components

**1. MainUIController**
- **Responsibility**: Orchestrates UI and coordinates components
- **Interfaces Used**: None (top-level controller)
- **Dependencies**: StockDataManager, ChartDisplayComponent, InputFormComponent

**2. ChartDisplayComponent** 
- **Interface**: IChartDisplay
- **Responsibility**: Renders stock price charts
- **Methods**: renderChart(), renderComparisonChart(), clearChart(), updateChart()

**3. InputFormComponent**
- **Interface**: IInputHandler  
- **Responsibility**: Captures and validates user input
- **Methods**: getStockSymbol(), getDateRange(), validateInput()

#### Business Logic Layer Components

**4. StockDataManager**
- **Interface**: IStockDataManager
- **Responsibility**: Core business logic for stock data operations
- **Dependencies**: LocalRepository, APIService, CacheManager
- **Methods**: fetchStockData(), getStoredData(), isDataAvailable()
- **Pattern**: Implements multiple interfaces (DataProvider, DataValidator, CacheCoordinator)

**5. PriceAnalyzer**
- **Type**: AbstractPriceAnalyzer
- **Responsibility**: Analyzes and processes stock price data
- **Methods**: calculatePriceChange(), findHighLow(), normalizeData()

**6. ComparisonService**
- **Interface**: IComparisonService
- **Responsibility**: Compares multiple stocks
- **Dependencies**: StockDataManager, PriceAnalyzer
- **Methods**: compareStocks(), alignDataRanges(), calculateRelativePerformance()

#### Data Access Layer Components

**7. LocalRepository**
- **Interface**: IRepository
- **Responsibility**: Persistent local storage
- **Storage**: SQLite database or JSON files
- **Methods**: save(), retrieve(), exists(), delete(), getAllSymbols()

**8. APIService**
- **Interface**: IAPIService
- **Responsibility**: External API communication
- **External System**: Yahoo Finance (or similar)
- **Methods**: fetchHistoricalData(), validateSymbol(), isConnectionAvailable()

**9. CacheManager**
- **Interface**: ICacheManager
- **Responsibility**: In-memory caching for performance
- **Methods**: put(), get(), invalidate(), clear(), isExpired()

#### Domain Models

**StockData**
- Core data entity
- Fields: symbol, date, open, high, low, close, volume

**DateRange**  
- Encapsulates date range with validation
- Enforces 2-year maximum business rule

---

## How to Use These Diagrams

### Option 1: Render in VS Code or Online
1. Install "Mermaid Preview" extension in VS Code
2. Open `.mermaid` file and click preview
3. Take screenshot for your documentation

### Option 2: Use Mermaid Live Editor
1. Go to: https://mermaid.live
2. Copy the content from `.mermaid` file
3. Paste into the editor
4. Download as PNG or SVG

### Option 3: Include in Documentation
Many markdown renderers support Mermaid:
- GitHub (automatically renders)
- GitLab
- Notion
- Obsidian

### Option 4: Export as Image
Use the Mermaid CLI:
```bash
npm install -g @mermaid-js/mermaid-cli
mmdc -i high-level-architecture.mermaid -o high-level-architecture.png
mmdc -i component-specification.mermaid -o component-specification.png
```

---

## For Your Code Review Presentation

### Show Diagram 1 First (1-2 minutes)
"Our architecture follows a three-layer design:
- Presentation layer handles UI
- Business logic layer contains core rules
- Data access layer manages persistence

This separation allows us to test business logic independently and swap data sources easily."

### Show Diagram 2 Second (2-3 minutes)
"We've identified 9 components across these layers:

**Presentation**: MainUIController coordinates the Chart Display and Input Form components.

**Business Logic**: StockDataManager is our core component that orchestrates data fetching. It uses the PriceAnalyzer for calculations and works with ComparisonService for multi-stock analysis.

**Data Access**: We have three data sources - LocalRepository for persistent storage, APIService for external data, and CacheManager for performance optimization.

This design follows SOLID principles and allows each component to be developed and tested independently."

---

## Design Decisions Explained

### Why Three Layers?
- **Separation of Concerns**: Each layer has a single responsibility
- **Testability**: Business logic can be tested without UI
- **Flexibility**: Can swap UI framework or database without affecting business logic

### Why These Specific Components?
- **MainUIController**: Orchestration is separate from display logic
- **Separate Chart & Input**: Single Responsibility Principle
- **StockDataManager**: Facade pattern - single entry point for business operations
- **PriceAnalyzer**: Analysis logic is reusable across different features
- **ComparisonService**: Comparison is complex enough to warrant its own component
- **Three Data Sources**: Layered data strategy (cache → local → API)

### Interface Allocation
- **Multiple Interfaces**: StockDataManager implements multiple interfaces because it coordinates several concerns
- **Single Interfaces**: Display and Input components have single focused interfaces

---

## Questions You Might Get Asked

**Q: Why is StockDataManager in business logic and not data access?**
A: It contains business rules (like validation, cache strategy) and orchestrates data sources. It's not just about accessing data, but about the business logic of how to get and manage that data.

**Q: Why separate PriceAnalyzer from StockDataManager?**
A: Adheres to Single Responsibility Principle. Analyzer focuses only on calculations, while Manager orchestrates operations. Makes both more testable and reusable.

**Q: Why have both Repository and Cache?**
A: Different concerns - Repository is persistent storage, Cache is temporary performance optimization. Separating them allows independent evolution.

**Q: How does this support offline functionality?**
A: StockDataManager checks Cache first, then Repository, then API. If API fails, it can still serve from local storage.

**Q: Is this scalable?**
A: Yes - we can add new data sources, new analysis algorithms, or new UI components without modifying existing code. Open/Closed Principle.

---

## Matching to Requirements

| Requirement | Architecture Support |
|-------------|---------------------|
| FR1: Fetch stock data | APIService + StockDataManager |
| FR2: Persistent storage | LocalRepository |
| FR3: Display graphs | ChartDisplayComponent |
| FR4: Stock comparison | ComparisonService |
| FR5: Offline mode | Cache + Repository pattern |
| NFR4: Maintainability | Clear component boundaries |
| NFR5: Scalability | Loosely coupled design |

---

## Sprint 2 Preview

These components will evolve in Sprint 2:
- Add concrete implementations of repositories
- Implement actual API integration
- Add use case diagrams
- Add sequence diagrams for interactions
- Refine to Clean Architecture principles

---

*Created for Sprint 1 Code Review*  
*Date: 12.02.2026*  
*Team: Anwar, Abdala, Meshari, Omran*
