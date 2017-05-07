package org.itachi.cms.util;

import org.apache.el.ExpressionFactoryImpl;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.engine.messageinterpolation.LocalizedMessage;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenIterator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.itachi.cms.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;

import javax.validation.MessageInterpolator;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:07
 */
public final class ThrowableUtil {
    private ThrowableUtil() {
    }

    public static ThrowableUtil getInstance() {
        return instance;
    }

    private static ThrowableUtil instance = new ThrowableUtil();
    private static final String MESSAGE = "message";
    private static final String CODE = "code";

    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableUtil.class);

    public HttpStatus handleThrowable(Map<String, Object> result, Throwable throwable) throws Exception {
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
    private HttpStatus handleHystrixBadRequestException(Map<String, Object> result, HystrixBadRequestException e) throws Exception {
        LOGGER.debug("==========handleHystrixBadRequestException==========");
        String message = "Hystrix Observable Bad Request Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }

    private HttpStatus handleHystrixRuntimeException(Map<String, Object> result, HystrixRuntimeException e) {
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

    private HttpStatus handleHystrixTimeoutException(Map<String, Object> result, HystrixTimeoutException e) {
        LOGGER.debug("==========handleHystrixTimeoutException==========");
        String message = "Hystrix Observable Time Out Exception!";
        if (e.getCause() != null && e.getCause() != e) {
            return handleException(result, e.getCause(), message);
        }
        return handleCommonThrowable(result, e, message);
    }
    */

    public HttpStatus handleException(Map<String, Object> result, Throwable cause, String message) {
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
            /*
            if (cause instanceof HystrixBadRequestException) {
                return handleHystrixBadRequestException(result, (HystrixBadRequestException) throwable);
            }
            if (cause instanceof HystrixRuntimeException) {
                return handleHystrixRuntimeException(result, (HystrixRuntimeException) throwable);
            }
            if (cause instanceof HystrixTimeoutException) {
                return handleHystrixTimeoutException(result, (HystrixTimeoutException) throwable);
            }
            */
            return handleCommonThrowable(result, cause, message);
        } catch (Exception e) {
            return handleCommonThrowable(result, cause, message);
        }
    }

    public HttpStatus handleException(Map<String, Object> result, Throwable cause) {
        return handleException(result, cause, "web server handle data exception!");
    }

    private HttpStatus handleBadSqlGrammarException(Map<String, Object> result, BadSqlGrammarException cause) {
        LOGGER.error("====BadSqlGrammarException: {} \n\n {} \n\n {}", cause, cause.getSql(), cause.getMessage());
        result.put(CODE, (cause.getSQLException() == null || cause.getSQLException().getErrorCode() == 200) ? 417 : cause.getSQLException().getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private HttpStatus handleSQLException(Map<String, Object> result, SQLException cause) {
        LOGGER.error("====SQLException: {} \n\n {} \n\n {}", cause, cause.getErrorCode(), cause.getMessage());
        result.put(CODE, cause.getErrorCode() == 200 ? 417 : cause.getErrorCode());
        result.put(MESSAGE, cause.getMessage());
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private HttpStatus handleBaseException(Map<String, Object> result, BaseException cause) {
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

    public HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause) {
        return handleCommonThrowable(result, cause, "web server handle data exception!");
    }

    public HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause, String message) {
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
            result.put(MESSAGE, cause.getMessage());
        } else if (message != null) {
            result.put(MESSAGE, message);
        }

        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
        String interpolatedMessage = message;
        try {
            interpolatedMessage = interpolateMessage(message, context, locale);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
        }
        return interpolatedMessage;
    }

    /**
     * Runs the message interpolation according to algorithm specified in the Bean Validation specification.
     * <br/>
     * Note:
     * <br/>
     * Look-ups in user bundles is recursive whereas look-ups in default bundle are not!
     *
     * @param message the message to interpolate
     * @param context the context for this interpolation
     * @param locale  the {@code Locale} to use for the resource bundle.
     * @return the interpolated message.
     */
    private String interpolateMessage(String message, MessageInterpolator.Context context, Locale locale)
            throws MessageDescriptorFormatException {
        LocalizedMessage localisedMessage = new LocalizedMessage(message, locale);
        String resolvedMessage = null;

        // if the message is not already in the cache we have to run step 1-3 of the message resolution
        if (resolvedMessage == null) {
            ResourceBundleLocator resourceBundleLocator = null;

            ResourceBundle userResourceBundle = resourceBundleLocator
                    .getResourceBundle(locale);
            ResourceBundle defaultResourceBundle = resourceBundleLocator
                    .getResourceBundle(locale);

            String userBundleResolvedMessage;
            resolvedMessage = message;
            boolean evaluatedDefaultBundleOnce = false;
            do {
                // search the user bundle recursive (step1)
                userBundleResolvedMessage = interpolateBundleMessage(
                        resolvedMessage, userResourceBundle, locale, true
                );

                // exit condition - we have at least tried to validate against the default bundle and there was no
                // further replacements
                if (evaluatedDefaultBundleOnce
                        && !hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) {
                    break;
                }

                // search the default bundle non recursive (step2)
                resolvedMessage = interpolateBundleMessage(
                        userBundleResolvedMessage,
                        defaultResourceBundle,
                        locale,
                        false
                );
                evaluatedDefaultBundleOnce = true;
            } while (true);
        }

        // resolve parameter expressions (step 4)
        List<Token> tokens = null;
        if (tokens == null) {
            TokenCollector tokenCollector = new TokenCollector(resolvedMessage, InterpolationTermType.PARAMETER);
            tokens = tokenCollector.getTokenList();
        }
        resolvedMessage = interpolateExpression(
                new TokenIterator(tokens),
                context,
                locale
        );

        // resolve EL expressions (step 5)
        tokens = null;
        if (tokens == null) {
            TokenCollector tokenCollector = new TokenCollector(resolvedMessage, InterpolationTermType.EL);
            tokens = tokenCollector.getTokenList();
        }
        resolvedMessage = interpolateExpression(
                new TokenIterator(tokens),
                context,
                locale
        );

        // last but not least we have to take care of escaped literals
        resolvedMessage = replaceEscapedLiterals(resolvedMessage);

        return resolvedMessage;
    }

    private String replaceEscapedLiterals(String resolvedMessage) {
        resolvedMessage = resolvedMessage.replace("\\{", "{");
        resolvedMessage = resolvedMessage.replace("\\}", "}");
        resolvedMessage = resolvedMessage.replace("\\\\", "\\");
        resolvedMessage = resolvedMessage.replace("\\$", "$");
        return resolvedMessage;
    }

    private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
        return !origMessage.equals(newMessage);
    }

    private String interpolateBundleMessage(String message, ResourceBundle bundle, Locale locale, boolean recursive)
            throws MessageDescriptorFormatException {
        TokenCollector tokenCollector = new TokenCollector(message, InterpolationTermType.PARAMETER);
        TokenIterator tokenIterator = new TokenIterator(tokenCollector.getTokenList());
        while (tokenIterator.hasMoreInterpolationTerms()) {
            String term = tokenIterator.nextInterpolationTerm();
            String resolvedParameterValue = resolveParameter(
                    term, bundle, locale, recursive
            );
            tokenIterator.replaceCurrentInterpolationTerm(resolvedParameterValue);
        }
        return tokenIterator.getInterpolatedMessage();
    }

    private String interpolateExpression(TokenIterator tokenIterator, MessageInterpolator.Context context, Locale locale)
            throws MessageDescriptorFormatException {
        while (tokenIterator.hasMoreInterpolationTerms()) {
            String term = tokenIterator.nextInterpolationTerm();

            InterpolationTerm expression = new InterpolationTerm(term, locale, new ExpressionFactoryImpl());
            String resolvedExpression = expression.interpolate(context);
            tokenIterator.replaceCurrentInterpolationTerm(resolvedExpression);
        }
        return tokenIterator.getInterpolatedMessage();
    }

    private String resolveParameter(String parameterName, ResourceBundle bundle, Locale locale, boolean recursive)
            throws MessageDescriptorFormatException {
        String parameterValue;
        try {
            if (bundle != null) {
                parameterValue = bundle.getString(removeCurlyBraces(parameterName));
                if (recursive) {
                    parameterValue = interpolateBundleMessage(parameterValue, bundle, locale, recursive);
                }
            } else {
                parameterValue = parameterName;
            }
        } catch (MissingResourceException e) {
            // return parameter itself
            parameterValue = parameterName;
        }
        return parameterValue;
    }

    private String removeCurlyBraces(String parameter) {
        return parameter.substring(1, parameter.length() - 1);
    }
}
