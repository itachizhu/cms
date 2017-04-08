package org.itachi.cms.provider;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.engine.messageinterpolation.LocalizedMessage;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenIterator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.itachi.cms.util.LocaleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Priority;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.MessageInterpolator;
import javax.validation.ValidationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.*;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 18:12
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationProvider implements ExceptionMapper<ValidationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationProvider.class);

    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    @Context
    private ServletContext servletContext;

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(ValidationException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put(CODE, Response.Status.BAD_REQUEST.getStatusCode());

        if (exception != null && exception.getMessage() != null && exception.getMessage().length() > 0) {
            result.put(MESSAGE, exception.getMessage());
        } else {
            result.put(MESSAGE, "Parameters Validation Failed!");
        }

        try {
            if (exception != null && exception instanceof ConstraintViolationException) {
                Set<ConstraintViolation<?>> messages = ((ConstraintViolationException) exception).getConstraintViolations();
                if (messages != null && !messages.isEmpty()) {
                    Locale locale = LocaleUtil.getLocale(request);
                    List<Map<String, String>> list = new ArrayList<>();
                    for (ConstraintViolation<?> message : messages) {
                        Map<String, String> map = new HashMap<>();
                        map.put("field", StringUtils.substringAfterLast(message.getPropertyPath().toString(), "."));
                        if (message.getMessageTemplate() != null && message.getMessageTemplate().length() > 0) {
                            MessageInterpolatorContext context = new MessageInterpolatorContext(message.getConstraintDescriptor(),
                                    message.getInvalidValue(), message.getRootBeanClass(), new HashMap<String, Object>(0));

                            map.put(MESSAGE, interpolate(message.getMessageTemplate(), context, locale));
                        } else {
                            map.put(MESSAGE, message.getMessage());
                        }
                        list.add(map);
                    }
                    result.put("errors", list);
                }
            }
        } catch (Exception e) {
            LOGGER.error("====ConstraintViolationException error:", e);
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(result)
                .type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .build();
    }

    private String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
        String interpolatedMessage = message;
        try {
            interpolatedMessage = interpolateMessage(message, context, locale);
        } catch (javax.xml.bind.ValidationException e) {
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
            ResourceBundleLocator resourceBundleLocator =
                    WebApplicationContextUtils.getWebApplicationContext(servletContext)
                            .getBean("userResourceBundleLocator", ResourceBundleLocator.class);

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

            InterpolationTerm expression = new InterpolationTerm(term, locale);
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