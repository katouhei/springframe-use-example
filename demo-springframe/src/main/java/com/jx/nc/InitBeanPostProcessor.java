package com.jx.nc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by fuchaochao on 16/8/12.
 */
public class InitBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("8.postProcessBeforeInitialization(BeanPostProcessor), bean = " + o.getClass());
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("12.postProcessAfterInitialization(BeanPostProcessor), bean = " + o.getClass());
        return o;
    }
}
