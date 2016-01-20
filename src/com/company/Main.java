package com.company;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import

public class Main {

    static String stringPublic = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkpsSOSvt12GZ4yooC+lJrx5yd" +
            "bJjdVwG9VX1y96uWsdl1AuvgxEuL6D8qVLFH0+xIz9Y+qByuL3aDxxE1UZHNj0J9" +
            "U6ke8OEj4mpTDUJ4bwtL8gpoqtWc7rDRrKzY3gSf/1VBMezQqI0domcgStAiSVnm" +
            "2akMhH2yfCe5L4BWcwIDAQAB";

    static String stringPrivate ="95371d4a4c113ce5adfcca9cbc73"+
"403e1386cc0f96b151644fda81de68"+
"df6cc35b62541e76a673ea486a7d53"+
"87549de56bcb558265faf5761f7634"+
"4ce2d4e82f817134036be6a487f30a"+
"88c838e509f805216bbeaf9f31e0af"+
"650edfb66457bc204a4cfa94b51499"+
"473a3f76ddbee3e3bf5fada271be58"+
"049f1ef96158414b01";


    public static void main(String[] args) {
        // write your code here
        Log("Test");
        PublicKey publicKey = createPublicKey(stringPublic);
        PrivateKey privateKey = createPrivateKey(stringPrivate);
        byte[] encrypted = encryptData(publicKey, "Hello world!");
        byte[] decrypted = decryptData(privateKey, encrypted);
        Log("The result of decrypting encrypted data was " + new String(decrypted));

    }

    public static PublicKey createPublicKey(String stringPub) {

//        public static RSAPublicKey createPublicKey(String stringPub) {
// /        BigInteger modulus = new BigInteger(stringPub, 16);
//        BigInteger pubExp = new BigInteger("010001", 16);
//
//        RSAPublicKey key = null;
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
//            key = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
//            return key;
//        } catch (Exception e) {
//            Log("Error creating key -- " + e.toString());
//        }
//        return null;
        byte[] byteKey = null;

        try {
            byteKey = Base64.decode(stringPub);
            Log("length of bytekey " + byteKey.length);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            Log("Made key factory -- " + kf.toString());
            PublicKey pubkey = kf.generatePublic(new X509EncodedKeySpec(byteKey));
            Log("Created public key");
            return pubkey;
        }catch (Exception e){
            Log("Error making pubkey -- " + e.toString());
            return null;
        }

    }
    public static PrivateKey createPrivateKey(String stringPriv) {
        try {
            byte[] keyBytes = Base64.decode(stringPriv.getBytes("utf-8"));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey priv = fact.generatePrivate(keySpec);
            Log("Created privet key ");
            return priv;
        }catch (Exception e){
            return null;
        }
//    public static RSAPrivateKey createPrivateKey(String stringPriv) {
//        BigInteger modulus = new BigInteger(stringPriv, 16);
//        BigInteger pubExp = new BigInteger("010001", 16);
//
//        RSAPrivateKey key = null;
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, pubExp);
//            key = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
//            return key;
//        } catch (Exception e) {
//            Log("Error creating key -- " + e.toString());
//        }
//        return null;
    }
    public static byte[] encryptData(PublicKey pubkey, String data) {

//    public static byte[] encryptData(RSAPublicKey pubkey, String data) {

        Log("Encrypting data: " + data);
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            result = cipher.doFinal(data.getBytes());
            return result;

        } catch (Exception e) {
            Log("Error in the encryption step -- " + e.toString());
            return null;

        }

    }
    public static byte[] decryptData(PrivateKey privKey, byte[] data) {
//    public static byte[] decryptData(RSAPrivateKey privKey, byte[] data) {

        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            result = cipher.doFinal(data);
            return result;

        } catch (Exception e) {
            Log("Error in the encryption step -- " + e.toString());
            return null;

        }

    }



    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void Log(String text){

        System.out.println(text);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");

        }catch(Exception e){
            Log("Exception");
        }

    }
}
