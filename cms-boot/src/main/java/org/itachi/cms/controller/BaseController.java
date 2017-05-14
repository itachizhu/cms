package org.itachi.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyo on 2017/5/6.
 */
public abstract class BaseController {
    private static final String ENCODE_UTF8 = "UTF-8";

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpServletRequest request;

    protected Map<String, String> getQueryParameters() throws Exception {
        return decodeQuery(request.getQueryString(), false, true);
    }

    private void decodeQueryParam(final Map<String, String> params, final String param,
                                         final boolean decodeNames, final boolean decodeValues) {
        try {
            final int equals = param.indexOf('=');
            if (equals > 0) {
                params.put((decodeNames) ? URLDecoder.decode(param.substring(0, equals), ENCODE_UTF8) : param.substring(0, equals),
                        (decodeValues) ? URLDecoder.decode(param.substring(equals + 1), ENCODE_UTF8) : param.substring(equals + 1));
            } else if (equals == 0) {
                // no key declared, ignore
            } else if (param.length() > 0) {
                params.put((decodeNames) ? URLDecoder.decode(param, ENCODE_UTF8) : param, "");
            }
        } catch (final UnsupportedEncodingException ex) {
            // This should never occur
            throw new IllegalArgumentException(ex);
        }
    }

    private Map<String, String> decodeQuery(final String q, final boolean decodeNames,
                                                             final boolean decodeValues) {
        final Map<String, String> queryParameters = new HashMap<>();

        if (q == null || q.length() == 0) {
            return queryParameters;
        }

        int s = 0;
        do {
            final int e = q.indexOf('&', s);

            if (e == -1) {
                decodeQueryParam(queryParameters, q.substring(s), decodeNames, decodeValues);
            } else if (e > s) {
                decodeQueryParam(queryParameters, q.substring(s, e), decodeNames, decodeValues);
            }
            s = e + 1;
        } while (s > 0 && s < q.length());

        return queryParameters;
    }

    private Map<String, String> decodeQuery(final String q, final boolean decodeValues) {
        return decodeQuery(q, true, decodeValues);
    }

    private Map<String, String> decodeQuery(final String q) {
        return decodeQuery(q, true, true);
    }
}
