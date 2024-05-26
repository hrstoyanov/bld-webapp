package peruncs.utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

import static peruncs.utilities.ValidationUtils.isEmptyOrNull;

public interface TemporalRange<T extends Temporal> extends Range<T> {

    DateTimeFormatter MM_DD_LOCAL_DATE = DateTimeFormatter.ofPattern("MM d, yy");

    static  String formatDate(Instant i, DateTimeFormatter dateTimeFormatter){
        return formatDate(dateTimeFormatter, i);
    }

    static  String formatDate(DateTimeFormatter dateFormatter, Instant i){
        return i==null?"":dateFormatter.format(i.atZone(ZoneId.systemDefault()).toLocalDate());
    }

//    static String formatDateRange(DateTimeFormatter dateFormatter,TemporalRange<?> range){
//        return formatDateRange(dateFormatter, range);
//    }

    static String formatDateRange(DateTimeFormatter dateFormatter, TemporalRange<?> range){
        if(isEmptyOrNull(range)) return "";
        return STR."\{dateFormatter.format(range.start())} - \{dateFormatter.format(range.end())}";
    }
}

