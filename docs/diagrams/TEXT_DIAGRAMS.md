# Text-Based Diagrams for Easy Drawing

Use these as templates to draw in PowerPoint, Draw.io, or on paper.

---

## Diagram 1: High-Level Architecture (Simple)

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│  ┌────────────────┐  ┌────────────────┐  ┌──────────────┐  │
│  │  Main UI       │  │  Chart Display │  │  Input Form  │  │
│  │  Controller    │  │  Component     │  │  Component   │  │
│  └────────────────┘  └────────────────┘  └──────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                   BUSINESS LOGIC LAYER                      │
│  ┌────────────────┐  ┌────────────────┐  ┌──────────────┐  │
│  │  Stock Data    │  │  Price         │  │  Comparison  │  │
│  │  Manager       │  │  Analyzer      │  │  Service     │  │
│  └────────────────┘  └────────────────┘  └──────────────┘  │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                    DATA ACCESS LAYER                        │
│  ┌────────────────┐  ┌────────────────┐  ┌──────────────┐  │
│  │  Local         │  │  API           │  │  Cache       │  │
│  │  Repository    │  │  Service       │  │  Manager     │  │
│  └───────┬────────┘  └───────┬────────┘  └──────┬───────┘  │
│          │                   │                   │          │
│          ▼                   ▼                   ▼          │
│    ┌─────────┐         ┌─────────┐        ┌─────────┐     │
│    │SQLite/  │         │ Yahoo   │        │ Memory  │     │
│    │JSON DB  │         │ Finance │        │ Cache   │     │
│    └─────────┘         └─────────┘        └─────────┘     │
└─────────────────────────────────────────────────────────────┘
```

**Legend:**
- Blue boxes = Presentation Layer
- Yellow boxes = Business Logic Layer  
- Purple boxes = Data Access Layer
- Green boxes = External systems/storage

---

## Diagram 2: Component Specification (Detailed)

```
PRESENTATION LAYER
┌─────────────────────────────────────────────────────────────────────────┐
│                                                                         │
│  ┌──────────────────────┐    ┌──────────────────────┐                 │
│  │ ChartDisplayComponent│    │ InputFormComponent   │                 │
│  │ ──────────────────── │    │ ──────────────────── │                 │
│  │ IChartDisplay        │    │ IInputHandler        │                 │
│  │ ──────────────────── │    │ ──────────────────── │                 │
│  │ + renderChart()      │    │ + getStockSymbol()   │                 │
│  │ + renderComparison() │    │ + getDateRange()     │                 │
│  │ + clearChart()       │    │ + validateInput()    │                 │
│  └──────────┬───────────┘    └──────────┬───────────┘                 │
│             │                           │                              │
│             └───────────────┬───────────┘                              │
│                             │                                          │
│                  ┌──────────▼───────────┐                              │
│                  │ MainUIController     │                              │
│                  │ ──────────────────── │                              │
│                  │ + initialize()       │                              │
│                  │ + handleUserInput()  │                              │
│                  │ + displayError()     │                              │
│                  │ + updateView()       │                              │
│                  └──────────┬───────────┘                              │
└─────────────────────────────┼────────────────────────────────────────┘
                              │
BUSINESS LOGIC LAYER          │
┌─────────────────────────────▼────────────────────────────────────────┐
│                                                                       │
│               ┌─────────────────────────────────┐                    │
│               │  StockDataManager               │                    │
│               │  ──────────────────────────────  │                    │
│               │  IStockDataManager              │                    │
│               │  ──────────────────────────────  │                    │
│               │  + fetchStockData()             │                    │
│               │  + getStoredData()              │                    │
│               │  + isDataAvailable()            │                    │
│               │  + validateSymbol()             │                    │
│               └──┬────────────────────────────┬─┘                    │
│                  │                            │                       │
│       ┌──────────▼──────────┐    ┌───────────▼──────────┐            │
│       │ PriceAnalyzer       │    │ ComparisonService    │            │
│       │ ──────────────────  │    │ ───────────────────  │            │
│       │ AbstractPriceAnalyzer│   │ IComparisonService   │            │
│       │ ──────────────────  │    │ ───────────────────  │            │
│       │ + calculateChange() │    │ + compareStocks()    │            │
│       │ + findHighLow()     │    │ + alignDataRanges()  │            │
│       │ + normalizeData()   │    │ + calcPerformance()  │            │
│       └─────────────────────┘    └──────────────────────┘            │
│                                                                       │
└───────────────────────────────┬───────────────────────────────────────┘
                                │
DATA ACCESS LAYER               │
┌───────────────────────────────▼───────────────────────────────────────┐
│                                                                        │
│     ┌───────────────┐    ┌───────────────┐    ┌───────────────┐      │
│     │LocalRepository│    │  APIService   │    │ CacheManager  │      │
│     │────────────── │    │────────────── │    │────────────── │      │
│     │ IRepository   │    │ IAPIService   │    │ ICacheManager │      │
│     │────────────── │    │────────────── │    │────────────── │      │
│     │ + save()      │    │ + fetchData() │    │ + put()       │      │
│     │ + retrieve()  │    │ + validate()  │    │ + get()       │      │
│     │ + exists()    │    │ + isOnline()  │    │ + invalidate()│      │
│     │ + delete()    │    │               │    │ + clear()     │      │
│     └───────┬───────┘    └───────┬───────┘    └───────┬───────┘      │
│             │                    │                    │               │
│             ▼                    ▼                    ▼               │
│       ┌─────────┐          ┌─────────┐         ┌─────────┐           │
│       │SQLite or│          │ Yahoo   │         │ Memory  │           │
│       │JSON DB  │          │ Finance │         │ Cache   │           │
│       │  Files  │          │   API   │         │         │           │
│       └─────────┘          └─────────┘         └─────────┘           │
│                                                                        │
└────────────────────────────────────────────────────────────────────────┘

DOMAIN MODELS (Used by all layers)
┌────────────────────────────────────────────────────────────────┐
│                                                                │
│  ┌─────────────────────┐        ┌─────────────────────┐       │
│  │    StockData        │        │    DateRange        │       │
│  │  ─────────────────  │        │  ─────────────────  │       │
│  │  - symbol: String   │        │  - startDate        │       │
│  │  - date: LocalDate  │        │  - endDate          │       │
│  │  - open: double     │        │  - MAX_DAYS: 730    │       │
│  │  - high: double     │        │  ─────────────────  │       │
│  │  - low: double      │        │  + validate()       │       │
│  │  - close: double    │        │  + getDayCount()    │       │
│  │  - volume: long     │        │  + contains()       │       │
│  └─────────────────────┘        └─────────────────────┘       │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

## How to Draw These in PowerPoint

### For Diagram 1 (High-Level):

1. **Create 3 large rectangles** for the layers
   - Top: Light Blue (Presentation)
   - Middle: Light Yellow (Business Logic)
   - Bottom: Light Purple (Data Access)

2. **Inside each layer, add 3 smaller rectangles** for components
   - Use rounded corners
   - Add component names

3. **Add arrows** between layers going downward
   - Use thick arrows

4. **Add icons** (optional):
   - 🖥️ for UI components
   - ⚙️ for business logic
   - 💾 for data access

### For Diagram 2 (Component Specification):

1. **Use the same 3 layer rectangles** as background

2. **For each component, create a box with:**
   ```
   Component Name
   ─────────────────
   Interface Name
   ─────────────────
   + method1()
   + method2()
   + method3()
   ```

3. **Add arrows** between components to show dependencies

4. **Add small boxes** at bottom for:
   - Database (cylinder shape)
   - External API (cloud shape)
   - Memory cache (rectangle)

### Colors to Use:
- **Presentation Layer**: Light Blue (#E1F5FF)
- **Business Logic Layer**: Light Yellow (#FFF4E1)
- **Data Access Layer**: Light Purple (#F0E1FF)
- **Domain Models**: Light Green (#E8F5E9)
- **External Systems**: Darker Green (#D4EDDA)

---

## Quick Draw.io Instructions

1. Go to https://app.diagrams.net/
2. Create new diagram
3. Use shapes from left panel:
   - **Rectangles** for layers and components
   - **Arrows** for dependencies
   - **Cylinder** for database
   - **Cloud** for external API

4. Use **Text tool** to add labels

5. **Group** related items together

6. **Export** as PNG when done

---

## For Your Code Review

**Print out these diagrams** on A4 paper as backup in case tech fails!

You can also draw them on a whiteboard during the presentation if you prefer a more interactive approach.

---

*These are simplified versions - your actual diagrams can be more detailed*
