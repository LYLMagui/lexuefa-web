package test;

import com.lexuefa.LeXueFaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis测试类
 * @author ukir
 * @date 2023/04/28 14:38
 **/
@SpringBootTest(classes = LeXueFaApplication.class)
public class RedisTest {
    
    @Resource
    private RedisTemplate redisTemplate;
    
     @Resource
    private StringRedisTemplate stringRedisTemplate;
     
     @Test
     public void testPut(){
         //设置key和value，并保存到redis中
         redisTemplate.opsForValue().set("wechatCode","123456");
         stringRedisTemplate.opsForList().rightPush("girl","王小美");
         stringRedisTemplate.opsForList().rightPush("girl","菲菲");
         stringRedisTemplate.opsForList().rightPush("girl","陈千灵");
     }
     @Test
     public void testGet(){
         Object value = redisTemplate.opsForValue().get("wechatCode");
         System.out.println(value);

         List<String> girls = stringRedisTemplate.opsForList().range("girl",0,-1);
         System.out.println(girls);
     }
}
