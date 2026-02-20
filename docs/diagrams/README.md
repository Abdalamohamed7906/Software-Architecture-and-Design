# Architecture Diagrams
**StockCompare Project - Sprint 1**

This folder contains the architectural diagrams for the StockCompare application.

---

## Diagram 1: High-Level Architecture

This diagram shows the overall 3-layer architecture with data flows between components.

```mermaid
graph TB
    subgraph Presentation["Presentation Layer"]
        UI[Main UI Controller]
        Chart[Chart Display Component]
        Input[Input Form Component]
    end
    
    subgraph Business["Business Logic Layer"]
        Manager[Stock Data Manager]
        Analyzer[Price Analyzer]
        Compare[Comparison Service]
    end
    
    subgraph Data["Data Access Layer"]
        Repo[Local Repository]
        API[API Service]
        Cache[Cache Manager]
    end
    
    UI --> Manager
    UI --> Chart
    UI --> Input
    
    Chart --> Manager
    Input --> Manager
    
    Manager --> Analyzer
    Manager --> Compare
    Manager --> Repo
    Manager --> API
    Manager --> Cache
    
    Compare --> Analyzer
    
    Repo -.-> DB[(SQLite/JSON Database)]
    API -.-> EXT[External API<br/>Yahoo Finance]
    Cache -.-> MEM[(In-Memory Cache)]
    
    style Presentation fill:#e1f5ff
    style Business fill:#fff4e1
    style Data fill:#f0e1ff
    style DB fill:#d4edda
    style EXT fill:#d4edda
    style MEM fill:#d4edda
```

**Key Points:**
- **Presentation Layer**: Handles all user interactions and display
- **Business Logic Layer**: Contains core application logic and rules
- **Data Access Layer**: Manages all data storage and retrieval

---

## Diagram 2: Detailed Component Specification

This diagram shows all 9 components with their interfaces and methods.

```mermaid
graph TB
    subgraph Presentation["🖥️ Presentation Layer"]
        UI["<b>MainUIController</b><br/>────────────<br/>+ initialize()<br/>+ handleUserInput()<br/>+ displayError()<br/>+ updateView()"]
        
        Chart["<b>ChartDisplayComponent</b><br/>📊<br/>────────────<br/>IChartDisplay<br/>────────────<br/>+ renderChart()<br/>+ renderComparisonChart()<br/>+ clearChart()<br/>+ updateChart()"]
        
        Input["<b>InputFormComponent</b><br/>📝<br/>────────────<br/>IInputHandler<br/>────────────<br/>+ getStockSymbol()<br/>+ getDateRange()<br/>+ validateInput()<br/>+ displayValidationError()"]
    end
    
    subgraph Business["⚙️ Business Logic Layer"]
        Manager["<b>StockDataManager</b><br/>📈<br/>────────────<br/>IStockDataManager<br/>────────────<br/>+ fetchStockData()<br/>+ getStoredData()<br/>+ isDataAvailable()<br/>+ validateSymbol()<br/>+ clearCache()"]
        
        Analyzer["<b>PriceAnalyzer</b><br/>🔍<br/>────────────<br/>AbstractPriceAnalyzer<br/>────────────<br/>+ calculatePriceChange()<br/>+ findHighLow()<br/>+ normalizeData()<br/>+ formatForDisplay()"]
        
        Compare["<b>ComparisonService</b><br/>⚖️<br/>────────────<br/>IComparisonService<br/>────────────<br/>+ compareStocks()<br/>+ alignDataRanges()<br/>+ calculateRelativePerformance()"]
    end
    
    subgraph Data["💾 Data Access Layer"]
        Repo["<b>LocalRepository</b><br/>🗄️<br/>────────────<br/>IRepository<br/>────────────<br/>+ save()<br/>+ retrieve()<br/>+ exists()<br/>+ delete()<br/>+ getAllSymbols()"]
        
        API["<b>APIService</b><br/>🌐<br/>────────────<br/>IAPIService<br/>────────────<br/>+ fetchHistoricalData()<br/>+ validateSymbol()<br/>+ isConnectionAvailable()<br/>+ getRateLimitRemaining()"]
        
        Cache["<b>CacheManager</b><br/>⚡<br/>────────────<br/>ICacheManager<br/>────────────<br/>+ put()<br/>+ get()<br/>+ invalidate()<br/>+ clear()<br/>+ isExpired()"]
    end
    
    subgraph Domain["📦 Domain Models"]
        Stock["<b>StockData</b><br/>────────────<br/>- symbol: String<br/>- date: LocalDate<br/>- open/high/low/close: double<br/>- volume: long"]
        
        Range["<b>DateRange</b><br/>────────────<br/>- startDate: LocalDate<br/>- endDate: LocalDate<br/>- MAX_DAYS: 730<br/>+ validate()"]
    end
    
    UI -->|uses| Manager
    UI -->|displays| Chart
    UI -->|gets input| Input
    
    Chart -.->|displays| Stock
    Input -.->|creates| Range
    
    Manager -->|uses| Repo
    Manager -->|uses| API
    Manager -->|uses| Cache
    Manager -->|analyzes with| Analyzer
    Manager -.->|works with| Stock
    Manager -.->|validates| Range
    
    Compare -->|uses| Manager
    Compare -->|uses| Analyzer
    Compare -.->|compares| Stock
    
    Repo -.->|stores/retrieves| Stock
    API -.->|fetches| Stock
    Cache -.->|caches| Stock
    
    Repo -.->|persists to| DB[(SQLite or<br/>JSON Files)]
    API -.->|calls| EXT[Yahoo Finance<br/>API]
    Cache -.->|stores in| MEM[(Memory)]
    
    style Presentation fill:#e1f5ff,stroke:#0066cc,stroke-width:3px
    style Business fill:#fff4e1,stroke:#cc8800,stroke-width:3px
    style Data fill:#f0e1ff,stroke:#8800cc,stroke-width:3px
    style Domain fill:#e8f5e9,stroke:#2e7d32,stroke-width:3px
    
    style UI fill:#bbdefb
    style Chart fill:#bbdefb
    style Input fill:#bbdefb
    
    style Manager fill:#ffe082
    style Analyzer fill:#ffe082
    style Compare fill:#ffe082
    
    style Repo fill:#e1bee7
    style API fill:#e1bee7
    style Cache fill:#e1bee7
    
    style Stock fill:#c8e6c9
    style Range fill:#c8e6c9
    
    style DB fill:#a5d6a7
    style EXT fill:#a5d6a7
    style MEM fill:#a5d6a7
```

**Key Points:**
- Each component shows its interface and main methods
- Solid arrows show direct dependencies
- Dashed arrows show data flow
- Color-coded by layer for clarity

---

## Diagram 3: PROVIDED and REQUIRED Interfaces

This diagram shows which interfaces each component **provides** (○) and **requires** (◐).

```mermaid
graph LR
    subgraph Presentation["PRESENTATION LAYER"]
        UI["MainUIController<br/>Component"]
        Chart["ChartDisplay<br/>Component"]
        Input["InputForm<br/>Component"]
    end
    
    subgraph Business["BUSINESS LOGIC LAYER"]
        Manager["StockDataManager<br/>Component"]
        Analyzer["PriceAnalyzer<br/>Component"]
        Compare["ComparisonService<br/>Component"]
    end
    
    subgraph Data["DATA ACCESS LAYER"]
        Repo["LocalRepository<br/>Component"]
        API["APIService<br/>Component"]
        Cache["CacheManager<br/>Component"]
    end
    
    %% Provided Interfaces (lollipops)
    Manager -->|PROVIDES<br/>IStockDataManager| P1((○))
    Chart -->|PROVIDES<br/>IChartDisplay| P2((○))
    Input -->|PROVIDES<br/>IInputHandler| P3((○))
    Compare -->|PROVIDES<br/>IComparisonService| P4((○))
    Repo -->|PROVIDES<br/>IRepository| P5((○))
    API -->|PROVIDES<br/>IAPIService| P6((○))
    Cache -->|PROVIDES<br/>ICacheManager| P7((○))
    Analyzer -->|PROVIDES<br/>IAnalyzer| P8((○))
    
    %% Required Interfaces (sockets)
    R1((◐)) -->|REQUIRES<br/>IStockDataManager| UI
    R2((◐)) -->|REQUIRES<br/>IChartDisplay| UI
    R3((◐)) -->|REQUIRES<br/>IInputHandler| UI
    R4((◐)) -->|REQUIRES<br/>IRepository| Manager
    R5((◐)) -->|REQUIRES<br/>IAPIService| Manager
    R6((◐)) -->|REQUIRES<br/>ICacheManager| Manager
    R7((◐)) -->|REQUIRES<br/>IAnalyzer| Manager
    R8((◐)) -->|REQUIRES<br/>IStockDataManager| Compare
    R9((◐)) -->|REQUIRES<br/>IAnalyzer| Compare
    
    %% Connections
    P1 -.-> R1
    P2 -.-> R2
    P3 -.-> R3
    P5 -.-> R4
    P6 -.-> R5
    P7 -.-> R6
    P8 -.-> R7
    P1 -.-> R8
    P8 -.-> R9
    
    style Presentation fill:#e1f5ff
    style Business fill:#fff4e1
    style Data fill:#f0e1ff
    style UI fill:#bbdefb
    style Chart fill:#bbdefb
    style Input fill:#bbdefb
    style Manager fill:#ffe082
    style Analyzer fill:#ffe082
    style Compare fill:#ffe082
    style Repo fill:#e1bee7
    style API fill:#e1bee7
    style Cache fill:#e1bee7
```

**Key Points:**
- **○ (Lollipop)**: Interface PROVIDED by the component
- **◐ (Socket)**: Interface REQUIRED by the component
- Dotted lines show interface connections
- This demonstrates **loose coupling** through clear interface contracts

---

## Component Summary Table

| Component | Layer | Provides | Requires |
|-----------|-------|----------|----------|
| **MainUIController** | Presentation | — | IStockDataManager, IChartDisplay, IInputHandler |
| **ChartDisplay** | Presentation | IChartDisplay | — |
| **InputForm** | Presentation | IInputHandler | — |
| **StockDataManager** | Business Logic | IStockDataManager | IRepository, IAPIService, ICacheManager |
| **PriceAnalyzer** | Business Logic | IPriceAnalyzer | — |
| **ComparisonService** | Business Logic | IComparisonService | IStockDataManager, IPriceAnalyzer |
| **LocalRepository** | Data Access | IRepository | — |
| **APIService** | Data Access | IAPIService | — |
| **CacheManager** | Data Access | ICacheManager | — |

---

## Architecture Benefits

✅ **Loose Coupling**: Components depend on interfaces, not concrete implementations  
✅ **Testability**: Easy to create mock implementations for testing  
✅ **Substitutability**: Can swap implementations without changing dependents  
✅ **Scalability**: Easy to add new components or data sources  
✅ **Maintainability**: Clear separation of concerns across layers  

---

## Files in This Directory

- `high-level-architecture.mermaid` - Source file for Diagram 1
- `component-specification.mermaid` - Source file for Diagram 2
- `component-provided-required.mermaid` - Source file for Diagram 3
- `PROVIDED-REQUIRED-SPECIFICATION.md` - Detailed component specifications
- `TEXT_DIAGRAMS.md` - ASCII text versions of diagrams
- `DIAGRAM_GUIDE.md` - Guide for creating and rendering diagrams

---

**Sprint 1 - February 2026**  
**Team**: Anwar, Abdalla, Meshari, Omran
