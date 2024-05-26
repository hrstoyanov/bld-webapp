package peruncs.utilities;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.*;
import static peruncs.utilities.ValidationUtils.requireNonEmptyAndNonNull;

/**
 * Download exchange rates from ECB in CSV format.
 * <a href="https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html">CSV</a>
 */
final public class ECBExchangeRates {

    final public static Currency EURO = Currency.getInstance("EUR");
    final public static Currency USD = Currency.getInstance("USD");
    final private static ZoneId CET = ZoneId.of("CET");
    final private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    final private static URI CSV_DATA_FILE = URI.create("https://www.ecb.europa.eu/stats/eurofxref/eurofxref.zip");

    private volatile Map<String, Double> euroMapRate;
    private volatile ZonedDateTime ceLastUpdated;
    private volatile LocalDate asOf;

    public ECBExchangeRates() {
    }

    public ZonedDateTime getLastUpdatedCET() {
        return ceLastUpdated;
    }

    public LocalDate getAsOf() {
        return asOf;
    }

    synchronized ECBExchangeRates forceRefresh() {
        performDownload();
        return this;
    }

    public ECBExchangeRates refreshIfNeeded(Runnable postRefreshAction) {
        Runnable r = null;
        //double-check locking!
        if (needToDownload()) {
            synchronized (this) {
                if (needToDownload()) {
                    performDownload();
                    r = postRefreshAction;
                }
            }
        }
        //Call post processing outside the critical section!
        if (r != null) {
            r.run();
        }
        return this;
    }

    public Map<String, Double> getEuroMapRate() {
        return euroMapRate == null? Collections.emptyMap():euroMapRate;
    }


    public Stream<Currency> getCurrenciesStream(){
        return getEuroMapRate()
                .keySet()
                .stream()
                .sorted()
                .map(Currency::getInstance);
    }

    public int getCurrenciesCount(){
        return getEuroMapRate().size();
    }

    public double convertTo(Currency target, Currency source, double sourceAmount) {
        return convertTo(target.getCurrencyCode(), source.getCurrencyCode(),sourceAmount);
    }

    public double convertTo(String targetCurrencyCode, String sourceCurrencyCode, double sourceAmount) {
        if (Objects.equals(sourceCurrencyCode,targetCurrencyCode) || sourceAmount==0) {
            return sourceAmount;
        }
        double sourceAmountInEuro = convertToEuro(sourceCurrencyCode, sourceAmount);
        if(Objects.equals(EURO.getCurrencyCode(), targetCurrencyCode))
            return sourceAmountInEuro;
        Double rate = getEuroMapRate().get(targetCurrencyCode);
        if (rate == null || rate.isNaN()) {
            return 0;
        }
        return rate * sourceAmountInEuro;
    }

    public double convertToEuro(Currency currency, double monetaryValue) {
        return convertToEuro(currency.getCurrencyCode(), monetaryValue);
    }

    public double convertToEuro(String currencyCode, double monetaryValue) {
        if(Objects.equals(EURO.getCurrencyCode(),currencyCode) || monetaryValue==0.0)
            return monetaryValue;
        Double rate = getEuroMapRate().get(currencyCode);
        if (rate == null || rate.isNaN() || rate == 0.0) {
            return 0.0;
        }
        return monetaryValue / rate;
    }

    private boolean needToDownload() {
        if (ceLastUpdated == null) return true;
        var ceLocalTimeNow = Instant.now().atZone(CET);
        var ce1600today = ceLocalTimeNow.withHour(16);
        var ce1600PrevDay = ce1600today.minusDays(1);
        return ceLastUpdated.isBefore(ce1600PrevDay) || ceLastUpdated.isBefore(ce1600today) && ceLocalTimeNow.isAfter(ce1600today);
    }

    private void performDownload() {
        FileUtils.downloadZip(CSV_DATA_FILE, zipInputStream -> {
            //The zip content has a single file entry (with name "eurofxref.csv")
            var zipEntry = requireNonNull(zipInputStream.getNextEntry());
            byte[] allData = requireNonEmptyAndNonNull(zipInputStream.readAllBytes(), "no data ");
            String allDataString = new String(allData);
            String[] lines = requireNonEmptyAndNonNull(allDataString.split("\n"), "cannot spit lines in CSV content");
            String[] columnNames = requireNonEmptyAndNonNull(lines[0].split("\\s*,\\s*"), "cannot parse columns?");
            String[] values = requireNonEmptyAndNonNull(lines[1].split("\\s*,\\s*"), "cannot parse values?");
            if (columnNames.length != values.length) {
                throw new IllegalStateException(STR."columns: \{columnNames.length}, values: \{values.length}");
            }
            //We the first column it is the date
            asOf = LocalDate.parse(values[0], DATE_FORMATTER);
            Map<String, Double> tempMap = new HashMap<>();
            for (int i = 1; i < columnNames.length; i++) {
                var currency = columnNames[i];
                Double rate = Double.valueOf(values[i]);
                tempMap.put(currency, rate);
            }
            euroMapRate = Map.copyOf(tempMap);
            ceLastUpdated = Instant.now().atZone(CET);
        });
    }

}
