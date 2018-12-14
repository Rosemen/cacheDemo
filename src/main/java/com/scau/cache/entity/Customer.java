package com.scau.cache.entity;

import java.io.Serializable;

/**
 * 实现序列化接口，以便将对象放到redis中
 */
public class Customer implements Serializable {
    private String cust_id;
    private String cust_name;
    private Integer cust_age;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public Integer getCust_age() {
        return cust_age;
    }

    public void setCust_age(Integer cust_age) {
        this.cust_age = cust_age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cust_id='" + cust_id + '\'' +
                ", cust_name='" + cust_name + '\'' +
                ", cust_age=" + cust_age +
                '}';
    }
}
