package Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import model.Licence;

public class LicenceGenerator {
	
	// Algorithmes utilisés
	private static final String KEY_ALGORITHM	= "EC";
	private static final String SIGN_ALGORITHM	= "SHA256withECDSA";
	// private static final String USED_CURVE		= "secp521r1";
	
	// Couple de clés temporaires (pour test, à remplacer plus tard)
	private static final String PUBLIC_KEY_FILE		= "/home/louka/temp_key/public.pem";
	private static final String PRIVATE_KEY_FILE 	= "/home/louka/temp_key/pkcs8-private.pem";
    // (bien utilisé la clé au format PKCS8)
	
	// Séparateur entre les champs de la licence
	private static final String SEP = ":";
	
	/*
	 * generate: genère le fichier de licence avec la licence passée
	 * en paramètre et le hash des données hardware du client.
	 */
	public static String generate(Licence licence) {
		String toSign = licence.getHardwareId()
				+ SEP + licence.getSoftwareId()
				+ SEP + licence.getValidity();
		
		PrivateKey sk = getPrivateKey(PRIVATE_KEY_FILE);
		String signature = sign(sk, toSign);
		
		String signatureFile = String.join(System.lineSeparator(),
				"--- Signature file ---",
				signature,
				"Until: " + licence.getValidity(),
				"--- End Signature file ---"
		);
		return signatureFile;
	}
	
	// Méthodes outils
	
	/*
	 * Signe la chaîne de caractères toSign avec la clé privée sk.
	 * L'algorithme utilisé est ECDSA avec la courbe elliptique : secp521r1.
	 * Renvoie la signature sous forme de chaîne de caractère encodée
	 * en base64.
	 */
	private static String sign(PrivateKey sk, String toSign) {
		String resSign = null;
		try {
			Signature ecdsaSign = Signature.getInstance(SIGN_ALGORITHM);
			ecdsaSign.initSign(sk);
			ecdsaSign.update(toSign.getBytes());
			
			byte[] signature = ecdsaSign.sign();
			resSign = Base64.getEncoder().encodeToString(signature);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return resSign;
	}
	
	/*
	 * Vérifie la signature signature correspond à la chaîne de caractère toCheck
	 * avec la clé publique pk.
	 * Renvoie vrai si la signature est correct, faux sinon.
	 */
	private static boolean verify(PublicKey pk, String toCheck, String signature) {
		boolean result = false;
		try {
			Signature ecdsaVerify = Signature.getInstance(SIGN_ALGORITHM);
			ecdsaVerify.initVerify(pk);
			ecdsaVerify.update(toCheck.getBytes());
			
			result = ecdsaVerify.verify(Base64.getDecoder().decode(signature));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static PublicKey getPublicKey(String pathToKeyFile) {
		PublicKey pk = null;
		try {
			// Lecture du fichier
			String keyFile = Files.readString(Paths.get(pathToKeyFile), Charset.defaultCharset());
			// Récupération de la clé
			String key = keyFile.replace("-----BEGIN PUBLIC KEY-----", "")
				      .replaceAll(System.lineSeparator(), "")
				      .replace("-----END PUBLIC KEY-----", "");
			
			// Décode depuis base64
			byte[] keyBytes = Base64.getDecoder().decode(key);
			// Genère la clé à partir d'une keyFactory selon un algorithme et une keySpec
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			pk = keyFactory.generatePublic(keySpec);
			
		} catch (IOException e) {
			// erreur de lecture
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// l'algorithme demandé n'existe pas
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// L'encodage de clé n'existe pas
			e.printStackTrace();
		}
		return pk;
	}
	
	private static PrivateKey getPrivateKey(String pathToKeyFile) {
		PrivateKey sk = null;
		try {
			// Lecture du fichier
			String keyFile = Files.readString(Paths.get(pathToKeyFile), Charset.defaultCharset());
			// Récupération de la clé
			String key = keyFile.replace("-----BEGIN PRIVATE KEY-----", "")
				    .replaceAll(System.lineSeparator(), "")
				    .replace("-----END PRIVATE KEY-----", "");
			
			// Décode depuis base64
			byte[] keyBytes = Base64.getDecoder().decode(key);
			// Genère la clé à partir d'une keyFactory, selon un algorithme et une keySpec
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			sk = keyFactory.generatePrivate(keySpec);

		} catch (IOException e) {
			// erreur de lecture
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// l'algorithme demandé n'existe pas
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// L'encodage de clé n'existe pas
			e.printStackTrace();
		}
		return sk;
	}
	
	// Test de la signature & vérification
	
	public static void main(String[] args) throws IOException {
		System.out.println("Get the public and private key...");
		long startTime = System.currentTimeMillis();
		
		// récupère les clés
		PrivateKey sk = getPrivateKey(PRIVATE_KEY_FILE);
		PublicKey pk = getPublicKey(PUBLIC_KEY_FILE);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Key generation execution time: "
					+ (endTime - startTime) + " ms");
		
		// récupère le contenu du fichier de test
		System.out.println("Read file content...");
		String toSign = Files.readString(Paths.get("/home/louka/temp_key/testfile.txt"),
				Charset.defaultCharset());
		
		// mélange pour s'assurer qu'il ne signe pas la même chose à
		// chaque et optimise par conséquent
		List<String> toSignList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			List<String> characters = Arrays.asList(toSign.split(""));
	 		Collections.shuffle(characters);
	 		toSignList.add(characters.toString());
	 		System.out.println(toSignList.get(i));
		}
		
		startTime = System.currentTimeMillis();
		
		// effectue 10 signatures et vérification
		for (int i = 0; i < 10; i++) {
			System.out.println("Sign the " + i + "-th string...");
			String signature = sign(sk, toSignList.get(i));
			
			System.out.println("Verify the signature...");
			boolean verify = verify(pk, toSignList.get(i), signature);
			if (verify) {
				System.out.println("Correct signature !");
			} else {
				System.out.println("Inorrect signature !");
			}
		}
		
		endTime = System.currentTimeMillis();
		System.out.println("Signature (10 iterations) execution time: "
					+ (endTime - startTime) + " ms");
	}
}
