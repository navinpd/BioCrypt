
package com.gemalto.tokenlibrary.utilities;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Utilities {

	private static final byte[] PRESHARED_WBC_KEY = new byte []{(byte)0x26, (byte)0x58, (byte)0xe8, (byte)0xab, (byte)0x20, (byte)0xb7, (byte)0xe6, (byte)0x5b, (byte)0x31, (byte)0x51, (byte)0xc2, (byte)0x9c,
			(byte)0xb7, (byte)0xd6, (byte)0xf6, (byte)0xe8, (byte)0xee, (byte)0xbe, (byte)0x08, (byte)0x05, (byte)0x4d, (byte)0x23, (byte)0x44, (byte)0x99,
			(byte)0x07, (byte)0x15, (byte)0x09, (byte)0xcc, (byte)0xdd, (byte)0xd8, (byte)0x52, (byte)0xde};
    private static final byte[] IV = new byte []{(byte)0x26, (byte)0x58, (byte)0xe8, (byte)0xab, (byte)0x20, (byte)0xb7, (byte)0xe6, (byte)0x5b, (byte)0x31, (byte)0x51, (byte)0xc2, (byte)0x9c,
            (byte)0xb7, (byte)0xd6, (byte)0xf6, (byte)0xe8};

    public static final boolean ENABLED_LOG = true;

	public static byte[] encrypt(byte[] key, byte[] plainMsg) {
		try {
			IvParameterSpec iv = new IvParameterSpec(IV);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(plainMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] key, byte[] encMsg) {
		try {
			IvParameterSpec iv = new IvParameterSpec(IV);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(encMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt_ecb(byte[] key, byte[] encMsg) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return cipher.doFinal(encMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt_ecb(byte[] key, byte[] encMsg) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return cipher.doFinal(encMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] getHash(byte[] inputData) {
		MessageDigest digest=null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		digest.reset();
		return digest.digest(inputData);
	}


	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String stringToHex(String arg) {
		try {
			return String.format("%040x", new java.math.BigInteger(1, arg.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    public static String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

	public static class ByteUtils {
		public static byte[] longToBytes(long x) {
			ByteBuffer longBuffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
			longBuffer.putLong(0, x);
			return longBuffer.array();
		}

		public static long bytesToLong(byte[] bytes) {
			ByteBuffer longBuffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
			longBuffer.put(bytes, 0, bytes.length);
			longBuffer.flip();//need flip
			return longBuffer.getLong();
		}

		public static byte[] intToBytes(int x) {
			ByteBuffer intBuffer = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
			intBuffer.putInt(0, x);
			return intBuffer.array();
		}

		public static int bytesToInt(byte[] bytes) {
			ByteBuffer intBuffer = ByteBuffer.allocate(Integer.SIZE/Byte.SIZE);
			intBuffer.put(bytes, 0, bytes.length);
			intBuffer.flip();//need flip
			return intBuffer.getInt();
		}
	}
}
