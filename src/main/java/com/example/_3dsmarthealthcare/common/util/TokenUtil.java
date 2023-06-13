package com.example._3dsmarthealthcare.common.util;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.UUID;

@Component
public class TokenUtil {

    /**
     * keyId,公钥,私钥 都是用 createKeyPair 方法生成
     */
    @Value("${token-key-id}")
    private String keyId;
    @Value("${token-private-key}")
    private String privateKeyStr;
    @Value("${token-public-key}")
    private String publicKeyStr;

    public static long accessTokenExpirationTime = 60 * 60 * 24;

    //jws创建token
    public String createToken(String email) {
        try {
            //Payload
            JwtClaims claims = new JwtClaims();
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            //expire time
            NumericDate date = NumericDate.now();
            date.addSeconds(accessTokenExpirationTime);
            claims.setExpirationTime(date);
            claims.setNotBeforeMinutesInThePast(1);
            claims.setSubject("YOUR_SUBJECT");
            claims.setAudience("YOUR_AUDIENCE");
            //添加自定义参数,必须是字符串类型
            claims.setClaim("email", email);

            //jws
            JsonWebSignature jws = new JsonWebSignature();
            //签名算法RS256
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            jws.setKeyIdHeaderValue(keyId);
            jws.setPayload(claims.toJson());
            /*
            RsaJsonWebKey jwk = null;
            try {
                jwk = RsaJwkGenerator.generateJwk(2048);
                } catch (JoseException e) {
                    e.printStackTrace();
                }
                jwk.setKeyId(keyId); */
            //PrivateKey privateKey = jwk.getPrivateKey();
            PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(privateKeyStr)).getPrivateKey();
            jws.setKey(privateKey);

            //get token
            String idToken = jws.getCompactSerialization();
            return idToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jws校验token
     *
     * @param token
     * @return 返回 用户账号
     * @throws JoseException
     */
    public String verifyToken(String token) {
        try {
            JwtConsumer consumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setMaxFutureValidityInMinutes(5256000)
                    .setAllowedClockSkewInSeconds(30)
                    .setRequireSubject()
                    //.setExpectedIssuer("")
                    .setExpectedAudience("YOUR_AUDIENCE")
                    /*
                    RsaJsonWebKey jwk = null;
                    try {
                        jwk = RsaJwkGenerator.generateJwk(2048);
                        } catch (JoseException e) {
                            e.printStackTrace();
                        }
                        jwk.setKeyId(keyId); */
                    //.setVerificationKey(jwk.getPublicKey())
                    .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(publicKeyStr)).getPublicKey())
                    .build();

            JwtClaims claims = consumer.processToClaims(token);
            if (claims != null) {
                System.out.println("认证通过！");
                String email = (String) claims.getClaimValue("email");
                System.out.println("token payload携带的自定义内容:用户账号email=" + email);
                return email;
            }
        }  catch (JoseException e) {
            e.printStackTrace();
        }  catch (InvalidJwtException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建jwk keyId , 公钥 ，秘钥
     */
    public static void createKeyPair(){
        String keyId = UUID.randomUUID().toString().replaceAll("-", "");
        RsaJsonWebKey jwk = null;
        try {
            jwk = RsaJwkGenerator.generateJwk(2048);
        } catch (JoseException e) {
            e.printStackTrace();
        }
        jwk.setKeyId(keyId);
        //采用的签名算法 RS256
        jwk.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
        String publicKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        String privateKey = jwk.toJson(RsaJsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

        System.out.println("keyId="+keyId);
        System.out.println();
        System.out.println("私钥 privateKeyStr="+privateKey);
        System.out.println();
        System.out.println("公钥 publicKeyStr="+publicKey);
    }

    public static void main(String[] args){
        createKeyPair();
    }
}

