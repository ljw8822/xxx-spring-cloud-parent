package com.example.redis.configuration;

/**
 * @author ：jiweili
 * @date ：Created in 2021/8/3 14:24
 * @description：
 * @modified By：
 * @version: $
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
/**
 * FastJson2JsonRedisSerializer
 *  Redis使用FastJson序列化
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    static {

        /**2017年3月15日，fastjson官方发布安全升级公告，该公告介绍fastjson在1.2.24及之前的版本存在代码执行漏洞，当恶意攻击者提交一个精心构造的序列化数据到服务端时，由于fastjson在反序列化时存在漏洞，可导致远程任意代码执行。
　　      自1.2.25及之后的版本，禁用了部分autotype的功能，也就是”@type”这种指定类型的功能会被限制在一定范围内使用。
　　      而由于反序列化对象时，需要检查是否开启了autotype。所以如果反序列化检查时，autotype没有开启，就会报错。*/
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //如果遇到反序列化autoType is not support错误，请添加并修改一下包名到bean文件路径
        // ParserConfig.getGlobalInstance().addAccept("com.example.sentinel.datasource");
    }
    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }

}