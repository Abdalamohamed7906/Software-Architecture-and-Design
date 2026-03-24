# Architecture Design Report — StockCompare
## Sprint 2: Software Architecture Development

---

## Section 1 — Business Interfaces

### How Business Interface Operations Were Discovered

Business interface operations were discovered by systematically analysing each use case from the Use Case Model and identifying what the system must be able to do in order to fulfil each actor's goal. The process followed three steps:

1. **Start from the use case steps** — each system step in a use case directly implies one or more operations that must exist in a system interface.
2. **Group related operations** — operations that belong to the same domain concern (e.g. user account management) were grouped into a single business interface.
3. **Validate with sequence diagrams** — collaboration/sequence diagrams were drawn for each use case to confirm the operations are sufficient and correctly ordered.

---

### Discovery — Use Case by Use Case

#### Create Account → `ICreateAccount` / `IAccountService`
The use case requires the system to display a registration form, validate user input, check for duplicate usernames/emails, and create the account. This directly produced three operations:
- `createAccount(UserDetail user)` — triggered when the user submits the form
- `validateUserDetails(UserDetail user)` — validates format of input fields
- `checkUserExists(String username, String email)` — checks for duplicates before saving

#### Manage Account → `IManageAccount` / `IAccountService`
The use case requires the system to retrieve stored account details, allow the user to modify them, validate the changes, and save them. This produced:
- `getAccountDetails(String userId)` — fetches current account data
- `updateAccountDetails(UserDetail user)` — saves updated details
- `validateUpdatedDetails(UserDetail user)` — validates before saving

Both `ICreateAccount` and `IManageAccount` were allocated to the same business service (`IAccountService`) because they share the same domain (user management) and the same data access layer (`UserRepository`).

#### Search Share Symbol → `ISearchShare` / `IStockService`
The use case requires the system to accept a search query, call the external stock API, and return matching symbols. This produced:
- `searchShareSymbol(String query)` — sends query to the stock API
- `getShareDetails(String symbol)` — retrieves details for a selected symbol

#### Select Date Range → `ISelectDateRange` / `IStockService`
The use case requires the system to display date selection options, accept start and end dates, and validate them. This produced:
- `validateDateRange(Date startDate, Date endDate)` — checks dates are valid and logical
- `createDateRange(Date startDate, Date endDate)` — creates a DateRange object for use in subsequent operations

#### Retrieve Share Price Data → `IRetrievePriceData` / `IStockAnalysisService`
The use case requires the system to fetch price data from the external API for a given symbol and date range, then store it temporarily. This produced:
- `getSharePriceData(String symbol, Date startDate, Date endDate)` — fetches data from the API
- `storeTemporaryPriceData(PriceData[] data)` — stores data in temporary session storage for display

#### View Share Price Graph → `IViewPriceGraph` / `IStockAnalysisService`
The use case requires the system to process retrieved price data and render it as a graph. This produced:
- `getPriceDataForGraph(String symbol, Date startDate, Date endDate)` — retrieves the data set for graphing
- `generatePriceGraph(PriceData[] data)` — processes data into a graphical chart

#### Compare Share Prices → `ICompareSharePrices` / `IStockAnalysisService`
The use case requires the system to retrieve price data for multiple symbols simultaneously and produce a comparison. This produced:
- `getMultipleShareData(String[] symbols, Date startDate, Date endDate)` — fetches data for all selected symbols
- `compareSharePrices(PriceData[] data)` — processes and compares the data sets

`IViewPriceGraph`, `IRetrievePriceData`, and `ICompareSharePrices` were all allocated to `IStockAnalysisService` because they all operate on `PriceData` and share the same analytical concern.

#### Save Stock Data → `ISaveStockData` / `ISavedStockService`
The use case requires the system to accept a save request, ask for confirmation, and persist the stock data to the user's account. This produced:
- `saveStockData(String userId, PriceData[] data)` — saves stock data linked to the user
- `confirmSave(String userId, String symbol)` — confirms the save was successful

#### Load Saved Stock Data → `ILoadSavedStock` / `ISavedStockService`
The use case requires the system to retrieve the user's list of saved stocks and load the selected one. This produced:
- `getSavedStocks(String userId)` — retrieves the list of saved stocks for the user
- `loadStockData(String stockId)` — loads the full price data for the selected stock

#### Delete Saved Stock → `IDeleteSavedStock` / `ISavedStockService`
The use case requires the system to display saved stocks, request confirmation, and remove the selected one. This produced:
- `getSavedStocks(String userId)` — retrieves the list (shared with Load use case)
- `deleteStock(String stockId)` — removes the selected stock from storage

`ISaveStockData`, `ILoadSavedStock`, and `IDeleteSavedStock` were allocated to `ISavedStockService` because they all manage the same `SavedStock` entity in the database.

#### Export Price Data → `IExportPriceData` / `IExportService`
The use case requires the system to accept a format selection, convert the data, generate a file, and provide it for download. This produced:
- `generateExportFile(PriceData[] data, String format)` — converts data to the chosen format
- `exportPriceData(File exportFile)` — provides the file for download

#### Update Stored Stock Data → `IUpdateStockData` / `IStockService`
The use case (triggered by Admin) requires the system to connect to the stock API and replace outdated stored data with the latest values. This produced:
- `updateStoredStockData()` — triggers the update process
- `fetchLatestStockData()` — fetches the latest data from the API

---

### Interface to Component Allocation Summary

| Business Interface | Component | Reason |
|---|---|---|
| `ICreateAccount`, `IManageAccount` | `IAccountService` | Same domain: user management |
| `ISearchShare`, `ISelectDateRange`, `IUpdateStockData` | `IStockService` | Same domain: stock symbol management |
| `IRetrievePriceData`, `IViewPriceGraph`, `ICompareSharePrices` | `IStockAnalysisService` | Same domain: price data analysis |
| `ISaveStockData`, `ILoadSavedStock`, `IDeleteSavedStock` | `ISavedStockService` | Same domain: saved stock CRUD |
| `IExportPriceData` | `IExportService` | Isolated concern: file generation |

**Components implementing more than one interface:**
- `IAccountService` → implements `ICreateAccount` + `IManageAccount`
- `IStockService` → implements `ISearchShare` + `ISelectDateRange` + `IUpdateStockData`
- `IStockAnalysisService` → implements `IRetrievePriceData` + `IViewPriceGraph` + `ICompareSharePrices`
- `ISavedStockService` → implements `ISaveStockData` + `ILoadSavedStock` + `IDeleteSavedStock`

**Components implementing only one interface:**
- `IExportService` → implements only `IExportPriceData`

---

## Section 2 — Clean Architecture

### Overview

The StockCompare system is implemented following **Clean Architecture** principles as defined by Robert C. Martin. The core idea is that business logic should be completely independent of frameworks, databases, and external APIs. Dependencies always point inward — outer layers depend on inner layers, never the other way around.

---

### Layer Structure

```
┌─────────────────────────────────────────┐
│          Presentation Layer             │  ← Outermost
│   (Main.java, UI classes)               │
├─────────────────────────────────────────┤
│         Business Service Layer          │
│   (Service interfaces + implementations)│
├─────────────────────────────────────────┤
│           Domain Layer                  │
│   (Core business types: User, Stock...) │
├─────────────────────────────────────────┤
│         Data Access Layer               │  ← Innermost
│   (Repositories, StockAPIClient)        │
└─────────────────────────────────────────┘
         ↓ Dependencies point inward ↓
```

---

### Layer Descriptions

#### Presentation Layer — `com.stockcompare.presentation`
This is the outermost layer. It contains `Main.java` which acts as the entry point and console UI. This layer is responsible only for displaying information to the user and collecting input. It contains **no business logic** — all requests are delegated to service interfaces via the `AppContainer`.

- Knows about: Service interfaces (via `AppContainer`)
- Does NOT know about: Database, external APIs, repositories

#### Business Service Layer — `com.stockcompare.service`
This layer contains all business logic. Each service implements one or more system interfaces defined in the architecture. Services coordinate between the presentation layer and the data access layer.

- `AccountService` implements `IAccountService`
- `StockService` implements `IStockService`
- `StockAnalysisService` implements `IStockAnalysisService`
- `SavedStockService` implements `ISavedStockService`
- `ExportService` implements `IExportService`

Services only depend on repository interfaces and domain types — they never directly reference database or API classes.

#### Domain Layer — `com.stockcompare.domain`
This is the innermost layer containing the core business types. These are plain Java objects (POJOs) with no dependencies on any framework, database, or external library.

Key domain types:
- `UserDetail` — represents a registered user
- `ShareSymbol` / `ShareDetail` — represents a stock symbol
- `PriceData` — represents a price record (open, high, low, close, volume)
- `DateRange` — represents a selected time period
- `SavedStock` — represents a user's saved stock
- `ComparisonResult` — represents the output of a price comparison
- `ExportFile` — represents a generated export file

#### Data Access Layer — `com.stockcompare.data`
This is the outermost infrastructure layer. It contains the concrete implementations of repositories and the external API client. This layer is the only place in the system that knows about SQLite or the Stock API.

- `UserRepository` — reads/writes user data to SQLite
- `SavedStockRepository` — reads/writes saved stocks to SQLite
- `StockRepository` — manages cached stock/price data in SQLite
- `StockAPIClient` — communicates with the external stock price API (JSON via Gson)

---

### How the Principles Are Applied

#### Dependency Rule
No inner layer knows about any outer layer. For example:
- `UserRepository` (Data) does not import anything from `presentation`
- `AccountService` (Service) does not import `sqlite-jdbc` directly
- `Main.java` (Presentation) only calls methods through service interfaces

#### Interface Segregation
Each business interface is narrow and focused. The UI calls `IAccountService.createAccount()` — it does not need to know that internally this calls `UserRepository.saveUser()` and `UserRepository.checkUserExists()`.

#### Inversion of Control via AppContainer
`AppContainer` acts as the dependency injection container. It wires together the concrete implementations at startup:

```java
// AppContainer wires everything together
UserRepository userRepo = new UserRepository(db);
IAccountService accountService = new AccountService(userRepo);
// Main only sees IAccountService, not UserRepository
```

This means the presentation layer is fully decoupled from the data access layer.

#### Testability
Because all business logic is in service classes that depend only on interfaces, each service can be unit tested independently by substituting mock repositories — without needing a real database or API connection.

---

### Summary

| Principle | How It Is Applied |
|---|---|
| Single Responsibility | Each service handles one domain concern only |
| Dependency Inversion | Services depend on repository interfaces, not concrete classes |
| Interface Segregation | Each interface is narrow and use-case specific |
| Separation of Concerns | UI, business logic, and data access are fully separated |
| Testability | Services can be tested with mock repositories |
| Independence from frameworks | Domain types are plain Java objects with no external dependencies |
