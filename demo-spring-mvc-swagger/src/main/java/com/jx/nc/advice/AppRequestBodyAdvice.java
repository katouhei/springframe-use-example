package com.jx.nc.advice;

import cn.hutool.core.exceptions.StatefulException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.jx.nc.Contants;
import com.jx.nc.advice.annotation.SecretAnnotation;
import com.jx.nc.busexcep.CustomBusException;
import com.jx.nc.redis.StandaloneRedisService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(basePackages = {"com.jx.nc.controller", "com.jx.nc.system.controller"})
public class AppRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(AppRequestBodyAdvice.class);

    @Resource
    private StandaloneRedisService standaloneRedisService;

    @ExceptionHandler(StatefulException.class)
    @ResponseBody
    public String StatefulException(StatefulException se) {
        return se.getMessage();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //判断方法是否存在解密注解，存在则拦截，不存在则不处理
        return methodParameter.getMethodAnnotation(SecretAnnotation.class) != null;
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
                        if (tokenList == null || tokenList.isEmpty()) {
                            throw new RuntimeException("请求头缺少token");
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
    private String decode(String bodyStr, String token) throws CustomBusException {

        Map map = JSONUtil.toBean(bodyStr, HashMap.class);

        logger.info("提交的内容：" + bodyStr);
        // 密钥
        JWT jwt = JWT.of(token);
        // 默认验证HS265的算法
//        JWTSigner signer = JWTSignerUtil.createSigner(SignAlgorithm.SHA256withRSA.getValue(),
//                // 随机生成密钥对，此处用户可自行读取`KeyPair`、公钥或私钥生成`JWTSigner`
//                Contants.keyPair.getPublic());
        JWTSigner signer = JWTSignerUtil.createSigner(SignAlgorithm.MD5withRSA.getValue(),
                // 随机生成密钥对，此处用户可自行读取`KeyPair`、公钥或私钥生成`JWTSigner`
                KeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), Base64.decode(Contants.publicKey)));

        boolean verify = jwt.setSigner(signer).verify();
//        boolean verify = JWT.of(token).setKey(StrUtil.bytes(Contants.secretKey)).verify();
        if (standaloneRedisService.containsKey(token) && standaloneRedisService.getValue(token) != null) {
            if (verify) {
                logger.info("Payloads的信息：" + JSONUtil.toJsonStr(jwt.getPayloads()));
                standaloneRedisService.cacheValue(token, "jwttoken", 5 * 60 * 1000);
                return "";
            } else {
                throw new CustomBusException("token验证无效");
            }
        } else {
            throw new CustomBusException("token已过期");
        }
    }
}
