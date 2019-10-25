package ru.kkb.isimple.jmx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.access.MBeanProxyFactoryBean;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

@Configuration
public class MBeanConfiguration {

    @Bean
    public MBeanProxyFactoryBean exceptionMBeanFactory() throws MalformedObjectNameException {
        MBeanProxyFactoryBean proxyFactory = new MBeanProxyFactoryBean();
        proxyFactory.setObjectName(new ObjectName(ExceptionMBean.OBJECT_NAME));
        proxyFactory.setProxyInterface(ExceptionMBean.class);
        proxyFactory.afterPropertiesSet();
        return proxyFactory;
    }

    @Bean
    public ExceptionMBean exceptionMBean(MBeanProxyFactoryBean exceptionMBeanFactory) {
        return (ExceptionMBean) exceptionMBeanFactory.getObject();
    }
}
