package ru.kkb.isimple.jmx;

import org.springframework.stereotype.Service;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;

@Service
public class JmxAgent {

    public JmxAgent() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        try {
            StandardMBean mBean = new StandardMBean(new ExceptionMBeanImpl(), ExceptionMBean.class);
            mBeanServer.registerMBean(mBean, new ObjectName(ExceptionMBean.OBJECT_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
