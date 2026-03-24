package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.PriceData;
import java.io.File;
import java.util.List;

/**
 * IExportService — business interface for UC12.
 *
 * Matches Export Price Data sequence:
 *   UI → exportData() → ExportService → generateFile() → FileGenerator
 *                                     ← file ← FileGenerator
 *                     → exportReady() → UI → downloadFile() → User
 *
 * SOLID — Single Responsibility: file generation and export only.
 */
public interface IExportService {
    File    generateExportFile(List<PriceData> data, String format);
    boolean exportPriceData(File exportFile);
}
