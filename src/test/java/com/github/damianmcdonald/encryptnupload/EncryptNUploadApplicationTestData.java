package com.github.damianmcdonald.encryptnupload;

import java.io.*;

import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class EncryptNUploadApplicationTestData {
	
	protected final static String USERNAME = "han.solo";
	protected final static String PASSWORD = "millennium-falcon";
	protected final static String SHARED_SECRET = "kGQvUzSp#fLt+k+kUPk2x_-3F_GwUCB!r&+^H*Ka5kMJ9J#tWq&4ZByHetsk$QfA";
	protected final static String FILENAME = urlTOFile().getAbsolutePath();
	protected final static byte[] FILE_BYTES = uriToBytes();
	protected final static String MD5_CHECKSUM = "acebad1c64c0f6c7dcb81cf2c86c5631";
	protected final static String ENCRYPTED_USERNAME = "IpxatQIUkuBrJqMQOpnRmw==";
	protected final static String SALT_USERNAME = "b346a272df5d8b19cd3bac5a4855f60e";
	protected final static String IV_USERNAME = "a296df82519bd65fb4ce666e29fcd8eb";
	protected final static String[] PASSPHRASE_USERNAME = new String[]{"Hm","*s","*2","rD","+J","3X","zZ","$L","LP","Yq"};
	protected final static String ENCRYPTED_PASSWORD = "e3kgxBIRwD4+d2rV0xwomW8OppQJxx4NpxFZUAgVWL8=";
	protected final static String SALT_PASSWORD = "7b03880a0421dc7307c1003abf1d52df";
	protected final static String IV_PASSWORD = "e8984ccf99de9f9912049b65e515bb03";
	protected final static String[] PASSPHRASE_PASSWORD = new String[]{"n4","Yq","Hm","3X","W^","8@","Lt","Ps","Hm","L$"};
	protected final static String UPLOAD_SUCCESS_VAL = "Upload successfull";

	protected static byte[] uriToBytes(){
		Path path = null;
		byte[] bytes = null;
		try {
			path = Paths.get(EncryptNUploadApplicationTestData.class.getResource("/jabba_the_hutt.jpg").toURI());
			bytes =  Files.readAllBytes(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	protected static File urlTOFile() {
		File f = null;
		try {
		  f = new File(EncryptNUploadApplicationTestData.class.getResource("/jabba_the_hutt.jpg").toURI());
		} catch(URISyntaxException e) {
			try {
				f = new File(EncryptNUploadApplicationTestData.class.getResource("/jabba_the_hutt.jpg").toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				fail("Unable to parse file resource: jabba_the_hutt.jpg");
			}
		}
		return f;
	}
	
	protected static String arrayToString(String[] arr) {
		final StringBuffer sb = new StringBuffer();
		for(int i = 0; i<arr.length; i++) {
			if (i == arr.length-1) {
				sb.append(arr[i]);
			} else {
				sb.append(arr[i] + ",");
			}
		}
		return sb.toString();
	}
	
	protected static String byteArrayToString(byte[] arr) {
		final StringBuffer sb = new StringBuffer();
		for(int i = 0; i<arr.length; i++) {
			if (i == arr.length-1) {
				sb.append(arr[i]);
			} else {
				sb.append(arr[i] + ",");
			}
		}
		return sb.toString();
	}
	

	
}
