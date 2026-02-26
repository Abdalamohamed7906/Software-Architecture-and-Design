package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.util.List;

/**
 * INTERFACE: IAPIService
 * PROVIDED BY: APIService component
 * REQUIRED BY: StockDataManager
 */
public interface IAPIService {
    
    List<StockData> fetchHistoricalData(String symbol, DateRange dateRange) 
        throws APIException;
    
    boolean validateSymbol(String symbol);
    
    boolean isConnectionAvailable();
    
    int getRateLimitRemaining();
}

/**
 * Exception for API-related errors
 */
class APIException extends Exception {
    
    public APIException(String message) {
        super(message);
    }
    
    public APIException(String message, Throwable cause) {
        super(message, cause);
    }
}
