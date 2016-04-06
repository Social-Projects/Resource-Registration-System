package org.registrator.community.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/** Holds Spring's ApplicationContext to get Bean by type
 *
 */

@Component
public class SpringApplicationContext implements ApplicationContextAware, MessageSourceAware {
    private static ApplicationContext CONTEXT;
    private static MessageSource MESSAGE_SOURCE;

    @Override
    public void setApplicationContext(final ApplicationContext context)
            throws BeansException {
        CONTEXT = context;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        MESSAGE_SOURCE = messageSource;
    }

    public static MessageSource getMessageSource() {
        return MESSAGE_SOURCE;
    }

    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }
}