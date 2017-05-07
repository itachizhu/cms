package org.itachi.cms.handler;

import org.itachi.cms.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 10:24
 */
@EnableWebMvc
@ControllerAdvice
@Controller
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(Throwable throwable, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put(CODE, 999);
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        try {
            status = handleThrowable(result, throwable);
        } catch (Exception e) {
            LOGGER.error("=====ExceptionMapperProvider exception:", e);
            result.put(MESSAGE, "exception mapper provider have exception!");
        }
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
    }

    private HttpStatus handleThrowable(Map<String, Object> result, Throwable throwable) throws Exception {
        if (throwable != null && throwable.getCause() != null && throwable.getCause() != throwable) {
            return ThrowableUtil.getInstance().handleException(result, throwable.getCause());
        }
        return ThrowableUtil.getInstance().handleException(result, throwable);
    }
}
