package ru.kkb.isimple.jmx;

import java.util.Map;

public interface ExceptionMBean {

    String OBJECT_NAME = "JMX:name=" + ExceptionMBeanImpl.class.getSimpleName();

    Map<String, Integer> getExceptions();

    void addException(Exception exception);
}
