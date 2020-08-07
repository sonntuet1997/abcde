package Module.EncryptKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Created by Son on 6/15/2017.
 */
public class EncryptKeyService {
//    private static SessionFactory factory;
//    private static int currentActive;
//    private byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f");
//    private byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
//
//    public EncryptKeyService(SessionFactory factory) {
//        this.factory = factory;
//    }
//
//    public EncryptKeyService() {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    public static void setFactory(SessionFactory factory) {
//        EncryptKeyService.factory = factory;
//    }
//
//    public CryptoEntity encrypt(CryptoEntity cryptoEntity) {
//        try {
//            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
//            InputStream inputStream = new ByteArrayInputStream(cryptoEntity.certificate.getBytes(Charset.forName("UTF-8")));
//            CertificateFactory fact = CertificateFactory.getInstance("X.509");
//            X509Certificate cer = (X509Certificate) fact.generateCertificate(inputStream);
//            PublicKey key = cer.getPublicKey();
//            ecdsaSign.initVerify(key);
//            ecdsaSign.update(cryptoEntity.data
//                    .getBytes(StandardCharsets.UTF_8));
//            cryptoEntity.data = encryptKey(key, cryptoEntity.data);
//            return cryptoEntity;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public CryptoEntity[] encryptAll(CryptoEntity[] cryptoEntities) {
//        try {
//            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
//            for (CryptoEntity cryptoEntity : cryptoEntities) {
//                InputStream inputStream = new ByteArrayInputStream(cryptoEntity.certificate.getBytes(Charset.forName("UTF-8")));
//                CertificateFactory fact = CertificateFactory.getInstance("X.509");
//                X509Certificate cer = (X509Certificate) fact.generateCertificate(inputStream);
//                PublicKey key = cer.getPublicKey();
//                ecdsaSign.initVerify(key);
//                ecdsaSign.update(cryptoEntity.data
//                        .getBytes(StandardCharsets.UTF_8));
//                cryptoEntity.data = encryptKey(key, cryptoEntity.data);
//            }
//            return cryptoEntities;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public CryptoEntity decrypt(CryptoEntity cryptoEntity) {
//        try {
//            cryptoEntity.privateKey = cryptoEntity.privateKey.replaceAll("[\\r\\n]", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
//            KeyFactory kf = KeyFactory.getInstance("EC");
//            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(cryptoEntity.privateKey));
//            PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
////            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
////            ecdsaSign.initSign(privKey);
//            cryptoEntity.data = decryptKey(privKey, cryptoEntity.data);
//            return cryptoEntity;
//        } catch (Exception e) {
//            cryptoEntity.data = null;
//            return cryptoEntity;
//        }
//    }
//
//    private String encryptKey(PublicKey publicKey, String key) throws Exception {
//        IESParameterSpec params = new IESParameterSpec(derivation, encoding, 128, 128, Hex.decode("000102030405060708090a0b0c0d0e0f"));
//        Cipher iesCipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
//        iesCipher.init(Cipher.ENCRYPT_MODE, publicKey, params);
//        byte[] ciphertext = iesCipher.doFinal(key.getBytes());
//        return new String(Base64.getEncoder().encode(ciphertext));
//    }
//
//    private String decryptKey(PrivateKey privateKey, String encryptedKey) throws Exception {
//        IESParameterSpec params = new IESParameterSpec(derivation, encoding, 128, 128, Hex.decode("000102030405060708090a0b0c0d0e0f"));
//        Cipher iesCipher = Cipher.getInstance("ECIESwithAES-CBC", "BC");
//        iesCipher.init(Cipher.DECRYPT_MODE, privateKey, params);
//        byte[] plaintext = iesCipher.doFinal(Base64.getDecoder().decode(encryptedKey.getBytes()));
//        return new String(plaintext);
//    }
//
//    public CryptoEntity sign(CryptoEntity cryptoEntity) {
//        try {
//            cryptoEntity.privateKey = cryptoEntity.privateKey.replaceAll("[\\r\\n]", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
//            KeyFactory kf = KeyFactory.getInstance("EC");
//            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(cryptoEntity.privateKey));
//            PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
//            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
//            ecdsaSign.initSign(privKey);
//            ecdsaSign.update(cryptoEntity.data.getBytes(StandardCharsets.UTF_8));
//            byte[] realSig = ecdsaSign.sign();
//            cryptoEntity.sign = Base64.getEncoder().encodeToString(realSig);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cryptoEntity;
//    }
//
//    public CryptoEntity test(String file) {
//        return null;
//    }
//
//    public boolean verify(CryptoEntity cryptoEntity) {
//        try {
//            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
//            InputStream inputStream = new ByteArrayInputStream(cryptoEntity.certificate.getBytes(Charset.forName("UTF-8")));
//            CertificateFactory fact = CertificateFactory.getInstance("X.509");
//            X509Certificate cer = (X509Certificate) fact.generateCertificate(inputStream);
//            PublicKey key = cer.getPublicKey();
//            ecdsaSign.initVerify(key);
//            ecdsaSign.update(cryptoEntity.data.getBytes(StandardCharsets.UTF_8));
//            boolean c = ecdsaSign.verify(Base64.getDecoder().decode(cryptoEntity.sign.getBytes()));
//            return c;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
