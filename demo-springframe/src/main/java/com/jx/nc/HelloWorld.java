package com.jx.nc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import java.io.Serializable;

/**
 * Created by fuchaochao on 16/8/5.
 */
public class HelloWorld implements BeanNameAware,BeanFactoryAware,InitializingBean,DisposableBean{
    private String message;

    public HelloWorld(){
        System.out.println("3.HelloWorld struct.......");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void xml_init(){
        //xml开头的表示配置文件配置,这里是bean配置中init-method配置调用
        System.out.println("11.HelloWorld 初始化(init-method)");
    }
    public void xml_destroy(){
        //destroy-method 配置调用
        System.out.println("16.HelloWorld 销毁(destroy-method)");
    }

    public void setBeanName(String s) {
        //属性注入后调用
        System.out.println("6.setBeanName(BeanNameAware) 属性注入后调用, 此时s = " + s);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //setBeanName 后调用
        System.out.println("7.setBeanFactory(BeanFactory) setBeanName后调用");
    }

    public void afterPropertiesSet() throws Exception {
        //processBeforeInitialization(BeanPostProcessor)后调用
        System.out.println("10.afterPropertiesSet(InitializingBean) processBeforeInitialization之后,配置的xml_init之前调用");
    }

    public void destroy() throws Exception {
        System.out.println("15.destroy(DisposableBean) 在processAfterInitialization之后,配置的xml_destroy之前调用");
    }
}
