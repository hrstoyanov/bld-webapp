package peruncs.utilities;

import java.util.Currency;

import static java.lang.Long.compare;
import static java.util.FormatProcessor.FMT;
import static peruncs.utilities.ValidationUtils.onEmptyOrNull;

public record MonetaryAmount(Currency currency, long monetaryAmount) implements Comparable<MonetaryAmount>, FormattedMonetaryAmount {

    static public double asDecimal(long monetaryAmount) {
        return monetaryAmount / 100.00;
    }

    static public String formatted(long monetaryAmount, Currency currency) {
        return FMT."%,.2f\{monetaryAmount / 100.00} \{onEmptyOrNull(currency.getCurrencyCode(), "???")}";
    }

    static public String formatted(long monetaryAmount) {
        return FMT."%,.2f\{monetaryAmount / 100.00}";
    }

    @Override
    public int compareTo(MonetaryAmount m) {
        return compare(monetaryAmount, m.monetaryAmount);
    }

    @Override
    public String formatted() {
        return formatted(monetaryAmount, currency);
    }

    @Override
    public String formattedDecimal(){return formatted(monetaryAmount);}

    public MonetaryAmount convertTo(Currency targetCurrency, ECBExchangeRates ecbExchangeRates) {
        return new MonetaryAmount(targetCurrency, Math.round(ecbExchangeRates.convertTo(targetCurrency, currency, monetaryAmount)));
    }

}
