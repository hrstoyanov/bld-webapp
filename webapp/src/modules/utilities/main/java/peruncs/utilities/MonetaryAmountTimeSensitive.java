package peruncs.utilities;


import java.util.Currency;

import static java.lang.Long.compare;

public record MonetaryAmountTimeSensitive<R extends TemporalRange<?>>(Currency currency, R validForRange,long monetaryAmount)
        implements Comparable<MonetaryAmountTimeSensitive<R>>, FormattedMonetaryAmount {

    @Override
    public int compareTo(MonetaryAmountTimeSensitive<R> m) {
        return compare(monetaryAmount, m.monetaryAmount);
    }

    public double asDecimal(){
        return MonetaryAmount.asDecimal(monetaryAmount);
    }

    @Override
    public String formatted() {
        return MonetaryAmount.formatted(monetaryAmount, currency);
    }

    @Override
    public String formattedDecimal(){return MonetaryAmount.formatted(monetaryAmount);}

    public MonetaryAmountTimeSensitive<R> convertTo(Currency targetCurrency, ECBExchangeRates ecbExchangeRates) {
        return new MonetaryAmountTimeSensitive<>(targetCurrency, validForRange, Math.round(ecbExchangeRates.convertTo(targetCurrency, currency, monetaryAmount)));
    }

}
