package com.scau.cache.service;

import com.scau.cache.entity.Customer;

public interface CustomerService {
    Customer getCustomerById(String cust_id);

    Customer getCustomerByName(String cust_name);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(String cust_id);
}
