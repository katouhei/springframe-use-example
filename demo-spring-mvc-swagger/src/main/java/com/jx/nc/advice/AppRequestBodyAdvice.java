package com.jx.nc.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.jwt.JWT;
import com.jx.nc.advice.annotation.SecretAnnotation;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

@ControllerAdvice(basePackages = "com.jx.nc.controller")
public class AppRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //判断方法是否存在解密注解，存在则拦截，不存在则不处理
        return methodParameter.getMethodAnnotation(SecretAnnotation.class) != null ;
//        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getMethod().isAnnotationPresent(SecretAnnotation.class)) {
            SecretAnnotation secretAnnotation = methodParameter.getMethod().getAnnotation(SecretAnnotation.class);
            if (secretAnnotation.decode()) {
                return new HttpInputMessage() {
                    @Override
                    public InputStream getBody() throws IOException {
                        List<String> tokenList = httpInputMessage.getHeaders().get("token");
                        if (tokenList.isEmpty()) {
                            throw new RuntimeException("请求头缺少appID");
                        }
                        String token = tokenList.get(0);
                        String bodyStr = IoUtil.read(httpInputMessage.getBody(), "utf-8");

                        bodyStr = decode(bodyStr, token);


                        return IoUtil.toStream(bodyStr, "utf-8");
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        return httpInputMessage.getHeaders();
                    }
                };
            }
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    /**
     * 对数据做鉴权、解密等业务逻辑处理
     *
     * @param bodyStr
     * @param token
     * @return
     */
    private String decode(String bodyStr, String token) {
        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwiYWRtaW4iOnRydWUsIm5hbWUiOiJsb29seSJ9." +
                "536690902d931d857d2f47d337ec81048ee09a8e71866bcc8404edbbcbf4cc40";

// 密钥
        byte[] key = "1234567890".getBytes();

// 默认验证HS265的算法
        boolean verify = JWT.of(rightToken).setKey(key).verify();

//        byte[] data = "我是一段测试字符串".getBytes();
//        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
//        //签名
//        byte[] signed = sign.sign(data);
//        //验证签名
//        boolean verify = sign.verify(data, signed);

        return bodyStr;
    }
}
