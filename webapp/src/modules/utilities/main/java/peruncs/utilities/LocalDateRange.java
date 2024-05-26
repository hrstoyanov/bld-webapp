package peruncs.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public record LocalDateRange(LocalDate start, LocalDate end) implements TemporalRange<LocalDate> {

    public LocalDateRange {
        Range.validate(start, end);
    }

    public static int asInt(LocalDate localDate) {
        return Integer.parseInt(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        return o1.compareTo(o2);
    }

    @Override
    public LocalDateRange of(LocalDate s, LocalDate e) {
        return new LocalDateRange(s, e);
    }

    public int startAsInt() {
        return asInt(start);
    }

    public int endAsInt() {
        return asInt(end);
    }

}
