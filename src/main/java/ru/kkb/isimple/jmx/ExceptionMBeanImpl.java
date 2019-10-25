package ru.kkb.isimple.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ManagedResource
public class ExceptionMBeanImpl implements ExceptionMBean {

    private ConcurrentHashMap<String, Integer> exceptions = new ConcurrentHashMap<>();

    @Override
    public Map<String, Integer> getExceptions() {
        return exceptions;
    }

    @Override
    public void addException(Exception exception) {
       String className = exception.getClass().getName();
       if (exceptions.containsKey(className)) {
           exceptions.put(className, exceptions.get(className) + 1);
       } else {
           exceptions.put(className, 1);
       }
    }
}
