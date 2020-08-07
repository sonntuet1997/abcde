package Module.EncryptFile;

import Module.EncryptKey.CryptoEntity;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

import static Module.EncryptFile.Encryptor.encryptFileService;

/**
 * Created by Son on 6/15/2017.
 */
public class EncryptFileService {
    private String initVector = "RandomInitVector";

    public EncryptFileService() {

    }

    public static byte[] getRandomKey() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);
        return array;
    }

    public void encryptAndHash(InputStream in, byte[] key, OutputStream out) {
        try {
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(initVector.getBytes());
            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, paramSpec);
            // Bytes written to out will be message
            out = new CipherOutputStream(out, ecipher);
            // Read in the cleartext bytes and write to out to encryptAndHash
            int numRead = 0;
//            byte[] buf = new byte[8192];
            byte[] buf = new byte[1048576];
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(InputStream in, byte[] key, OutputStream out) {
        try {
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(initVector.getBytes());
            Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            dcipher.init(Cipher.DECRYPT_MODE, skeySpec, paramSpec);
            // Bytes read from in will be decrypted
            in = new CipherInputStream(in, dcipher);
//            byte[] buf = new byte[8192];
            byte[] buf = new byte[1048576];
            // Read in the decrypted bytes and write the cleartext to out
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (Exception ignored) {
        }
    }

    public void test(String file) {
        byte[] key = getRandomKey();
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Paths.get(file));
            PrintStream ps = new PrintStream(System.out,true,"UTF8");
            encryptFileService.encryptAndHash(inputStream,key,ps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
