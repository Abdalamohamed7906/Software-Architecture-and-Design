package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IStockAnalysisService;
import com.stockcompare.domain.model.PriceData;
import com.stockcompare.repository.IStockRepository;
import com.stockcompare.data.StockAPIClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockAnalysisServiceTest {

    @Mock private StockAPIClient stockAPIClient;
    @Mock private IStockRepository stockRepository;
    private IStockAnalysisService service;

    private final LocalDate start = LocalDate.of(2026,4,9);
    private final LocalDate end   = LocalDate.of(2026,4,14);

    @BeforeEach void setUp() { service = new StockAnalysisService(stockAPIClient, stockRepository); }

    private PriceData pd(String sym, String date, double close) {
        return new PriceData(sym, LocalDate.parse(date), 100.0, 110.0, 90.0, close, 1000000L);
    }

    @Test void getPriceDataForGraph_validSymbol_returnsData() throws Exception {
        when(stockAPIClient.fetchData("AAPL", start, end)).thenReturn(List.of(pd("AAPL","2026-04-09",175.0)));
        List<PriceData> result = service.getPriceDataForGraph("AAPL", start, end);
        assertFalse(result.isEmpty());
    }

    @Test void getPriceDataForGraph_blankSymbol_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getPriceDataForGraph("", start, end));
    }

    @Test void getPriceDataForGraph_nullSymbol_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getPriceDataForGraph(null, start, end));
    }

    @Test void compareShares_twoSymbols_returnsTwoResults() throws Exception {
        when(stockAPIClient.fetchData("AAPL", start, end)).thenReturn(List.of(pd("AAPL","2026-04-09",175.0)));
        when(stockAPIClient.fetchData("MSFT", start, end)).thenReturn(List.of(pd("MSFT","2026-04-09",320.0)));
        List<List<PriceData>> result = service.compareShares(List.of("AAPL","MSFT"), start, end);
        assertEquals(2, result.size());
    }

    @Test void compareShares_fewerThanTwoSymbols_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.compareShares(List.of("AAPL"), start, end));
    }

    @Test void compareShares_nullList_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.compareShares(null, start, end));
    }

    @Test void compareShares_uppercasesSymbols() throws Exception {
        when(stockAPIClient.fetchData("AAPL", start, end)).thenReturn(List.of(pd("AAPL","2026-04-09",175.0)));
        when(stockAPIClient.fetchData("TSLA", start, end)).thenReturn(List.of(pd("TSLA","2026-04-09",200.0)));
        List<List<PriceData>> result = service.compareShares(List.of("aapl","tsla"), start, end);
        assertEquals(2, result.size());
    }
}
