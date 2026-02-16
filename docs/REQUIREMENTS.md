# Requirements Specification - Stock-Compare
**Sprint 1 Deliverable**

## Team Members
- Anwar
- Abdala
- Meshari
- Omran

## 1. Project Scope

### 1.1 In Scope
- [ ] Fetch daily stock price data from external API (Yahoo Finance or similar)
- [ ] Support date range queries (up to 2 years maximum)
- [ ] Store stock price data locally (SQLite or JSON)
- [ ] Display price data as line graphs
- [ ] Compare up to 2 different stocks on same graph
- [ ] Offline functionality using cached data
- [ ] Basic user interface for data entry and display

### 1.2 Out of Scope
- Advanced UI/UX design and styling
- Real-time stock price updates
- User authentication and accounts
- Portfolio management features
- Stock trading functionality
- Mobile application
- Support for more than 2 stocks comparison simultaneously
- Historical data beyond 2 years
- Technical indicators (moving averages, RSI, etc.) - *may be added in Sprint 3*

---

## 2. Functional Requirements

### FR1: Stock Data Retrieval
**Priority**: High  
**Description**: System must fetch daily stock price data from external API

**Acceptance Criteria:**
- [ ] User can enter a valid stock symbol (e.g., AAPL, GOOGL)
- [ ] User can specify start date and end date
- [ ] System validates date range (maximum 2 years)
- [ ] System retrieves: Date, Open, High, Low, Close, Volume
- [ ] System handles invalid stock symbols with error message
- [ ] System handles API connection failures gracefully

---

### FR2: Data Persistence
**Priority**: High  
**Description**: System must store fetched stock data locally

**Acceptance Criteria:**
- [ ] Data is saved to local storage (SQLite or JSON file)
- [ ] Duplicate data for same stock/date is not stored
- [ ] Stored data includes: Symbol, Date, Open, High, Low, Close, Volume
- [ ] System can retrieve stored data without network connection
- [ ] System updates existing records if new data is fetched

---

### FR3: Data Visualization
**Priority**: High  
**Description**: System must display stock price data as graphical charts

**Acceptance Criteria:**
- [ ] Display line graph of closing prices over time
- [ ] X-axis shows dates, Y-axis shows prices
- [ ] Graph is clearly labeled with stock symbol
- [ ] User can view graph for single stock
- [ ] User can view comparison graph for two stocks
- [ ] Graph scales appropriately to data range

---

### FR4: Stock Comparison
**Priority**: Medium  
**Description**: System must allow comparison of two stocks

**Acceptance Criteria:**
- [ ] User can select two different stock symbols
- [ ] Both stocks display on same graph with different colors
- [ ] Legend identifies which line represents which stock
- [ ] Date ranges can be adjusted for comparison
- [ ] System handles cases where date ranges don't overlap

---

### FR5: Offline Functionality
**Priority**: Medium  
**Description**: System must work with cached data when offline

**Acceptance Criteria:**
- [ ] User can view previously fetched stock data
- [ ] System indicates when using cached vs fresh data
- [ ] System attempts to fetch new data when online
- [ ] User is notified when network is unavailable

---

## 3. Non-Functional Requirements

### NFR1: Performance
- System should retrieve stock data within 5 seconds
- Graph rendering should complete within 2 seconds
- Application should handle at least 10,000 data points efficiently

### NFR2: Reliability
- System should handle API failures without crashing
- Data persistence should not lose data on application close
- System should validate all user inputs

### NFR3: Usability
- User interface should be intuitive and self-explanatory
- Error messages should be clear and actionable
- Graph should be readable with appropriate scaling

### NFR4: Maintainability
- Code should follow clean architecture principles
- Components should be loosely coupled
- Code should be well-documented and commented
- System should be easily extensible for future features

### NFR5: Scalability
- Architecture should support adding new data sources
- Design should accommodate additional chart types
- System should support comparing more than 2 stocks in future

---

## 4. System Constraints

### Technical Constraints
- **Programming Language**: Java
- **Data Source**: Yahoo Finance API (or equivalent free API)
- **Storage**: SQLite database or JSON files
- **Maximum Date Range**: 2 years
- **Deployment**: Desktop application (web version optional)

### External Dependencies
- Internet connection for initial data fetch
- External stock price API availability
- Third-party charting library (if used)

---

## 5. User Stories

### US1: Fetch Stock Data
*As a user, I want to retrieve historical stock prices for a specific company so that I can analyze its performance.*

### US2: View Price Trends
*As a user, I want to see a graphical representation of stock prices over time so that I can visualize trends easily.*

### US3: Compare Stocks
*As a user, I want to compare two different stocks on the same chart so that I can evaluate their relative performance.*

### US4: Access Offline
*As a user, I want to access previously downloaded stock data when offline so that I can work without internet connection.*

### US5: Validate Input
*As a user, I want to receive clear error messages for invalid inputs so that I understand what went wrong.*

---

## 6. Requirements Alignment with Project Goals

| Requirement | Project Goal Alignment | Architecture Impact |
|-------------|------------------------|---------------------|
| FR1: Data Retrieval | Core functionality | Requires API service component |
| FR2: Persistence | Offline support | Requires data access layer |
| FR3: Visualization | User experience | Requires presentation layer |
| FR4: Comparison | Analysis capability | Requires data processing logic |
| FR5: Offline Mode | Reliability | Requires caching strategy |

---

## 7. Assumptions
- Stock symbols will follow standard format (e.g., NYSE, NASDAQ tickers)
- External API will remain available and free
- Users have basic knowledge of stock symbols
- System will run on machines with Java 11+ installed
- Date format will be standardized (YYYY-MM-DD)

---

## 8. Risk Assessment

| Risk | Impact | Likelihood | Mitigation |
|------|--------|------------|------------|
| API becomes unavailable | High | Medium | Support multiple data sources |
| API rate limiting | Medium | High | Implement caching and throttling |
| Invalid data from API | Medium | Low | Add data validation layer |
| Large data volumes | Medium | Medium | Implement pagination/filtering |

---

## Notes for Team
**To Complete This Document:**
1. Review and check off items in scope/out of scope
2. Add any additional functional requirements you identify
3. Validate acceptance criteria with team
4. Assign owners to each major requirement for implementation
5. Update assumptions based on team discussion

**Assigned To**: [Team member name]  
**Review Date**: [Date]  
**Status**: Draft / Under Review / Approved

---
*Document Version: 1.0*  
*Last Updated: [Date]*
