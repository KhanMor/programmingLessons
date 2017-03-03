package crypt;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mordr on 23.02.2017.
 * Шифрование строки по MD5
 */
public class EncryptMD5 {
    private static final Logger logger = Logger.getLogger(EncryptMD5.class);

    public static String encrypt(String string){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] passBytes = string.getBytes();
            messageDigest.reset();
            byte[] digested = messageDigest.digest(passBytes);
            StringBuilder stringBuilder;
            stringBuilder = new StringBuilder();
            for (byte aDigested : digested) {
                stringBuilder.append(Integer.toHexString(0xff & aDigested));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return null;
    }
}
