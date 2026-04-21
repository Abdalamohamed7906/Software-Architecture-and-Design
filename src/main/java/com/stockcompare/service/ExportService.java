package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IExportService;
import com.stockcompare.domain.model.PriceData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import java.io.*;
import java.util.List;

public class ExportService implements IExportService {

    @Override
    public File generateExportFile(List<PriceData> data, String format) {
        if (data == null || data.isEmpty()) throw new IllegalArgumentException("No data to export.");
        if (format == null || format.isBlank()) throw new IllegalArgumentException("Format required.");
        String filename = "stockcompare_export_" + System.currentTimeMillis() + "." + format.toLowerCase();
        File file = new File(filename);
        try {
            switch (format.toLowerCase()) {
                case "csv"  -> writeCSV(file, data);
                case "json" -> writeJSON(file, data);
                case "xlsx" -> writeXLSX(file, data);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            }
        } catch (IOException e) { throw new RuntimeException("Export failed: " + e.getMessage(), e); }
        System.out.println("[ExportService] Generated: " + file.getAbsolutePath());
        return file;
    }

    @Override
    public boolean exportPriceData(File exportFile) {
        boolean ready = exportFile != null && exportFile.exists();
        System.out.println("[ExportService] Export ready: " + ready);
        return ready;
    }

    private void writeCSV(File file, List<PriceData> data) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Symbol,Date,Open,High,Low,Close,Volume\n");
            for (PriceData p : data)
                fw.write(String.format("%s,%s,%.4f,%.4f,%.4f,%.4f,%d\n",
                    p.getSymbol(), p.getDate(), p.getOpen(), p.getHigh(), p.getLow(), p.getClose(), p.getVolume()));
        }
    }

    private void writeJSON(File file, List<PriceData> data) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("[\n");
            for (int i = 0; i < data.size(); i++) {
                PriceData p = data.get(i);
                fw.write(String.format("  {\"symbol\":\"%s\",\"date\":\"%s\",\"open\":%.4f,\"high\":%.4f,\"low\":%.4f,\"close\":%.4f,\"volume\":%d}%s\n",
                    p.getSymbol(), p.getDate(), p.getOpen(), p.getHigh(), p.getLow(), p.getClose(), p.getVolume(), i < data.size()-1 ? "," : ""));
            }
            fw.write("]\n");
        }
    }

    private void writeXLSX(File file, List<PriceData> data) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet sheet = wb.createSheet("Price Data");
            String[] headers = {"Symbol","Date","Open","High","Low","Close","Volume"};
            Row hRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) hRow.createCell(i).setCellValue(headers[i]);
            for (int r = 0; r < data.size(); r++) {
                PriceData p = data.get(r);
                Row row = sheet.createRow(r + 1);
                row.createCell(0).setCellValue(p.getSymbol());
                row.createCell(1).setCellValue(p.getDate().toString());
                row.createCell(2).setCellValue(p.getOpen());
                row.createCell(3).setCellValue(p.getHigh());
                row.createCell(4).setCellValue(p.getLow());
                row.createCell(5).setCellValue(p.getClose());
                row.createCell(6).setCellValue(p.getVolume());
            }
            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            XSSFSheet chartSheet = wb.createSheet("Price Chart");
            XSSFDrawing drawing = chartSheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0,0,0,0,0,0,18,25);
            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText(data.get(0).getSymbol() + " Close Price History");
            chart.setTitleOverlay(false);
            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.BOTTOM);
            XDDFCategoryAxis catAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            catAxis.setTitle("Date");
            XDDFValueAxis valAxis = chart.createValueAxis(AxisPosition.LEFT);
            valAxis.setTitle("Close Price");
            valAxis.setCrosses(AxisCrosses.AUTO_ZERO);
            XDDFDataSource<String> dates = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, data.size(), 1, 1));
            XDDFNumericalDataSource<Double> closes = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, data.size(), 5, 5));
            XDDFLineChartData lineData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, catAxis, valAxis);
            XDDFLineChartData.Series series = (XDDFLineChartData.Series) lineData.addSeries(dates, closes);
            series.setTitle(data.get(0).getSymbol() + " Close", null);
            series.setSmooth(true);
            series.setMarkerStyle(MarkerStyle.NONE);
            chart.plot(lineData);

            try (FileOutputStream fos = new FileOutputStream(file)) { wb.write(fos); }
        }
    }
}
