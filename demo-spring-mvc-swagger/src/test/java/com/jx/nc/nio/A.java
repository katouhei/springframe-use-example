package com.jx.nc.nio;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Test;

public class A {

    @Test
    public void crate() {
        RSA rsa = new RSA();
        //获得私钥
//        rsa.getPrivateKey();
        //获得公钥
//        rsa.getPublicKey();
        System.out.println("公钥："+rsa.getPublicKeyBase64());
        System.out.println("私钥："+rsa.getPrivateKeyBase64());


        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String mw = Base64.encode(encrypt);
        System.out.println("原文加密后的密文："+ mw);

        byte[] decrypt = rsa.decrypt(Base64.decode(mw), KeyType.PrivateKey);
        System.out.println("密文解密字符串："+IoUtil.readUtf8(IoUtil.toStream(decrypt)));
    }
}
