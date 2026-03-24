package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IExportService;
import com.stockcompare.domain.model.PriceData;

import java.io.*;
import java.util.List;

/**
 * ExportService — implements IExportService.
 *
 * Matches Export Price Data sequence diagram:
 *   UI → exportData()     → ExportService
 *   ExportService → generateFile() → FileGenerator[ExportService]
 *   FileGenerator → file → ExportService
 *   ExportService → exportReady() → UI
 *   UI → downloadFile() → User
 *
 * Supports CSV and JSON formats.
 * SOLID — Single Responsibility: file generation and export only.
 * SOLID — Open/Closed: add new formats (e.g. XML) without changing interface.
 */
public class ExportService implements IExportService {

    // generateFile() → FileGenerator in Export sequence diagram
    @Override
    public File generateExportFile(List<PriceData> data, String format) {
        if (data == null || data.isEmpty())
            throw new IllegalArgumentException("No data to export.");
        if (format == null || format.isBlank())
            throw new IllegalArgumentException("Export format required (csv or json).");

        String filename = "stockcompare_export_"
                + System.currentTimeMillis() + "." + format.toLowerCase();
        File file = new File(filename);

        try (FileWriter fw = new FileWriter(file)) {
            switch (format.toLowerCase()) {
                case "csv"  -> writeCSV(fw, data);
                case "json" -> writeJSON(fw, data);
                default     -> throw new IllegalArgumentException(
                        "Unsupported format: " + format + " (use csv or json)");
            }
        } catch (IOException e) {
            throw new RuntimeException("Export failed: " + e.getMessage(), e);
        }

        System.out.println("[ExportService] Generated: " + file.getAbsolutePath());
        return file;
    }

    // exportReady() in Export sequence diagram
    @Override
    public boolean exportPriceData(File exportFile) {
        boolean ready = exportFile != null && exportFile.exists();
        System.out.println("[ExportService] Export ready: " + ready);
        return ready;
    }

    // ── Private file generators ───────────────────────────────────────────────

    private void writeCSV(FileWriter fw, List<PriceData> data) throws IOException {
        fw.write("Symbol,Date,Open,High,Low,Close,Volume\n");
        for (PriceData p : data) {
            fw.write(String.format("%s,%s,%.4f,%.4f,%.4f,%.4f,%d\n",
                    p.getSymbol(), p.getDate(),
                    p.getOpen(), p.getHigh(), p.getLow(),
                    p.getClose(), p.getVolume()));
        }
    }

    private void writeJSON(FileWriter fw, List<PriceData> data) throws IOException {
        fw.write("[\n");
        for (int i = 0; i < data.size(); i++) {
            PriceData p = data.get(i);
            fw.write(String.format(
                    "  {\"symbol\":\"%s\",\"date\":\"%s\","
                    + "\"open\":%.4f,\"high\":%.4f,\"low\":%.4f,"
                    + "\"close\":%.4f,\"volume\":%d}%s\n",
                    p.getSymbol(), p.getDate(),
                    p.getOpen(), p.getHigh(), p.getLow(),
                    p.getClose(), p.getVolume(),
                    i < data.size() - 1 ? "," : ""));
        }
        fw.write("]\n");
    }
}
