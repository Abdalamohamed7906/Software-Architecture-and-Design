# Share Price Comparison Application - Requirements Document

## 1. Project Overview

### 1.1 Purpose
Develop a robust and scalable Share Price Comparison web application that enables users to visualize and compare stock performance over time, with offline functionality through local data persistence.

### 1.2 Scope
- **In Scope**: Daily share price retrieval, local data persistence, graphical comparison of 1-2 companies, offline mode
- **Out of Scope**: Real-time trading, portfolio management, advanced financial analytics, mobile applications

---

## 2. Functional Requirements

### FR1: Share Price Data Retrieval
**Priority: High**
- **FR1.1**: System shall fetch daily share price data for a given stock symbol
- **FR1.2**: System shall accept user-specified date ranges (start date and end date)
- **FR1.3**: Date range shall not exceed 2 years (730 days)
- **FR1.4**: System shall validate stock symbols before fetching data
- **FR1.5**: System shall retrieve: Date, Open, High, Low, Close, Volume, Adjusted Close
- **FR1.6**: System shall integrate with Yahoo Finance API or equivalent data source

### FR2: Data Persistence
**Priority: High**
- **FR2.1**: System shall store fetched share price data locally
- **FR2.2**: System shall support SQLite database OR JSON file storage
- **FR2.3**: System shall prevent duplicate data entries for same symbol and date
- **FR2.4**: System shall allow data retrieval from local storage when offline
- **FR2.5**: System shall update existing data when newer information is available

### FR3: Data Visualization
**Priority: High**
- **FR3.1**: System shall display share price as a line graph over time
- **FR3.2**: System shall support single company visualization
- **FR3.3**: System shall support comparison of two companies simultaneously
- **FR3.4**: Graph shall include: X-axis (Date), Y-axis (Price), Legend, Title
- **FR3.5**: System shall allow users to select which price metric to display (Close, Open, High, Low)

### FR4: User Interface
**Priority: Medium**
- **FR4.1**: System shall provide input fields for stock symbol(s)
- **FR4.2**: System shall provide date range selectors
- **FR4.3**: System shall display loading indicators during data fetch
- **FR4.4**: System shall show error messages for invalid inputs or failed operations
- **FR4.5**: System shall provide a clear "Compare" or "Fetch" button

### FR5: Offline Functionality
**Priority: Medium**
- **FR5.1**: System shall detect network connectivity status
- **FR5.2**: System shall load data from local storage when offline
- **FR5.3**: System shall notify users when operating in offline mode
- **FR5.4**: System shall queue data fetch requests when offline and execute when online

---

## 3. Non-Functional Requirements

### NFR1: Performance
- **NFR1.1**: Data fetch operations shall complete within 5 seconds for typical requests
- **NFR1.2**: Graph rendering shall occur within 2 seconds after data is available
- **NFR1.3**: System shall handle up to 730 data points (2 years) efficiently

### NFR2: Scalability
- **NFR2.1**: Architecture shall support addition of new data sources
- **NFR2.2**: System shall be extensible to support >2 company comparisons in future
- **NFR2.3**: Database schema shall accommodate additional stock metrics

### NFR3: Reliability
- **NFR3.1**: System shall handle API failures gracefully
- **NFR3.2**: System shall validate all user inputs
- **NFR3.3**: System shall maintain data integrity during concurrent operations

### NFR4: Maintainability
- **NFR4.1**: Code shall follow SOLID principles
- **NFR4.2**: Components shall be loosely coupled
- **NFR4.3**: Code shall include comprehensive comments and documentation

### NFR5: Usability
- **NFR5.1**: UI shall be intuitive requiring no training
- **NFR5.2**: Error messages shall be clear and actionable

---

## 4. Technical Requirements

### TR1: Technology Stack
- **Programming Language**: Java 11 or higher
- **Data Storage**: SQLite 3.x OR JSON with Jackson library
- **Charting Library**: JFreeChart or Apache ECharts
- **HTTP Client**: Apache HttpClient or Java HttpClient
- **Build Tool**: Maven or Gradle

### TR2: Data Source
- **Primary**: Yahoo Finance API (via third-party wrapper like YahooFinanceAPI)
- **Alternative**: Alpha Vantage API, IEX Cloud

### TR3: Architecture
- **Pattern**: Layered Architecture with Clear Separation of Concerns
- **Layers**: Presentation, Service, Data Access, Domain Model

---

## 5. Constraints

### C1: Time Constraints
- Sprint 1 deadline: 19/2/26
- Focus on architectural foundation, not UI polish

### C2: Data Constraints
- Maximum 2-year historical data range
- Daily price granularity (no intraday data)

### C3: Network Constraints
- Must function with intermittent connectivity
- Must cache data for offline use

---

## 6. Assumptions

### A1: Data Availability
- Yahoo Finance or equivalent API remains accessible
- API provides consistent data format

### A2: User Environment
- Users have Java Runtime Environment installed
- Users have internet connectivity for initial data fetch

### A3: Stock Symbols
- Users provide valid stock ticker symbols
- Symbols follow standard naming conventions (e.g., AAPL, GOOGL)

---

## 7. Requirements Traceability Matrix

| Requirement ID | Category | Priority | Sprint | Component |
|---------------|----------|----------|--------|-----------|
| FR1.x | Data Retrieval | High | 1-2 | Service Layer |
| FR2.x | Persistence | High | 1-2 | Repository Layer |
| FR3.x | Visualization | High | 2-3 | Presentation Layer |
| FR4.x | UI | Medium | 2-3 | Presentation Layer |
| FR5.x | Offline | Medium | 3 | Service Layer |

---

## 8. Success Criteria

### Sprint 1 Success Criteria:
✓ All requirements documented and approved
✓ Architectural diagram created and validated
✓ Abstract components implemented in Java
✓ GitHub project configured with proper branching
✓ Team tasks allocated and Code of Conduct agreed

### Overall Project Success Criteria:
✓ Application fetches and displays share price data for valid symbols
✓ Application persists data locally
✓ Application compares 2 companies visually
✓ Application works offline with cached data
✓ Code follows architectural principles
✓ All team members contribute equally

---

## 9. Out of Scope (Future Enhancements)

- Real-time stock price updates
- Technical indicators (RSI, MACD, etc.)
- News integration
- Portfolio tracking
- Alert notifications
- Mobile application
- User authentication/multi-user support
- Export to PDF/Excel
- Candlestick charts
- More than 2 company comparisons

---

**Document Version**: 1.0  
**Last Updated**: February 6, 2026  
**Next Review**: Sprint 2 Planning
