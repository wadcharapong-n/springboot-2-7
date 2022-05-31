package com.north.poc.utility;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j(topic = "application")
@Component
public final class RSACryptoUtil {
	
	
	public static final String SHA256WITHRSA = "SHA256withRSA";
	
	public static final String BOUNCYCASTLE = "SHA256withRSA";

	public RSACryptoUtil() {
		Security.addProvider(new BouncyCastleProvider());
	}

	public String encryptWithPrivateKey(PrivateKey privateKey, String message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return new String(Base64.encode(encrypted));
	}

	public String decryptWithPublicKey(PublicKey publicKey, String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] cipherText = Base64.decode(encrypted.getBytes());
		return new String(cipher.doFinal(cipherText));
	}

	public String encryptWithPublicKey(PublicKey publicKey, String message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return new String(Base64.encode(encrypted));
	}

	public String decryptWithPrivateKey(PrivateKey privateKey, String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cipherText = Base64.decode(encrypted.getBytes());
		return new String(cipher.doFinal(cipherText));
	}
	
	public String sign(PrivateKey privateKey,String message) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Signature dsa = Signature.getInstance(SHA256WITHRSA, "BC"); 
		dsa.initSign(privateKey);
		dsa.update(message.getBytes());
		byte[] signed = dsa.sign();
		return new String(signed);
	}
	
	public boolean verify(PublicKey publicKey,String message,String signed) throws Exception, NoSuchProviderException {
		Signature sig = Signature.getInstance(SHA256WITHRSA,BOUNCYCASTLE);
		sig.initVerify(publicKey);
		sig.update(message.getBytes());
		
		return sig.verify(signed.getBytes());
	} 

	public PrivateKey generatePrivateKey(InputStream inputStream)
			throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
			PemFile pemFile = new PemFile(inputStream);
			byte[] content = pemFile.getPemObject().getContent();
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
//            X509EncodedKeySpec privKeySpec = new X509EncodedKeySpec(content);
			return factory.generatePrivate(privKeySpec);
		} catch (FileNotFoundException ex) {
			log.error("Error : private key file can not be found ", ex);
			return null;
		}
	}

	public PublicKey generatePublicKey(InputStream inputStream) throws InvalidKeySpecException, FileNotFoundException,
			IOException, NoSuchAlgorithmException, NoSuchProviderException {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
			PemFile pemFile = new PemFile(inputStream);
			byte[] content = pemFile.getPemObject().getContent();
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
			return factory.generatePublic(pubKeySpec);
		} catch (FileNotFoundException ex) {
			log.error("Error : public key file can not be found ", ex);
			return null;
		}
	}



	public static void generateRSAKeypair(int keySize)
			throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException, IOException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		generator.initialize(keySize);

		KeyPair keyPair = generator.generateKeyPair();
		log.info("RSA key pair generated.");

		RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();

		writePemFile(priv, "RSA PRIVATE KEY", "id_rsa");
		writePemFile(pub, "RSA PUBLIC KEY", "id_rsa.pub");

	}

	private static void writePemFile(Key key, String description, String filename) throws FileNotFoundException, IOException {
		PemFile pemFile = new PemFile(key, description);
		pemFile.write(filename);
		log.info(String.format("%s successfully writen in file %s.", description, filename));
	}

	
	
	
	public static class PemFile {

		private PemObject pemObject;

		public PemFile(Key key, String description) {
			this.pemObject = new PemObject(description, key.getEncoded());
		}

		public PemFile(InputStream inputStream) throws FileNotFoundException, IOException {
			PemReader pemReader = new PemReader(new InputStreamReader(inputStream));
			try {
				this.pemObject = pemReader.readPemObject();
			} finally {
				pemReader.close();
			}
		}

		public void write(String filename) throws FileNotFoundException, IOException {
			PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			try {
				pemWriter.writeObject(this.pemObject);
			} finally {
				pemWriter.close();
			}
		}

		public PemObject getPemObject() {
			return pemObject;
		}
	}
}