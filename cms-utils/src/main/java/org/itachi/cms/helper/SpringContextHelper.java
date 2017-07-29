package org.itachi.cms.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 16:21
 */
public final class SpringContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return context.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return context.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return context.getBean(requiredType, args);
    }

    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    public static Object getBean(String name, Object... args) throws BeansException {
        return context.getBean(name, args);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return context.isPrototype(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }
}
