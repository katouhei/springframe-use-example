package com.jx.nc.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.jx.nc.Contants;
import com.jx.nc.advice.annotation.SecretAnnotation;
import com.jx.nc.redis.StandaloneRedisService;
import com.jx.nc.system.bean.SysLog;
import com.jx.nc.system.bean.SysUser;
import com.jx.nc.system.service.SysLogService;
import com.jx.nc.system.service.SysUserService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "API对外接口")
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private StandaloneRedisService standaloneRedisService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @SecretAnnotation(decode = true)
    @PostMapping("/addInfo.api")
    @ApiOperation("新增用户信息")
    @ResponseBody
    public String addUser(@RequestBody SysUser requestObject) throws Exception {
        SysUser user = new SysUser();
        user.setUserName("新增用户" + LocalDateTime.now().format(FORMATTER));
        Map resultMap = new HashMap<>();
        if (sysUserService.insert(user)) {
            resultMap.put("msg", "新增用户成功");
        } else {
            resultMap.put("msg", "新增用户失败");
        }
        return JSONUtil.toJsonStr(resultMap);
    }

    @PostMapping("/queryLogInfo.api")
    @ApiOperation("查询日志信息")
    @ResponseBody
    public String queryLogInfo(@RequestBody SysLog requestObject) throws Exception {
        List<SysLog> list = sysLogService.select(new HashMap());
        return JSONUtil.toJsonStr(JSONUtil.parseArray(list, JSONConfig.create().setDateFormat("yyyy-MM-dd HH:mm:ss")));
    }

    @SecretAnnotation(decode = false)
    @PostMapping("/queryBlank.api")
    @ApiOperation("查询空值")
    @ResponseBody
    public String queryBlank(@RequestBody String s) throws Exception {

        return "";
    }

    @PostMapping("/queryListLogInfo.api")
    @ApiOperation("查询日志信息")
    @ResponseBody
    public List<SysLog> queryListLogInfo(@RequestBody SysLog requestObject) throws Exception {
        List<SysLog> list = sysLogService.select(new HashMap());
        return list;
    }

    @SecretAnnotation(decode = true)
    @PostMapping("/queryInfo.api")
    @ApiOperation("查询用户信息")
    @ResponseBody
    public String queryInfo(@RequestBody SysUser requestObject) throws Exception {
        List<SysUser> list = sysUserService.selectByMap(new HashMap());
        return JSONUtil.toJsonStr(list);
    }

    @SecretAnnotation(decode = true)
    @PostMapping("/test.api")
    @ApiOperation("测试redis信息")
    @ResponseBody
    public String test(@RequestBody String requestObject) throws Exception {

        Map resultMap = new HashMap<>();
        resultMap.put("uid", RandomUtil.randomString(32));
        return JSONUtil.toJsonStr(resultMap);
    }

    @PostMapping("/login.api")
    @ApiOperation("测试登录并生成token")
    @ResponseBody
    public String login(@RequestBody SysUser requestObject) throws Exception {
//        JWTSigner signer = JWTSignerUtil.createSigner(SignAlgorithm.SHA256withRSA.getValue(),
//                // 随机生成密钥对，此处用户可自行读取`KeyPair`、公钥或私钥生成`JWTSigner`
//                Contants.keyPair);
        JWTSigner signer = JWTSignerUtil.createSigner(SignAlgorithm.MD5withRSA.getValue(),
                // 随机生成密钥对，此处用户可自行读取`KeyPair`、公钥或私钥生成`JWTSigner`
                KeyUtil.generatePrivateKey(SignAlgorithm.SHA256withRSA.getValue(), Base64.decode(Contants.privateKey)));

//        String token = JWT.create()
//                .setPayload("sub", Contants.userAccount)
//                .setPayload("name", Contants.userName)
//                .setPayload("admin", true)
//                .setKey(StrUtil.bytes(Contants.secretKey))
////                .setSigner(signer)
//                .sign();

        String token = JWT.create()
                .setPayload("sub", Contants.userAccount)
                .setPayload("name", Contants.userName)
                .setPayload("admin", true)
//                .setKey(StrUtil.bytes(Contants.secretKey))
                .setSigner(signer)
                .sign();
        standaloneRedisService.cacheValue(token, "jwttoken", 5 * 60 * 1000);

        return token;
    }
}
