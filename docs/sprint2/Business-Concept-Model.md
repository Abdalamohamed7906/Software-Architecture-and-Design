# Business Concept Model - Sprint 2

## Stock-Compare Application

**Date:** February 27, 2026  
**Author:** Anwar  
**Sprint:** 2

---

## Overview

The Business Concept Model identifies the key business concepts, entities, and their relationships within the Stock-Compare application domain. This model forms the foundation for understanding what the system does from a business perspective.

---

## Core Business Concepts

### 1. **User**
**Description:** The person using the Stock-Compare application to analyze stock market data.

**Responsibilities:**
- Searches for stock data by symbol
- Compares multiple stocks
- Views price charts and analysis
- Defines date ranges for analysis

**Attributes:**
- User preferences (optional)
- Search history (optional)

**Relationships:**
- Performs searches
- Views comparisons
- Requests charts

---

### 2. **Stock Symbol**
**Description:** A unique identifier representing a publicly traded company (e.g., "AAPL" for Apple Inc.)

**Characteristics:**
- 1-5 uppercase letters
- Unique per company
- Used to identify stocks in markets

**Relationships:**
- Identifies a company
- Associated with price data
- Used in searches and comparisons

**Examples:**
- AAPL (Apple)
- GOOGL (Google)
- MSFT (Microsoft)
- TSLA (Tesla)

---

### 3. **Stock Price Data**
**Description:** Historical or real-time price information for a stock on a specific date.

**Components:**
- **Date:** When the price was recorded
- **Open:** Opening price for the trading day
- **High:** Highest price during the day
- **Low:** Lowest price during the day
- **Close:** Closing price for the day
- **Volume:** Number of shares traded

**Relationships:**
- Belongs to a specific stock symbol
- Associated with a specific date
- Used in analysis and comparisons
- Displayed in charts

**Business Rules:**
- Each stock has one price entry per trading day
- Prices are in USD
- Volume is always a positive integer

---

### 4. **Date Range**
**Description:** A time period defined by a start date and end date, used to specify the scope of data analysis.

**Components:**
- Start Date
- End Date

**Business Rules:**
- End date must be after or equal to start date
- Maximum range: 2 years (730 days)
- Both dates must be valid calendar dates
- Cannot include future dates

**Relationships:**
- Defines scope of price data retrieval
- Applied to stock searches
- Used in comparisons

**Example:**
- Start: January 1, 2024
- End: December 31, 2024
- Range: 1 year

---

### 5. **Stock Comparison**
**Description:** A side-by-side analysis of two stocks over the same time period.

**Components:**
- Stock 1 symbol and data
- Stock 2 symbol and data
- Common date range
- Performance metrics
- Comparison results

**Calculated Metrics:**
- Relative return (Stock 1 vs Stock 2)
- Price change percentage for each
- Volatility comparison
- Correlation (optional)

**Relationships:**
- Requires two stock symbols
- Uses price data for both stocks
- Generates comparison results
- Can be visualized in charts

**Business Value:**
- Helps users make investment decisions
- Identifies better performing stocks
- Shows relative strength

---

### 6. **Chart/Visualization**
**Description:** A graphical representation of stock price data over time.

**Types:**
- Line chart (price over time)
- Comparison chart (two stocks overlaid)
- Candlestick chart (OHLC)

**Components:**
- X-axis: Time (dates)
- Y-axis: Price (USD)
- Data points: Price values
- Legend: Stock symbols and colors

**Relationships:**
- Displays price data
- Can show single stock or comparison
- Generated from date range query

**Business Value:**
- Visual trend analysis
- Easy pattern recognition
- Quick comparison

---

### 7. **Data Source**
**Description:** The origin of stock price data.

**Types:**
- **External API:** Yahoo Finance, Alpha Vantage
- **Local Cache:** In-memory temporary storage
- **Repository:** Persistent local storage

**Characteristics:**
- External sources require network connection
- Cache provides fast access
- Repository enables offline capability

**Relationships:**
- Provides price data
- Feeds into the system
- Can be queried by symbol and date range

**Business Rules:**
- External API is primary source
- Cache reduces API calls
- Repository stores previously fetched data

---

### 8. **Analysis Result**
**Description:** The output of performing calculations on stock price data.

**Types:**
- Price change percentage
- Average price
- High/Low prices
- Return on investment
- Volatility

**Components:**
- Calculated value
- Formula used
- Time period
- Stock symbol(s)

**Relationships:**
- Derived from price data
- Used in comparisons
- Displayed to user

---

### 9. **Performance Metrics**
**Description:** Key performance indicators comparing two stocks.

**Metrics:**
- Stock 1 return percentage
- Stock 2 return percentage
- Relative difference
- Better performer indicator

**Relationships:**
- Created during stock comparison
- Based on price data analysis
- Presented to user

---

## Business Concept Relationships

### Primary Relationships:

```
User
  ↓ performs
Search (by Stock Symbol, Date Range)
  ↓ retrieves
Stock Price Data (from Data Source)
  ↓ used in
Analysis
  ↓ produces
Analysis Result / Performance Metrics
  ↓ displayed in
Chart/Visualization
  ↓ viewed by
User
```

### Comparison Flow:

```
User
  ↓ requests
Stock Comparison (Stock Symbol 1, Stock Symbol 2, Date Range)
  ↓ fetches
Stock Price Data (for both symbols)
  ↓ analyzes
Performance Metrics
  ↓ generates
Comparison Result
  ↓ displays
Comparison Chart
```

---

## Business Rules Summary

1. **Stock Symbol Rules:**
   - Must be 1-5 uppercase letters
   - Must be valid and exist in market

2. **Date Range Rules:**
   - Maximum 2 years
   - End date ≥ Start date
   - No future dates

3. **Price Data Rules:**
   - One entry per stock per trading day
   - Prices must be positive
   - Volume must be non-negative

4. **Comparison Rules:**
   - Requires exactly 2 stocks
   - Must use same date range
   - Both stocks must have data for the period

5. **Data Source Rules:**
   - Check cache first (fastest)
   - Check repository second (offline capability)
   - Fetch from API third (requires network)
   - Store fetched data for future use

---

## Business Value Proposition

### For Users:
- **Informed Decisions:** Compare stocks before investing
- **Historical Analysis:** Understand past performance
- **Visual Insights:** See trends at a glance
- **Time Savings:** Quick comparisons vs manual research

### For Business:
- **Data Efficiency:** Caching reduces API costs
- **Offline Capability:** Repository enables offline analysis
- **Scalability:** Can add more data sources
- **Extensibility:** Can add more analysis types

---

## Key Insights

1. **Data Flow:** External API → Cache → Repository → User
2. **Core Value:** Comparison capability drives user decisions
3. **Performance:** Multi-tier data access ensures speed
4. **Usability:** Visual charts make data accessible

---

## Next Steps

This Business Concept Model will inform:
1. **Business Type Model:** Java classes representing these concepts
2. **Use Cases:** How users interact with these concepts
3. **System Architecture:** How these concepts are implemented
4. **Interface Design:** How components expose these concepts

---

**Document Status:** ✅ Complete  
**Review Date:** Sprint 2 Code Review (20.3.26)  
**Next Document:** Business Type Model
