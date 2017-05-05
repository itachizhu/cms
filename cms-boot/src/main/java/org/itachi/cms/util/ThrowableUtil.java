package org.itachi.cms.util;

import org.itachi.cms.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;

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

    public static HttpStatus handleThrowable(Map<String, Object> result, Throwable throwable) throws Exception {
        LOGGER.debug("==========handleThrowable==========");
        /*
        if (throwable instanceof HystrixBadRequestException) {
            return handleHystrixBadRequestException(result, (HystrixBadRequestException) throwable);
        }
        if (throwable instanceof HystrixRuntimeException) {
            return handleHystrixRuntimeException(result, (HystrixRuntimeException) throwable);
        }
        if (throwable instanceof HystrixTimeoutException) {
            return handleHystrixTimeoutException(result, (HystrixTimeoutException) throwable);
        }
        */
        return handleCommonThrowable(result, throwable);
    }

    /*
    private static HttpStatus handleHystrixBadRequestException(Map<String, Object> result, HystrixBadRequestException e) throws Exception {
        LOGGER.debug("==========handleHystrixBadRequestException==========");
        String message = "Hystrix Observable Bad Request Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }

    private static HttpStatus handleHystrixRuntimeException(Map<String, Object> result, HystrixRuntimeException e) {
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

    private static HttpStatus handleHystrixTimeoutException(Map<String, Object> result, HystrixTimeoutException e) {
        LOGGER.debug("==========handleHystrixTimeoutException==========");
        String message = "Hystrix Observable Time Out Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }
    */

    public static HttpStatus handleException(Map<String, Object> result, Throwable cause, String message) {
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

    private static HttpStatus handleBadSqlGrammarException(Map<String, Object> result, BadSqlGrammarException cause) {
        LOGGER.error("====BadSqlGrammarException: {} \n\n {} \n\n {}", cause, cause.getSql(), cause.getMessage());
        result.put(CODE, (cause.getSQLException() == null || cause.getSQLException().getErrorCode() == 200) ? 417 : cause.getSQLException().getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private static HttpStatus handleSQLException(Map<String, Object> result, SQLException cause) {
        LOGGER.error("====SQLException: {} \n\n {} \n\n {}", cause, cause.getErrorCode(), cause.getMessage());
        result.put(CODE, cause.getErrorCode() == 200 ? 417 : cause.getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private static HttpStatus handleBaseException(Map<String, Object> result, BaseException cause) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        if (cause.getStatus() != null) {
            try {
                status = HttpStatus.valueOf(cause.getStatus().intValue());
            } catch (Exception e) {
                status = HttpStatus.SERVICE_UNAVAILABLE;
            }
        }
        result.put(CODE, cause.getCode());
        result.put(MESSAGE, cause.getMessage());
        return status;
    }

    public static HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause) {
        return handleCommonThrowable(result, cause, "web server handle data exception!");
    }

    public static HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause, String message) {
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
            result.put(MESSAGE, cause.getMessage());
        } else if (message != null) {
            result.put(MESSAGE, message);
        }

        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
