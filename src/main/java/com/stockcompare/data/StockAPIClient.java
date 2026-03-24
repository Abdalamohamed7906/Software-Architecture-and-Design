package com.stockcompare.data;

import com.google.gson.*;
import com.stockcompare.domain.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.*;
import java.util.*;

/**
 * StockAPIClient — matches "StockAPI" and "StockAPIClient" in your architecture diagram.
 *
 * Used in sequence diagrams:
 *   SearchService  → fetchSymbols(query)   → StockAPI
 *   PriceService   → fetchData()           → StockAPI
 *   UpdateService  → fetchLatestData()     → StockAPI
 *   CompareService → fetchMultipleData()   → StockAPI
 *
 * SOLID — Single Responsibility: HTTP calls + JSON parsing only.
 * SOLID — Open/Closed: swap API source by replacing this class, not the interfaces.
 */
public class StockAPIClient {

    private final HttpClient http;
    private static final String CHART_URL  = "https://query1.finance.yahoo.com/v8/finance/chart/";
    private static final String SEARCH_URL = "https://query1.finance.yahoo.com/v1/finance/search";

    public StockAPIClient() {
        this.http = HttpClient.newHttpClient();
    }

    // ─── fetchData() ─────────────────────────────────────────────────────────
    // Matches fetchData() in Retrieve Share Price Data sequence diagram
    public List<PriceData> fetchData(String symbol, LocalDate start, LocalDate end)
            throws IOException, InterruptedException {

        long p1 = start.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        long p2 = end.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        String url = CHART_URL + symbol.toUpperCase()
                + "?interval=1d&period1=" + p1 + "&period2=" + p2;

        HttpResponse<String> res = send(url);
        if (res.statusCode() != 200)
            throw new IOException("Yahoo Finance API error: HTTP " + res.statusCode());

        return parseChart(symbol.toUpperCase(), res.body());
    }

    // ─── fetchSymbols() ──────────────────────────────────────────────────────
    // Matches fetchSymbols(query) in Search Share Symbol sequence diagram
    public List<ShareDetail> fetchSymbols(String query)
            throws IOException, InterruptedException {

        String url = SEARCH_URL + "?q=" + query.replace(" ", "%20")
                + "&quotesCount=10&newsCount=0&listsCount=0";

        HttpResponse<String> res = send(url);
        return parseSearch(res.body());
    }

    // ─── fetchLatestData() ───────────────────────────────────────────────────
    // Matches fetchLatestData() in Update Stored Stock Data sequence diagram
    public List<PriceData> fetchLatestData(String symbol)
            throws IOException, InterruptedException {
        return fetchData(symbol, LocalDate.now().minusYears(1), LocalDate.now());
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private HttpResponse<String> send(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .GET().build();
        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private List<PriceData> parseChart(String symbol, String json) {
        List<PriceData> result = new ArrayList<>();
        try {
            JsonObject root    = JsonParser.parseString(json).getAsJsonObject();
            JsonObject chart   = root.getAsJsonObject("chart");
            JsonArray  results = chart.getAsJsonArray("result");
            if (results == null || results.isEmpty()) return result;

            JsonObject data     = results.get(0).getAsJsonObject();
            JsonArray  stamps   = data.getAsJsonArray("timestamp");
            JsonObject quoteObj = data.getAsJsonObject("indicators")
                                      .getAsJsonArray("quote")
                                      .get(0).getAsJsonObject();

            JsonArray opens  = quoteObj.getAsJsonArray("open");
            JsonArray highs  = quoteObj.getAsJsonArray("high");
            JsonArray lows   = quoteObj.getAsJsonArray("low");
            JsonArray closes = quoteObj.getAsJsonArray("close");
            JsonArray vols   = quoteObj.getAsJsonArray("volume");

            for (int i = 0; i < stamps.size(); i++) {
                if (closes.get(i).isJsonNull()) continue;
                LocalDate date = Instant.ofEpochSecond(stamps.get(i).getAsLong())
                        .atZone(ZoneOffset.UTC).toLocalDate();
                result.add(new PriceData(symbol, date,
                        safeDouble(opens,  i),
                        safeDouble(highs,  i),
                        safeDouble(lows,   i),
                        safeDouble(closes, i),
                        safeLong(vols, i)));
            }
        } catch (Exception e) {
            System.err.println("[StockAPIClient] Chart parse error: " + e.getMessage());
        }
        return result;
    }

    private List<ShareDetail> parseSearch(String json) {
        List<ShareDetail> list = new ArrayList<>();
        try {
            JsonObject root   = JsonParser.parseString(json).getAsJsonObject();
            JsonArray  quotes = root.getAsJsonArray("quotes");
            if (quotes == null) return list;
            for (JsonElement el : quotes) {
                JsonObject q = el.getAsJsonObject();
                if (!q.has("symbol")) continue;
                String sym  = q.get("symbol").getAsString();
                String name = q.has("longname")  ? q.get("longname").getAsString()
                            : q.has("shortname") ? q.get("shortname").getAsString()
                            : sym;
                String exch = q.has("exchange") ? q.get("exchange").getAsString() : "";
                list.add(new ShareDetail(sym, name, exch));
            }
        } catch (Exception e) {
            System.err.println("[StockAPIClient] Search parse error: " + e.getMessage());
        }
        return list;
    }

    private double safeDouble(JsonArray arr, int i) {
        return arr.get(i).isJsonNull() ? 0.0 : arr.get(i).getAsDouble();
    }

    private long safeLong(JsonArray arr, int i) {
        return arr.get(i).isJsonNull() ? 0L : arr.get(i).getAsLong();
    }
}
