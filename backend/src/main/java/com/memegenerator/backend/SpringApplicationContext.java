package com.memegenerator.backend;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    /**
     * @param context
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }


    /**
     * @param beanName
     * @return Object
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}