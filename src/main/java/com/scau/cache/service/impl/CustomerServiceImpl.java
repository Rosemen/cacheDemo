package com.scau.cache.service.impl;

import com.scau.cache.entity.Customer;
import com.scau.cache.mapper.CustomerMapper;
import com.scau.cache.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CacheManager:管理Cache组件
 * Cache组件：对缓存里的内容进行crud，缓存中的内容是以key-value形式存储的
 * @Cacheable ：标注在方法上，从缓存中根据key查找值，有的话不调用这个方法,没有的话则调用方法，并将返回值放到缓存中
 * value:缓存名
 * key:缓存中的key，默认是使用方法参数，可以自定义，也可以使用SPEL表达式，eg:#result.id 表示使用返回值的id值作为key
 * #root.methodName,#cust_id == #result.cust_id,.....
 * keyGenerator：key的生成器g与key属性二选一
 * cacheManager：指定从哪个缓存管理器中拿Cache组件
 * condition:符合条件则缓存，否则不缓存
 * unless：符合条件则不缓存
 * ==========================
 * @CachePut： 标注在方法上，，方法每次都会调用，也会把结果放到缓存中,主要用于更新相关的场景
 *
 * 缓存中的key对应的value是方法的返回值，所以没法用在无返回值的方法上（@Cacheable/@CachePut）
 * @CacheEvict: 标注在方法上，会删除缓存中在数据，除了不能用unless,其它属性与@Cacheable 与 @CachePut相同
 *    beforeInvocation = false;指定删除是在方法执行之前或执行之后，默认是false，也就是执行方法之后删除。
 *    如果是执行之后，则方法出错不会删除缓存中数据
 * @Caching: 使用复杂的缓存规则，里面可以使用@Cacheable、@CachePut、@CacheEvict
 * @CacheConfig: 用在类上，抽取公共的缓存配置，如缓存名，缓存管理器....
 */
@Service
@Transactional
@CacheConfig(cacheNames = {"customerCache"})//指定名字后，方法上的注解不用再指定名字
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Cacheable(/*value="customerCache",*/key="#cust_id",condition = "#cust_id != null")
    @Override
    public Customer getCustomerById(String cust_id) {
        Customer customer = customerMapper.getCustomerById(cust_id);
        System.out.println("执行到查询方法");
        return customer;
    }

    @Caching(//这样的话，返回对象既会以key = cust_name保存在缓存中，也会以key = cust_id 保存在缓存中
            cacheable = {
                    @Cacheable(/*cacheNames = "customerCache",*/key = "#cust_name")
            },
            put = {
                    @CachePut(/*cacheNames = "customerCache",*/key = "#result.cust_id")
    }
    )
    @Override
    public Customer getCustomerByName(String cust_name) {
        Customer customer = customerMapper.getCustomerByName(cust_name);
        return customer;
    }

    @CachePut(/*value="customerCache",*/key="#customer.cust_id",condition = "#customer != null")
    @Override
    public Customer updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
        System.out.println("执行到修改方法");
        return customer;
    }

    @CacheEvict(/*value="customerCache",*/key = "#cust_id")
    @Override
    public void deleteCustomer(String cust_id) {
        customerMapper.deleteCustomer(cust_id);
    }
}
