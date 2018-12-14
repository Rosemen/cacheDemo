package com.scau.cache.controller;

import com.scau.cache.entity.Customer;
import com.scau.cache.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cust")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/getById/{cust_id}")
    @ResponseBody
    public Customer getCustomerById(@PathVariable("cust_id") String cust_id){
        Customer customer = customerService.getCustomerById(cust_id);
        return customer;
    }

    @RequestMapping("/update")
    public String updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return "cust/success";
    }

    @RequestMapping("/delete/{cust_id}")
    public String deleteCustomer(@PathVariable("cust_id") String cust_id){
        customerService.deleteCustomer(cust_id);
        return "cust/success";
    }

    @ResponseBody
    @RequestMapping("/getByName/{cust_name}")
    public Customer getCustomerByName(@PathVariable("cust_name") String cust_name){
        Customer customer = customerService.getCustomerByName(cust_name);
        return customer;
    }
}
