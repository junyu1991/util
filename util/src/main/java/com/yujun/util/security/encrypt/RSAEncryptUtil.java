package com.yujun.util.security.encrypt;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author admin
 * @version 1.0.0
 * @date 2019/7/26 11:08
 * @description TODO
 **/
public class RSAEncryptUtil {
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String loadPublicKeyByFile(String filePath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        br.close();
        return sb.toString();
    }

    public static String getPublicKeyFromFile(String cerPath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(cerPath);
        X509Certificate Cert = (X509Certificate) certificateFactory.generateCertificate(fis);
        PublicKey pk = Cert.getPublicKey();
        String publicKey = new BASE64Encoder().encode(pk.getEncoded());
        return publicKey;
    }

    public static RSAPublicKey getPublicKey(String cerPath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(cerPath);
        X509Certificate Cert = (X509Certificate) certificateFactory.generateCertificate(fis);
        PublicKey pk = Cert.getPublicKey();
        return (RSAPublicKey) pk;
    }

    public static byte[] encrytByPublicKey(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if(publicKey == null) {
            throw new Exception("Public key is null");
        }
        Cipher cipher = null;
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(plainTextData);
        return output;
    }

    public static void main(String[] args) throws Exception {
        String publicKey = getPublicKeyFromFile("E:\\code\\eclipse\\cipher\\config-server-publickey.cer");
        System.out.println(publicKey);
        RSAPublicKey key =  getPublicKey("E:\\code\\eclipse\\cipher\\config-server-publickey.cer");
        String plain = "hello";
        System.out.println(new BASE64Encoder().encode(encrytByPublicKey(key, plain.getBytes())));
    }
}
