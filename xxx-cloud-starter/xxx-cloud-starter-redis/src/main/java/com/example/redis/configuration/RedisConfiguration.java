package com.example.redis.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/3 10:44
 * @description：
 * CachingConfigurerSupport：继成该类可自定义缓存实现<br/>
 * RedisTemplate：自定义redistemplate 需要设置连接方式 RedisConnectionFactory <br/>
 * RedisUtil: 向容器中添加RedisUtil 进行redis操作 <br/>
 *
 * <p>spring cache</p>
 * 1、通过 @EnableCaching 注解开启缓存
 * 2、@Cacheable注解
 * <pre>
 * 可以标记在一个方法上，也可以标记在一个类上。当标记在一个方法上时表示该方法是支持缓存的，
 * 当标记在一个类上时则表示该类所有的方法都是支持缓存的。对于一个支持缓存的方法，Spring会
 * 在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中
 * 获取结果，而不需要再次执行该方法。Spring在缓存方法的返回值时是以键值对进行缓存的，值就
 * 是方法的返回结果，至于键的话，Spring又支持两种策略，默认策略和自定义策略<br/>
 *
 * 可指定三个属性value、key和condition<br/>
 *  1) value属性是必须指定的，其表示当前方法的返回值是会被缓存在哪个Cache上的，对应Cache的名称。
 *  其可以是一个Cache也可以是多个Cache，当需要指定多个Cache时其是一个数组。<br/>
 *  2) key属性是用来指定Spring缓存方法的返回结果时对应的key的。该属性支持SpringEL表达式。当我
 *  们没有指定该属性时，Spring将使用默认策略生成key。我们这里先来看看自定义策略，至于默认策略会在后文单独介绍。
 *  自定义策略是指我们可以通过Spring的EL表达式来指定我们的key。这里的EL表达式可以使用方法参数及它们对应的属性。
 *  使用方法参数时我们可以直接使用“#参数名”或者“#p参数index”
 *  eg:
 *  @Cacheable(value="users", key="#id")
 *  public User find(Integer id) {
 *     returnnull;
 *  }
 *  @Cacheable(value="users", key="#p0")
 *  public User find(Integer id) {
 *     returnnull;
 *  }
 *  @Cacheable(value="users", key="#user.id")
 *  public User find(User user) {
 *     returnnull;
 *  }
 *  @Cacheable(value="users", key="#p0.id")
 *  public User find(User user) {
 *     returnnull;
 *  }
 * 3、@CacheEvict注解 标注了该注解的方法和类（类中每个方法）都会清除缓存 方法体可以任何事情不做
 *
 * 4、@CachePut注解， 不影响方法执行逻辑，每次都会更新缓存
 *
 * </pre>
 *
 * @modified By：
 * @version: $
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport{

    /**
     *  自定义redistemplate 设置key和value的序列化和反序列化器
     *  @param redisConnectionFactory redis的连接方式
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //自定义restTemplate需要设置连接方式
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //使用FastJsonRedisSerializer来序列化和反序列化redis的value
        FastJson2JsonRedisSerializer fastJsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        return redisTemplate;
    }

    /**
     * 启用缓存redis存储，从数据库中查询出来的数据缓存到redis中
     * @param connectionFactory redis的连接方式
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializationContext.SerializationPair serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(new FastJson2JsonRedisSerializer<>(Object.class));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ZERO)
                .serializeValuesWith(serializationPair);
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * TODO 增加本地缓存 减少redis缓存服务的压力 (热点数据本地缓存)  自定义CacheResolver？ https://blog.csdn.net/sz85850597/article/details/89301331
     *
     */
    @Override
    public CacheResolver cacheResolver() {

        return super.cacheResolver();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) ->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName());
            stringBuilder.append(method.getName());
            for (Object object : objects) {
                stringBuilder.append(object.toString());
            }
             return stringBuilder.toString();
        };
    }

    /**
     * TODO redis缓存异常处理，不能影响正常的业务处理（异常后，返回空数据，业务执行从数据库获取数据）
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }
}
