package com.xxx.sample.shiro.service.utils;

import org.apache.shiro.crypto.AesCipherService;

import java.security.Key;

/**
 * Created by Orrin on 2016/7/25.
 */
public class AESUtils {

	private final static int AES_KEY_SIZE = 128;

	private static AesCipherService aesCipherService;


	static {
		aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(AES_KEY_SIZE); //设置key长度
	}

	//加密
	public static byte[] encrypt(byte[] plainText,byte[] key){
		return aesCipherService.encrypt(plainText,key).getBytes();
	}

	//加密
	public static String encrypt(String text,String password){
		return aesCipherService.encrypt(text.getBytes(),password.getBytes()).toHex();
	}

	//解密
	public static byte[] decrypt(byte[] cipherText,byte[] password){
		return aesCipherService.decrypt(cipherText,password).getBytes();
	}

	//解密
	public static String decrypt(String cipherText,String password){
		return aesCipherService.decrypt(cipherText.getBytes(),password.getBytes()).toHex();
	}

	public static Key createKey(){
		return aesCipherService.generateNewKey(AES_KEY_SIZE);
	}
}
