package org.itachi.cms.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/5/7.
 * User: itachi
 * Date: 2017/5/7
 * Time: 15:46
 */
public final class Utils {
    private static final String ENCODE_UTF8 = "UTF-8";

    private Utils() {
    }

    public static void cleanSessions(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                if (names != null) {
                    while (names.hasMoreElements()) {
                        String attribute = names.nextElement();
                        session.removeAttribute(attribute);
                    }
                }
                session.invalidate();
            }
        } catch (Exception e) {
        }
    }

    public static Map<String, String> getQueryParameters(HttpServletRequest request) throws Exception {
        return decodeQuery(request.getQueryString(), false, true);
    }

    private static void decodeQueryParam(final Map<String, String> params, final String param,
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

    private static Map<String, String> decodeQuery(final String q, final boolean decodeNames,
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

    private static Map<String, String> decodeQuery(final String q, final boolean decodeValues) {
        return decodeQuery(q, true, decodeValues);
    }

    private static Map<String, String> decodeQuery(final String q) {
        return decodeQuery(q, true, true);
    }
}

