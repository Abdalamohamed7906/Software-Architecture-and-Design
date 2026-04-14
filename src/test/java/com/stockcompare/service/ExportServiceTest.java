package com.stockcompare.service;
import com.stockcompare.domain.model.PriceData;
import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class ExportServiceTest {
    private ExportService exportService;
    private List<PriceData> data() { return List.of(new PriceData("AAPL",LocalDate.of(2025,1,2),185,190,183,188,1000000L)); }
    @BeforeEach void setUp() { exportService = new ExportService(); }
    @Test @DisplayName("TC20 — generateExportFile creates CSV")
    void csv() { File f = exportService.generateExportFile(data(),"csv"); assertTrue(f.exists()); f.delete(); }
    @Test @DisplayName("TC21 — generateExportFile creates JSON")
    void json() { File f = exportService.generateExportFile(data(),"json"); assertTrue(f.exists()); f.delete(); }
    @Test @DisplayName("TC22 — throws for empty data")
    void emptyData() { assertThrows(IllegalArgumentException.class, () -> exportService.generateExportFile(List.of(),"csv")); }
    @Test @DisplayName("TC23 — throws for null data")
    void nullData() { assertThrows(IllegalArgumentException.class, () -> exportService.generateExportFile(null,"csv")); }
    @Test @DisplayName("TC24 — throws for unsupported format")
    void badFormat() { assertThrows(RuntimeException.class, () -> exportService.generateExportFile(data(),"xml")); }
    @Test @DisplayName("TC25 — exportPriceData returns true for valid file")
    void exportValid() { File f = exportService.generateExportFile(data(),"csv"); assertTrue(exportService.exportPriceData(f)); f.delete(); }
    @Test @DisplayName("TC26 — exportPriceData returns false for null")
    void exportNull() { assertFalse(exportService.exportPriceData(null)); }
}
