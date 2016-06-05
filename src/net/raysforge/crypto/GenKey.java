package net.raysforge.crypto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

// http://download.oracle.com/javase/tutorial/security/apisign/step2.html

public class GenKey {

	/**
	 * @param args
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");

		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(1024, random);

		KeyPair pair = keyGen.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();

		byte[] key = pub.getEncoded();
		FileOutputStream keyfos = new FileOutputStream("pub.key");
		keyfos.write(key);
		keyfos.close();

		key = priv.getEncoded();
		keyfos = new FileOutputStream("priv.key");
		keyfos.write(key);
		keyfos.close();

	}

}
