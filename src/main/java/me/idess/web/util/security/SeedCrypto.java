package me.idess.web.util.security;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import me.idess.web.util.security.seed.AnsiX923Padding;
import me.idess.web.util.security.seed.SeedCipher;

public class SeedCrypto {
	
	// User secret key
	final String	userKey;
	final byte		pbUserKey[];
	
	private SeedCrypto() {
		AnsiX923Padding padding = new AnsiX923Padding();
		// this.userKey = "InfoSec Tech.";
		this.userKey = "no1tms__no1tms__";
		// this.userKey = "infosec&finecube";
		this.pbUserKey = padding.addPadding(userKey.getBytes(), 16);
		
	}
	
	private static class Holder {
		private static final SeedCrypto	_instance	= new SeedCrypto();
	}
	
	public static SeedCrypto getInstance() {
		return Holder._instance;
	}
	
	public String encrypt(String strVal) {
		
		SeedCipher seed = new SeedCipher();
		byte[] outData;
		try {
			outData = seed.encrypt(strVal, pbUserKey, "UTF-8");
			String encryptText = new String(Base64.encodeBase64(outData));
			return encryptText;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String decrypt(String strVal) {
		try {
			SeedCipher seed = new SeedCipher();
			String decryptText = seed.decryptAsString(Base64.decodeBase64(strVal), pbUserKey, "UTF-8");
			return decryptText;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
