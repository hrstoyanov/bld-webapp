package peruncs.utilities;

sealed public interface FormattedMonetaryAmount
        permits MonetaryAmount, MonetaryAmountRange, MonetaryAmountTimeSensitive {
        String formatted();
        String formattedDecimal();
}
