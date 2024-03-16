package com.zzk.spring.core.beanfactory;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author zhangzhongkun02
 * @date 2023/5/2 3:20 PM
 */
class BeanFactoryDemo {

    @Test
    void createBeanFactoryAndCheckEmployeeBean() {
        Resource res = new ClassPathResource("beanfactory-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);

        assertTrue(factory.isSingleton("employee"));
        assertTrue(factory.getBean("employee") instanceof Employee);
        assertTrue(factory.isTypeMatch("employee", Employee.class));
        assertTrue(factory.getAliases("employee").length > 0);

        Employee emp = (Employee) factory.getBean("employee");
        assertEquals("Java", emp.getName());
    }

    @Data
    public static class Employee {
        private String name;
        private int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}