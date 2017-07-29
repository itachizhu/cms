package org.itachi.cms.config;

import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.itachi.cms.helper.SpringContextHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 10:09
 */
@Configuration
public class MessageSourceConfig {
    private MessageSource validationMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ResourceBundleLocator resourceBundleLocator() {
        return new MessageSourceResourceBundleLocator(validationMessageSource());
    }

    @Bean
    public SpringContextHelper springContextHelper() {
        return new SpringContextHelper();
    }
}

