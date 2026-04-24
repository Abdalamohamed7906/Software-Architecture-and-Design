package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IExportService;
import com.stockcompare.domain.model.PriceData;
import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExportServiceTest {

    private IExportService exportService;
    private List<PriceData> sampleData;

    @BeforeEach void setUp() {
        exportService = new ExportService();
        sampleData = List.of(
            new PriceData("AAPL", LocalDate.of(2026,4,9),  150.0, 155.0, 148.0, 152.0, 1000000L),
            new PriceData("AAPL", LocalDate.of(2026,4,10), 152.0, 157.0, 150.0, 155.0, 1200000L)
        );
    }

    @Test void generateExportFile_csv_createsFile() {
        File file = exportService.generateExportFile(sampleData, "csv");
        assertTrue(file.exists()); assertTrue(file.getName().endsWith(".csv")); file.delete();
    }

    @Test void generateExportFile_json_createsFile() {
        File file = exportService.generateExportFile(sampleData, "json");
        assertTrue(file.exists()); assertTrue(file.getName().endsWith(".json")); file.delete();
    }

    @Test void generateExportFile_emptyData_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> exportService.generateExportFile(List.of(), "csv"));
    }

    @Test void generateExportFile_nullData_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> exportService.generateExportFile(null, "csv"));
    }

    @Test void generateExportFile_unsupportedFormat_throwsException() {
        assertThrows(Exception.class, () -> exportService.generateExportFile(sampleData, "xml"));
    }

    @Test void exportPriceData_existingFile_returnsTrue() {
        File file = exportService.generateExportFile(sampleData, "csv");
        assertTrue(exportService.exportPriceData(file)); file.delete();
    }

    @Test void exportPriceData_nullFile_returnsFalse() {
        assertFalse(exportService.exportPriceData(null));
    }
}
