package org.itachi.cms.util;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.itachi.cms.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;


/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:07
 */
public final class ThrowableUtil {
    private ThrowableUtil() {
    }

    private static final String MESSAGE = "message";
    private static final String CODE = "code";

    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableUtil.class);

    public static Response.Status handleThrowable(Map<String, Object> result, Throwable throwable) throws Exception {
        LOGGER.debug("==========handleThrowable==========");
        if (throwable instanceof HystrixBadRequestException) {
            return handleHystrixBadRequestException(result, (HystrixBadRequestException) throwable);
        }
        if (throwable instanceof HystrixRuntimeException) {
            return handleHystrixRuntimeException(result, (HystrixRuntimeException) throwable);
        }
        if (throwable instanceof HystrixTimeoutException) {
            return handleHystrixTimeoutException(result, (HystrixTimeoutException) throwable);
        }
        return handleCommonThrowable(result, throwable);
    }

    private static Response.Status handleHystrixBadRequestException(Map<String, Object> result, HystrixBadRequestException e) throws Exception {
        LOGGER.debug("==========handleHystrixBadRequestException==========");
        String message = "Hystrix Observable Bad Request Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }

    private static Response.Status handleHystrixRuntimeException(Map<String, Object> result, HystrixRuntimeException e) {
        LOGGER.debug("==========handleHystrixRuntimeException==========");
        String message = "Hystrix Observable Runtime Exception!";
        if (e.getFallbackException() != null && e.getFallbackException() != e) {
            return handleException(result, e.getFallbackException(), message);
        }
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }

    private static Response.Status handleHystrixTimeoutException(Map<String, Object> result, HystrixTimeoutException e) {
        LOGGER.debug("==========handleHystrixTimeoutException==========");
        String message = "Hystrix Observable Time Out Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }

    public static Response.Status handleException(Map<String, Object> result, Throwable cause, String message) {
        try {
            if (cause instanceof BaseException) {
                return handleBaseException(result, (BaseException) cause);
            }
            if (cause instanceof SQLException) {
                return handleSQLException(result, (SQLException) cause);
            }
            if (cause instanceof BadSqlGrammarException) {
                return handleBadSqlGrammarException(result, (BadSqlGrammarException) cause);
            }
            return handleCommonThrowable(result, cause, message);
        } catch (Exception e) {
            return handleCommonThrowable(result, cause, message);
        }
    }

    private static Response.Status handleBadSqlGrammarException(Map<String, Object> result, BadSqlGrammarException cause) {
        LOGGER.error("====BadSqlGrammarException: {} \n\n {} \n\n {}", cause, cause.getSql(), cause.getMessage());
        result.put(CODE, (cause.getSQLException() == null || cause.getSQLException().getErrorCode() == 200) ? 417 : cause.getSQLException().getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return Response.Status.SERVICE_UNAVAILABLE;
    }

    private static Response.Status handleSQLException(Map<String, Object> result, SQLException cause) {
        LOGGER.error("====SQLException: {} \n\n {} \n\n {}", cause, cause.getErrorCode(), cause.getMessage());
        result.put(CODE, cause.getErrorCode() == 200 ? 417 : cause.getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return Response.Status.SERVICE_UNAVAILABLE;
    }

    private static Response.Status handleBaseException(Map<String, Object> result, BaseException cause) {
        Response.Status status = Response.Status.SERVICE_UNAVAILABLE;
        if (cause.getStatus() != null) {
            status = Response.Status.fromStatusCode(cause.getStatus());
        }
        if (status == null) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }
        result.put(CODE, cause.getCode());
        result.put(MESSAGE, cause.getMessage());
        return status;
    }

    public static Response.Status handleCommonThrowable(Map<String, Object> result, Throwable cause) {
        return handleCommonThrowable(result, cause, "web server handle data exception!");
    }

    public static Response.Status handleCommonThrowable(Map<String, Object> result, Throwable cause, String message) {
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
            result.put(MESSAGE, cause.getMessage());
        } else if (message != null) {
            result.put(MESSAGE, message);
        }

        return Response.Status.SERVICE_UNAVAILABLE;
    }
}
