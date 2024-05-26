package peruncs.utilities;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Collection;
import java.util.function.Supplier;

public interface ValidationUtils {

    static String formatNull(String s){
        return isEmptyOrNull(s)?"":s;
    }

    static boolean isEmptyOrNull(Object o){
            return o == null
                    || (o instanceof String s && s.isBlank())
                    || (o instanceof Collection<?> c && c.isEmpty())
                    || (o.getClass().isArray() && Array.getLength(o) == 0);
    }

    static <T> T onEmptyOrNull(T o, T alternativeValue) {
        return isEmptyOrNull(o)?alternativeValue:o;
    }

    static boolean isNotEmptyOrNull(Object o) {
        return !isEmptyOrNull(o);
    }

    static <T> T requireNonEmptyAndNonNull(T o, Supplier<String> message) {
        if (isEmptyOrNull(o)) throw new IllegalArgumentException(getMessage(message));
        return o;
    }

    static<T> T requireNonEmptyAndNonNull(T o, String message) {
        if (isEmptyOrNull(o)) throw new IllegalArgumentException(message);
        return o;
    }

    static <T> T requireNonEmptyAndNonNull(T o) {
        return requireNonEmptyAndNonNull(o, (String)null);
    }

    static long requirePositive(long l, Supplier<String> message) {
        if (l <= 0) throw new IllegalArgumentException(STR."Illegal positive value:\{l} \{getMessage(message)}");
        return l;
    }

    static long requirePositive(long l, String message) {
        if (l <= 0) throw new IllegalArgumentException(STR."Illegal positive value:\{l} \{message}");
        return l;
    }

    static long requirePositive(long l) {
        return requirePositive(l, (String)null);
    }

    static long requirePositiveOrZero(long l, Supplier<String> message) {
        if (l < 0) throw new IllegalArgumentException(STR."Illegal positive value:\{l} \{getMessage(message)}");
        return l;
    }

    static long requirePositiveOrZero(long l, String message) {
        if (l < 0) throw new IllegalArgumentException(STR."Illegal positive value:\{l} \{message}");
        return l;
    }

    static long requirePositiveOrZero(long l) {
        return requirePositiveOrZero(l, (String)null);
    }

    static String validateURI(String uri) {
        requireNonEmptyAndNonNull(uri);
        URI.create(uri);
        return uri;
    }

    static String validateURI(String uri, Supplier<String> message) {
        try {
            requireNonEmptyAndNonNull(uri, getMessage(message));
            new URI(uri);
            return uri;
        } catch (Exception e) {
            throw new IllegalArgumentException(STR."Invalid uri: \{uri}  \{getMessage(message)}");
        }
    }

    static String validateURI(String uri, String message) {
        try {
            requireNonEmptyAndNonNull(uri, message);
            new URI(uri);
            return uri;
        } catch (Exception e) {
            throw new IllegalArgumentException(STR."Invalid uri: \{uri}  \{message}");
        }
    }

    static String getMessage(Supplier<String> message) {
        return message != null ? message.get() : null;
    }

}
