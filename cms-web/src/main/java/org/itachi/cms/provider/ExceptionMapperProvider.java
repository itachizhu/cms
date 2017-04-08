package org.itachi.cms.provider;

import org.itachi.cms.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:56
 */
@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperProvider.class);

    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Throwable exception) {
        Map<String, Object> result = new HashMap<>();
        Response.Status status = Response.Status.SERVICE_UNAVAILABLE;
        result.put(CODE, 999);
        try {
            status = handleThrowable(result, exception);
        } catch (Exception e) {
            LOGGER.error("=====ExceptionMapperProvider exception:", e);
            result.put(MESSAGE, "exception mapper provider have exception!");
        }
        return Response.status(status).entity(result).type(MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
    }

    private Response.Status handleThrowable(Map<String, Object> result, Throwable throwable) throws Exception {
        if (throwable != null && throwable.getCause() != null && throwable.getCause() != throwable) {
            return ThrowableUtil.handleThrowable(result, throwable.getCause());
        }
        return ThrowableUtil.handleCommonThrowable(result, throwable);
    }
}
