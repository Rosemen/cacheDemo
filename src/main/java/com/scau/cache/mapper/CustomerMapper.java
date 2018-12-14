package com.scau.cache.mapper;

import com.scau.cache.entity.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CustomerMapper {
    @Select("select * from t_customer where cust_id = #{cust_id}")
    Customer getCustomerById(String cust_id);

    @Select("select * from t_customer where cust_name = #{cust_name}")
    Customer getCustomerByName(String cust_name);

    @Update("update t_customer set cust_name = #{cust_name},cust_age = #{cust_age} where cust_id = #{cust_id}")
    void updateCustomer(Customer customer);

    @Delete("delete from t_customer where cust_id = #{cust_id}")
    void deleteCustomer(String cust_id);
}
