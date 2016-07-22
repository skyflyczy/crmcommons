package com.np.crm.common.encrypt;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;

import org.apache.commons.lang3.StringUtils;

import com.springcryptoutils.core.cipher.Mode;
import com.springcryptoutils.core.cipher.asymmetric.Base64EncodedCipherer;
import com.springcryptoutils.core.cipher.asymmetric.Base64EncodedCiphererImpl;
import com.springcryptoutils.core.key.PrivateKeyFactoryBean;
import com.springcryptoutils.core.key.PublicKeyFactoryBean;
import com.springcryptoutils.core.keystore.Base64EncodedKeyStoreFactoryBean;

/**
 * 非对称加密
 * @author zhiya.chai
 * 2016年7月20日 下午3:44:21
 */
public class AsymmetricEncrypt {

	/**
	 * 
	 * @return Base64EncodedCipherer
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:34:55
	 */
	public static Base64EncodedCipherer getInstance(Key key,Mode mode){
		Base64EncodedCiphererImpl cipherer = new Base64EncodedCiphererImpl();
		cipherer.setKey(key);
		cipherer.setMode(mode);
		return cipherer;
	}
	
	/**
	 * 加密
	 * @return String
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:37:43
	 */
	public static String encryptStr(Key key,String message){
		Base64EncodedCipherer cipherer = getInstance(key, Mode.ENCRYPT);
		return cipherer.encrypt(message);
	}
	
	/**
	 * 解密
	 * @return String
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:38:21
	 */
	public static String decryptStr(Key key,String message){
		Base64EncodedCipherer cipherer = getInstance(key, Mode.DECRYPT);
		return cipherer.encrypt(message);
	}
	
	/**
	 * 得到KeyStore对象包含公钥私钥 jsf文件
	 * @return KeyStore
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:44:22
	 */
	public static KeyStore getKeyStore(String keystorefile,String password){
		Base64EncodedKeyStoreFactoryBean storeFactory = new Base64EncodedKeyStoreFactoryBean();
		storeFactory.setBase64EncodedKeyStoreFile(keystorefile);
		if(StringUtils.isNotBlank(password))
			storeFactory.setPassword(password);
		try {
			storeFactory.afterPropertiesSet();
			KeyStore keystore = (KeyStore) storeFactory.getObject();
			return keystore;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取公钥
	 * @return PublicKey
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:46:35
	 */
	public static PublicKey getPublicKey(KeyStore keystore,String alias){
		PublicKeyFactoryBean pkeyFactory = new PublicKeyFactoryBean();
		if(StringUtils.isNotBlank(alias))
			pkeyFactory.setAlias(alias);
		
		pkeyFactory.setKeystore(keystore);
		try {
			pkeyFactory.afterPropertiesSet();
			PublicKey pkey = (PublicKey) pkeyFactory.getObject();
			return pkey;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	/**
	 * 获取私钥
	 * @return PrivateKey
	 * @author zhiya.chai
	 * 2015年5月21日 下午4:48:49
	 */
	public static PrivateKey getPrivateKey(KeyStore keystore,String alias,String password){
		PrivateKeyFactoryBean priKeyFactory = new PrivateKeyFactoryBean();
		if(StringUtils.isNotBlank(alias))
			priKeyFactory.setAlias(alias);
		
		priKeyFactory.setKeystore(keystore);
		
		if(StringUtils.isNotBlank(password))
			priKeyFactory.setPassword(password);
		
		try {
			priKeyFactory.afterPropertiesSet();
			PrivateKey prikey = (PrivateKey) priKeyFactory.getObject();
			return prikey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableEntryException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
