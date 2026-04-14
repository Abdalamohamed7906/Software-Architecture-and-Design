package com.stockcompare.service;
import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.model.PriceData;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class StockAnalysisServiceTest {
    private IStockService mockStockService;
    private StockAnalysisService analysisService;
    private static final LocalDate START = LocalDate.of(2025,1,1);
    private static final LocalDate END   = LocalDate.of(2025,3,31);
    private List<PriceData> aapl() { return List.of(new PriceData("AAPL",LocalDate.of(2025,1,2),185,190,183,188,1000000L)); }
    private List<PriceData> tsla() { return List.of(new PriceData("TSLA",LocalDate.of(2025,1,2),250,260,248,255,800000L)); }
    @BeforeEach void setUp() { mockStockService = mock(IStockService.class); analysisService = new StockAnalysisService(mockStockService); }
    @Test @DisplayName("TC13 — getPriceDataForGraph returns data for valid symbol")
    void graphData_valid() {
        when(mockStockService.getPriceData("AAPL",START,END)).thenReturn(aapl());
        assertFalse(analysisService.getPriceDataForGraph("AAPL",START,END).isEmpty());
    }
    @Test @DisplayName("TC14 — getPriceDataForGraph throws for blank symbol")
    void graphData_blank() { assertThrows(IllegalArgumentException.class, () -> analysisService.getPriceDataForGraph("",START,END)); }
    @Test @DisplayName("TC15 — getPriceDataForGraph throws for null symbol")
    void graphData_null() { assertThrows(IllegalArgumentException.class, () -> analysisService.getPriceDataForGraph(null,START,END)); }
    @Test @DisplayName("TC16 — compareShares returns two results for two symbols")
    void compare_two() {
        when(mockStockService.getPriceData("AAPL",START,END)).thenReturn(aapl());
        when(mockStockService.getPriceData("TSLA",START,END)).thenReturn(tsla());
        assertEquals(2, analysisService.compareShares(List.of("AAPL","TSLA"),START,END).size());
    }
    @Test @DisplayName("TC17 — compareShares throws for one symbol")
    void compare_one() { assertThrows(IllegalArgumentException.class, () -> analysisService.compareShares(List.of("AAPL"),START,END)); }
    @Test @DisplayName("TC18 — compareShares throws for null list")
    void compare_null() { assertThrows(IllegalArgumentException.class, () -> analysisService.compareShares(null,START,END)); }
    @Test @DisplayName("TC19 — compareShares uppercases symbols")
    void compare_uppercase() {
        when(mockStockService.getPriceData("AAPL",START,END)).thenReturn(aapl());
        when(mockStockService.getPriceData("TSLA",START,END)).thenReturn(tsla());
        analysisService.compareShares(List.of("aapl","tsla"),START,END);
        verify(mockStockService).getPriceData("AAPL",START,END);
    }
}
