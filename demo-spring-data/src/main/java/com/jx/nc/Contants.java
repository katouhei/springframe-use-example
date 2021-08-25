package com.jx.nc;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

import java.security.KeyPair;

public class Contants {

    public static String secretKey = "30213891@2021";

    public static String userAccount = "test123";

    public static String userName = "东兵";

    public static KeyPair keyPair = KeyUtil.generateKeyPair(SignAlgorithm.SHA256withRSA.getValue());

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU9r2KgaVAnRP13U2BEnjqkNDDKT7YtXlF7h8nDxmzgK6IbR+8d34Tbn7mcEutb01/r7Dr6K+d5BvSIdjoI3vkfEA9TmrX8wcheJIsNoQU1xURX5YG/L8hNL2WKSQECSlV+w4bZiHcCiu82VARk0JD9Y6Pm2akvug50/etJEbzzQIDAQAB";

    public static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJT2vYqBpUCdE/XdTYESeOqQ0MMpPti1eUXuHycPGbOArohtH7x3fhNufuZwS61vTX+vsOvor53kG9Ih2Ogje+R8QD1OatfzByF4kiw2hBTXFRFflgb8vyE0vZYpJAQJKVX7DhtmIdwKK7zZUBGTQkP1jo+bZqS+6DnT960kRvPNAgMBAAECgYAXT8W5tzA1PF+zSj6mxE1H6AHDiODc6QlheDJH2oUbIjguAWXGCK0iPVh+l3RAX/qw0L3PLUrOWrQ/3iwjp+nrScIGw7Bjxetp0I4sYNeiAhBTC3Ga3ATaNYjOEIgfDhSNZqRynRY/q3hBixByLSaFy/WE/rz4PQiAckFCmu9qAQJBAOe4w7mlVDmIGkUEiFIcTnTu8/lvQUFDGmwrRtYMk1CD2pGMKXEjfnz3ArT+LXJC8t53KtsnKcMu4yr4KYD4UKkCQQCkkj667t0G/tXHhbquY7XEVk/TduO+r8o37BRiHQq5wSbdR835kJ41JTnb+7HeFZu2KPE5m/YkCt/G6KDjKCyFAkA7awhdQUSwn/LEgd4G2BVSLLfI3TtpZbCPziUS3BSmT7krGzrBNS3JE3nROWAj7YDjhMErgwZVc19y9EGIPaRJAkAF51UmwZDeQmAK40o6HUuU+n+sl1Q+L/IyJV7p0h7jYQFy6LOD42M5htdXg+TykZXfaAKXTXf7/QPgD9JNs9A9AkBhFQVHjCZwj19ZW0hWYgcg7li/DeSyxbN4In/3qwoV+eYmECfS/BPXe/hBRNem0/Pk0Z8OQJwE3eGuKPHTPeq0";
}
