package org.itachi.cms.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itachi.cms.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 09:45
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ObjectMapper mapper = new ObjectMapper();

    public AuthorizationInterceptor() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.request = request;
        this.response = response;

        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.UNAUTHORIZED.value());

        try {
            String servletPath = request.getServletPath();
            if (validateIgnorePath(servletPath)) {
                return true;
            }
            return checkSession(result, servletPath);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateApiPath(String servletPath) throws Exception  {
        return validatePath("api.path", servletPath);
    }

    private boolean validateIgnorePath(String servletPath) throws Exception  {
        return validatePath("ignore.path", servletPath);
    }

    private boolean validatePath(String key, String servletPath) throws Exception  {
        String paths = messageSource.getMessage(key, null, null, Locale.getDefault());
        if (paths != null && !paths.isEmpty()) {
            Pattern pattern = Pattern.compile(paths.trim());
            if (pattern.matcher(servletPath).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSession(Map<String, Object> result, String servletPath) throws Exception {
        String url = "login";
        HttpSession session = request.getSession(false);
        boolean apiFlag = validateApiPath(servletPath);
        if (session == null) {
            result.put("code", 502);
            result.put("message", "session不存在，用户没登陆");
            clearCookies();
            if (apiFlag) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().append(mapper.writeValueAsString(result)).flush();
                response.getWriter().close();
            } else  {
                response.sendRedirect(request.getContextPath() + "/" + url);
            }
            return false;
        }

        if (session.getAttribute(Constants.SESSION_KEY) == null) {
            result.put("code", 503);
            result.put("message", "session不存在，用户没登陆");
            clearCookies();
            if (apiFlag) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().append(mapper.writeValueAsString(result)).flush();
                response.getWriter().close();
            } else  {
                response.sendRedirect(url);
            }
            return false;
        }
        return true;
    }

    private void clearCookies() {
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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
