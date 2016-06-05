package net.raysforge.crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

// http://download.oracle.com/javase/tutorial/security/apisign/step2.html
// http://www.faqs.org/rfcs/rfc3161.html

// http://de.wikipedia.org/wiki/X.509#Beispiel_f.C3.BCr_ein_X.509-Zertifikat
// http://de.wikipedia.org/wiki/PKCS

// P7S Bouncycastle
// http://www.jensign.com/JavaScience/javacrypto/VerifyP7s.txt
// http://www.thatsjava.com/java-programming/221275/

// P7S pure Java
// http://www.thatsjava.com/java-tech/85019/
// http://www.thatsjava.com/java-tech/64778/
// http://stackoverflow.com/questions/5167174/problems-with-pkcs7-file-validation

public class Signer {

	public static void main(String[] args) throws Exception {

		FileInputStream keyfis = new FileInputStream("priv.key");
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		keyfis.close();

		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
		PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);

		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(privKey);

		FileInputStream fis = new FileInputStream(args[0]);
		BufferedInputStream bufin = new BufferedInputStream(fis);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = bufin.read(buffer)) >= 0) {
			dsa.update(buffer, 0, len);
		}

		bufin.close();

		byte[] realSig = dsa.sign();

		File out = new File(args[0] + ".sig");
		if (!out.exists()) {
			FileOutputStream sigfos = new FileOutputStream(out);
			sigfos.write(realSig);
			sigfos.close();
		}

	}

	public Signer() {

	}

}
