package com.zzk.spring.core.di.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.*;

@Configuration
public class CollectionConfig {

    @Bean
    public CollectionsBean getCollectionsBean() {
        return new CollectionsBean(new HashSet<>(Arrays.asList("John", "Adam", "Harry")));
    }

    @Bean
    public List<String> nameList(){
        return Arrays.asList("John", "Adam", "Harry", null);
    }

    @Bean
    public Map<Integer, String> nameMap(){
        Map<Integer, String>  nameMap = new HashMap<>();
        nameMap.put(1, "John");
        nameMap.put(2, "Adam");
        nameMap.put(3, "Harry");
        return nameMap;
    }

    @Bean
    @Qualifier("CollectionsBean")
    @Order(2)
    public BaeldungBean getElement() {
        return new BaeldungBean("John");
    }

    @Bean
    @Order(3)
    public BaeldungBean getAnotherElement() {
        return new BaeldungBean("Adam");
    }

    @Bean
    @Order(1)
    public BaeldungBean getOneMoreElement() {
        return new BaeldungBean("Harry");
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CollectionConfig.class);
        CollectionsBean collectionsBean = context.getBean(CollectionsBean.class);
        collectionsBean.printNameList();
        collectionsBean.printNameSet();
        collectionsBean.printNameMap();
        collectionsBean.printBeanList();
        collectionsBean.printBeanListWithoutQualifier();
        collectionsBean.printNameListWithDefaults();
    }


    public class CollectionsBean {
        // field 注入
        @Autowired
        private List<String> nameList;
        private Set<String> nameSet;
        private Map<Integer, String> nameMap;

        @Autowired(required = false)
        private List<BaeldungBean> beanListWithoutQualifier = new ArrayList<>();

        @Autowired(required = false)
        @Qualifier("CollectionsBean")
        private List<BaeldungBean> beanList = new ArrayList<>();

        // Setting an Empty List as a Default Value
        @Value("${names.list:}#{T(java.util.Collections).emptyList()}")
        private List<String> nameListWithDefaultValue;

        public CollectionsBean() {}
        // 构造函数传入
        public CollectionsBean(Set<String> strings) {
            this.nameSet = strings;
        }
        // setter 注入
        @Autowired
        public void setNameMap(Map<Integer, String> nameMap) {
            this.nameMap = nameMap;
        }

        public void printNameList() {System.out.println(nameList);}
        public void printNameSet() {
            System.out.println(nameSet);
        }
        public void printNameMap() {
            System.out.println(nameMap);
        }
        public void printBeanList() {
            System.out.println(beanList);
        }
        public void printBeanListWithoutQualifier() {
            System.out.println(beanListWithoutQualifier);
        }
        public void printNameListWithDefaults() {
            System.out.println(nameListWithDefaultValue);
        }
    }

    public class BaeldungBean {

        private String name;

        public BaeldungBean(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
