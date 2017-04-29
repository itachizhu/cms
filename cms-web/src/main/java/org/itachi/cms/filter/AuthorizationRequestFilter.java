package org.itachi.cms.filter;

import org.itachi.cms.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 19:52
 */
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationRequestFilter.class);

    private final String APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    private ContainerRequestContext requestContext;

    @Autowired
    @Qualifier("messageSource1")
    private MessageSource messageSource;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        this.requestContext = requestContext;

        Map<String, Object> result = new HashMap<>();
        result.put("code", Response.Status.UNAUTHORIZED.getStatusCode());

        try {
            String servletPath = request.getServletPath();
            if (validateIgnorePath(servletPath)) {
                return;
            }
            checkSession(result, servletPath);
        } catch (Exception e) {
            clearCookies();
            StringBuilder info = new StringBuilder();
            if (e != null && e.getMessage() != null) {
                info.append(e.getMessage());
            }
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(APPLICATION_JSON_UTF8).entity(result).build());
        }
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

    private void checkSession(Map<String, Object> result, String servletPath) throws Exception {
        String url = "login";
        HttpSession session = request.getSession(false);
        boolean apiFlag = validateApiPath(servletPath);
        if (session == null) {
            result.put("code", 502);
            result.put("message", "session不存在，用户没登陆");
            clearCookies();
            if (apiFlag) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(APPLICATION_JSON_UTF8).entity(result).build());
            } else  {
                response.sendRedirect(request.getContextPath() + "/" + url);
            }
        }

        if (session.getAttribute(Constants.SESSION_KEY) == null) {
            result.put("code", 503);
            result.put("message", "session不存在，用户没登陆");
            clearCookies();
            if (apiFlag) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(APPLICATION_JSON_UTF8).entity(result).build());
            } else  {
                response.sendRedirect(url);
            }
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
}
