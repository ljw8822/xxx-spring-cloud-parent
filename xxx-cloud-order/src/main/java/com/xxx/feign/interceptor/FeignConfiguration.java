package com.xxx.feign.interceptor;

import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Enumeration;

/**
 * @author ：jiweili
 * @date ：Created in 2021/7/30 17:03
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestIntercepter();
    }
    @Bean
    public Decoder decoder() {
        return new MyDecoder();
    }
    @Bean
    public Encoder encoder() {
        return new MyEncoder();
    }


    class FeignRequestIntercepter implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            System.out.println("intecepter");
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                requestTemplate.header(headerName,headerValue);
            }
            requestTemplate.body("a=123");
        }
    }

    class MyDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            System.out.println("decode");
            return response.body().toString();
        }
    }

    class MyEncoder implements Encoder {

        @Override
        public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
            System.out.println("encoder");
        }
    }

}
