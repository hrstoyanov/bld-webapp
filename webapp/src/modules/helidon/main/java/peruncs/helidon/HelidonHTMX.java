package peruncs.helidon;

import io.helidon.http.HeaderName;
import io.helidon.http.HeaderNames;
import peruncs.utilities.HTMX;


public interface HelidonHTMX {
    interface RequestHeaders{
        HeaderName HX_BOOSTED = HeaderNames.create(HTMX.RequestHeaders.HX_BOOSTED);
        HeaderName HX_CURRENT_URL = HeaderNames.create(HTMX.RequestHeaders.HX_CURRENT_URL);
        HeaderName HX_HISTORY_RESTORE_REQUEST = HeaderNames.create(HTMX.RequestHeaders.HX_HISTORY_RESTORE_REQUEST);
        HeaderName HX_PROMPT = HeaderNames.create(HTMX.RequestHeaders.HX_PROMPT);
        HeaderName HX_REQUEST = HeaderNames.create(HTMX.RequestHeaders.HX_REQUEST);
        HeaderName HX_TARGET = HeaderNames.create(HTMX.RequestHeaders.HX_TARGET);
        HeaderName HX_TRIGGER_NAME = HeaderNames.create(HTMX.RequestHeaders.HX_TRIGGER_NAME);
        HeaderName HX_TRIGGER = HeaderNames.create(HTMX.RequestHeaders.HX_TRIGGER);
    }

    interface ResponseHeaders{
        HeaderName HX_LOCATION = HeaderNames.create(HTMX.ResponseHeaders.HX_LOCATION);
        HeaderName HX_PUSH_URL = HeaderNames.create(HTMX.ResponseHeaders.HX_PUSH_URL);
        HeaderName HX_REDIRECT = HeaderNames.create(HTMX.ResponseHeaders.HX_REDIRECT);
        HeaderName HX_REFRESH = HeaderNames.create(HTMX.ResponseHeaders.HX_REFRESH);
        HeaderName HX_REPLACE_URL = HeaderNames.create(HTMX.ResponseHeaders.HX_REPLACE_URL);
        HeaderName HX_RESWAP = HeaderNames.create(HTMX.ResponseHeaders.HX_RESWAP);
        HeaderName HX_RETARGET = HeaderNames.create(HTMX.ResponseHeaders.HX_RETARGET);
        HeaderName HX_RESELECT = HeaderNames.create(HTMX.ResponseHeaders.HX_RESELECT);
        HeaderName HX_TRIGGER = HeaderNames.create(HTMX.ResponseHeaders.HX_TRIGGER);
        HeaderName HX_TRIGGER_AFTER_SETTLE = HeaderNames.create(HTMX.ResponseHeaders.HX_TRIGGER_AFTER_SETTLE);
        HeaderName HX_TRIGGER_AFTER_SWAP = HeaderNames.create(HTMX.ResponseHeaders.HX_TRIGGER_AFTER_SWAP);
    }
}
