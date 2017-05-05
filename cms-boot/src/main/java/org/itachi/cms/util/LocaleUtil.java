package org.itachi.cms.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:48
 */
public final class LocaleUtil {
    private LocaleUtil() {}

    public static Locale getLocale(HttpServletRequest request) {
        try {
            if (request != null) {
                String locale = request.getParameter("locale");
                if (locale != null && locale.length() > 0) {
                    String[] locales = locale.split("_");
                    if (locales.length < 2) {
                        return new Locale(locale);
                    } else {
                        return new Locale(locales[0], locales[1]);
                    }
                } else if (request.getLocale() != null) {
                    return request.getLocale();
                }
            }
        } catch (Exception e) {


        }
        return Locale.getDefault();
    }
}
