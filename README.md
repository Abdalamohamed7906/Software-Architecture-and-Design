# Sprint 2 — Software Architecture Development

## StockCompare — Share Price Analysis Application

**Sprint Deadline:** 19.3.26 | **Code Review:** 20.3.26

---

## Overview

This sprint focuses on developing a full software architecture from the requirements specification produced in Sprint 1. The architecture follows **Clean Architecture** and **Service-Oriented Architecture (SOA)** principles, with a clear separation of concerns across all layers.

---

## Team Members

- Anwar
- Abdala
- Meshari
- Omran
- Ismail

---

## Models Produced

### 1. Business Concept Model
A high-level domain model showing all concepts relevant to the StockCompare system, whether or not they are directly managed by the application.

**Key concepts:**
- `User` — abstract, with three specialisations: `Visitor`, `RegisteredUser`, `Admin`
- `RegisteredUser` has one `Account` (1:1) and saves 0..* `SavedStocks`
- `User` searches 1..* `ShareSymbols`
- Each `ShareSymbol` has 1..* `PriceData` records
- `PriceData` links to `DateRange` (1:1) and generates `PriceGraph`, `ComparisonResult`, and `ExportFile`
- `SavedStock` references 1..* `PriceData` records

---

### 2. Use Case Model
A full use case diagram with 12 use cases covering all system functionality.

**Actors:** User, RegisteredUser, Visitor, Admin

**Use Cases:**
| # | Use Case | Actor |
|---|----------|-------|
| 1 | Create Account | Visitor |
| 2 | Manage Account | RegisteredUser |
| 3 | Search Share Symbol | User |
| 4 | Select Date Range | User |
| 5 | Retrieve Share Price Data | User |
| 6 | View Share Price Graph | User |
| 7 | Compare Share Prices | User |
| 8 | Save Stock Data | RegisteredUser |
| 9 | Load Saved Stock Data | RegisteredUser |
| 10 | Delete Saved Stock | RegisteredUser |
| 11 | Export Price Data | RegisteredUser |
| 12 | Update Stock Data Stored | Admin |

Each use case includes a full written description of actor steps and system steps.

---

### 3. System Interfaces
System interfaces and operations were defined for each use case, mapping each use case to a corresponding interface type.

**Key interfaces defined:**
- `ICreateAccount` — `createAccount()`, `validateUserDetails()`, `checkUserExists()`
- `IManageAccount` — `getAccountDetails()`, `updateAccountDetails()`, `validateUpdatedDetails()`
- `ISearchShare` — `searchShareSymbol()`, `getShareDetails()`
- `ISelectDateRange` — `validateDateRange()`, `createDateRange()`
- `IRetrievePriceData` — `getSharePriceData()`, `storeTemporaryPriceData()`
- `IViewPriceGraph` — `getPriceDataForGraph()`, `generatePriceGraph()`
- `ICompareSharePrices` — `getMultipleShareData()`, `compareSharePrices()`
- `ISaveStockData` — `saveStockData()`, `confirmSave()`
- `ILoadSavedStock` — `getSavedStocks()`, `loadStockData()`
- `IDeleteSavedStock` — `getSavedStocks()`, `deleteStock()`
- `IExportPriceData` — `generateExportFile()`, `exportPriceData()`
- `IUpdateStockData` — `updateStoredStockData()`, `fetchLatestStockData()`

---

### 4. Business Type Model
Derived directly from the Business Concept Model, using the SOMA stereotype notation.

**Three spheres of responsibility:**

| Sphere | Interface | Core Type | Dependent Types |
|--------|-----------|-----------|-----------------|
| User Management | `IAccountService` | `RegisteredUser`, `Admin` | `Account` (type), `Visitor` (category) |
| Stock Management | `IStockService` | `ShareSymbol` | `SavedStock`, `PriceGraph` |
| Price / Data Mgmt | `IStockAnalysisService` | `PriceData` | `DateRange`, `ComparisonResult`, `ExportFile` |

**Cross-boundary associations:**
- `RegisteredUser` —saves→ `SavedStock` (1:0..*)
- `ShareSymbol` —has→ `PriceData` (1:1..*)

---

### 5. Initial System Architecture
A layered architecture diagram was produced showing how all components are structured and how interfaces are allocated.

**Layers:**
```
Presentation Layer
  └── SavedStockUI, CreateAccountUI, ManageAccountUI,
      GraphUI, AdminUI, StockSearchUI, DateRangeUI

System Interfaces
  └── ILoadSavedStock, IDeleteSavedStock, ISaveStockData,
      ICreateAccount, IManageAccount, IExportPriceData,
      IViewPriceGraph, ICompareSharePrices, IUpdateStockData,
      ISearchShare, IRetrievePriceData, ISelectDateRange

Business Services
  └── ISavedStockService, IAccountService, IExportService,
      IStockAnalysisService, IStockService

Data Access Layer
  └── SavedStockRepository, UserRepository,
      StockRepository, StockAPIClient

External
  └── Database (SQLite), External Stock API
```

**Interface allocation notes:**
- `IStockAnalysisService` and `IStockService` implement multiple interfaces (compare, retrieve, graph, update)
- `IAccountService` implements account creation and management
- `ISavedStockService` implements save, load, and delete operations

---

### 6. Business Interfaces (Collaboration / Sequence Diagrams)
Sequence diagrams were produced for all 12 use cases showing how business interface operations are invoked.

**Pattern used across all diagrams:**
```
User → UI → Service → Repository/API → response back to User
```

| Use Case | Participants |
|----------|-------------|
| Create Account | Visitor, UI, UserService, UserRepository |
| Manage Account | User, UI, UserService, UserRepository |
| Search Share Symbol | User, UI, SearchService, StockAPI |
| Select Date Range | User, UI, DateService |
| Retrieve Share Price Data | User, UI, PriceService, StockAPI, TempStorage |
| View Share Price Graph | User, UI, GraphService, PriceService |
| Compare Share Prices | User, UI, CompareService, StockAPI |
| Save Stock Data | User, UI, SaveService, Database |
| Load Saved Stock Data | User, UI, LoadService, Database |
| Delete Saved Stock | User, UI, DeleteService, Database |
| Export Price Data | User, UI, ExportService, FileGenerator |
| Update Stock Data | System, UpdateService, StockAPI, Database |

---

## Implementation

### Clean Architecture
The codebase follows Clean Architecture principles with strict layer separation:

```
com.stockcompare/
├── presentation/     ← UI layer, no business logic (Main.java)
├── service/          ← Business service interfaces and implementations
├── domain/           ← Core business types (User, Stock, PriceData)
└── data/             ← Repository implementations, DB access
```

**Key principles applied:**
- All business logic goes through service interfaces — the UI never talks directly to the database
- Dependencies only point inward (Presentation → Service → Data)
- External dependencies (SQLite, Stock API) are isolated in the Data Access layer

### Database Schema
SQLite database (`stockcompare.db`) with the following tables:

| Table | Purpose |
|-------|---------|
| `users` | Stores registered users with `role` column (`user`/`admin`) |
| `accounts` | Separate account table, 1:1 with users |
| `saved_stocks` | User-saved stock symbols with date ranges |
| `price_data` | Fetched share price records (OHLCV) |
| `comparison_results` | Stored comparison outputs |
| `export_files` | Record of exported files per user |

### Dependencies
| Library | Purpose |
|---------|---------|
| `sqlite-jdbc 3.45.1.0` | SQLite database access |
| `gson 2.10.1` | JSON parsing from Stock API |
| `slf4j-simple 1.7.36` | Logging |
| `junit-jupiter 5.10.0` | Unit testing |

---

## GitHub Structure

| Branch | Contents |
|--------|----------|
| `sprint1` | Sprint 1 — Requirements specification, initial diagrams |
| `sprint2` | Sprint 2 — Architecture models, sequence diagrams, implementation |

---

## How to Run

```bash
# Clone the repo
git clone https://github.com/Abdalamohamed7906/Software-Architecture-and-Design.git

# Open in IntelliJ IDEA
# Ensure Java JDK 17+ is installed

# Run via Maven
mvn exec:java

# Or run Main.java directly from IntelliJ
```
