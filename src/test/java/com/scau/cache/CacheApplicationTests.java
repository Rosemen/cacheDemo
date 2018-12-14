package com.scau.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scau.cache.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {
    @Autowired
    private RedisTemplate<Object, Customer> customerRedisTemplate;//自定义的能在redis将对象以json形式存储
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;//操作的key-value键值对都是对象类型的,默认的，容器为我们创建的
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//操作的key-value键值对都是String类型的

    private void testSimpleRedis() {//测试使用StringRedisTemplate操作五种数据类型：string/list/set/hash/zset
        //使用字符串模版
        //操作字符串类型
        // stringRedisTemplate.opsForValue().append("msg","Hello,redis!");//相当于命令: set msg "xxx";往redis中添加字符串
        //String msg = stringRedisTemplate.opsForValue().get("msg");
        //System.out.println("msg="+msg);
        //操作List对象
        //stringRedisTemplate.opsForList().leftPush("students","chen");//等价于 lpush students "chen";
        //操作set对象
        // stringRedisTemplate.opsForSet().add("children","tom","john");//等价于 sadd children tom,john
        //操作hash对象
        // stringRedisTemplate.opsForHash().put("myHash","name","chen");//等价于hput myHash name "chen"
        //========================================================================================
        // 使用对象模版
        //将对象以json方式存储到redis中
        //1、直接将对象转成json
        //2、自定义redisTemplate
        Customer customer = new Customer();
        customer.setCust_id("4");
        customer.setCust_name("hello");
        customer.setCust_age(18);


        try {//将对象转换成json串,key也要转换。。。
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(customer);
            // redisTemplate.opsForValue().set(customer.getCust_id(),value);
            // stringRedisTemplate.opsForValue().set(customer.getCust_id(),value);
            //System.out.println(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //使用自定义的redisTemplate,其实就是自己重写序列化器
        //customerRedisTemplate.opsForValue().set("customer",customer);
    }

    @Test
    public void contextLoads() {
        testSimpleRedis();
    }

}
