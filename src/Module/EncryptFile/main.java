package Module.EncryptFile;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class main {
    public static void main(String[] args) throws Exception, CertificateException, KeyStoreException, NoSuchAlgorithmException, URISyntaxException, InvalidKeySpecException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        String privateKeyContent = new String(Files.readAllBytes(Paths.get("privateKey")));
        privateKeyContent = privateKeyContent.replaceAll("[\\r\\n]", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privKey);
        String msg = "text ecdsa with sha256";//getSHA256(msg)
        ecdsaSign.update(msg
                .getBytes(StandardCharsets.UTF_8));
        FileInputStream is = new FileInputStream("certificate");
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
        PublicKey key = cer.getPublicKey();
        ecdsaSign.initVerify(key);
        ecdsaSign.update(msg
                .getBytes(StandardCharsets.UTF_8));
        Security.addProvider(new BouncyCastleProvider());
        System.out.println(encryptKey(key, "1:170.0 169.0 125.0 134.0 150.0 139.0 235.0 182.0 156.0 120.0 229.0 122.0"));
//        System.out.println(decryptKey(privKey, "BNVhA140rm4RK6KwGMKxyAjyS6j9JfL0BSVBD00BcHyH9EC6lg9GTN7tT0rE2D5NeDRFDpajMNVgapiFp95PE7qNN3ToEKeSNbNMOt6CVwyEJ2Ua7PGQD+5AW/xeSvh1bMvP4zw="));
    }

    public static String encryptKey(PublicKey publicKey, String key) throws Exception {
        byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f");
        byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
        IESParameterSpec params = new IESParameterSpec(derivation, encoding, 128, 128, Hex.decode("000102030405060708090a0b0c0d0e0f"));
        Cipher iesCipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
        iesCipher.init(Cipher.ENCRYPT_MODE, publicKey, params);
        byte[] ciphertext = iesCipher.doFinal(key.getBytes());
        return new String(Base64.getEncoder().encode(ciphertext));
    }

    public static String decryptKey(PrivateKey privateKey, String encryptedKey) throws Exception {
        byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f");
        byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
        IESParameterSpec params = new IESParameterSpec(derivation, encoding, 128, 128, Hex.decode("000102030405060708090a0b0c0d0e0f"));
        Cipher iesCipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
        iesCipher.init(Cipher.DECRYPT_MODE, privateKey, params);
        byte[] plaintext = iesCipher.doFinal(Base64.getDecoder().decode(encryptedKey.getBytes()));
        return new String(plaintext);
    }
//
//    public static T mix(int a, int b) {
//        T result = new T();
//        result.a = a + b;
//        result.b = a + b;
//        return result;
//    }
}
//
//class T {
//    public int a;
//    public int b;
//}
