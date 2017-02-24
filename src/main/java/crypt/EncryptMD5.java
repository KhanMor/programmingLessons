package crypt;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mordr on 23.02.2017.
 */
public class EncryptMD5 {
    private static  final String MD5 = "MD5";
    private static final Logger logger = Logger.getLogger(EncryptMD5.class);

    public static String encrypt(String string){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] passBytes = string.getBytes();
            messageDigest.reset();
            byte[] digested = messageDigest.digest(passBytes);
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < digested.length; i++){
                stringBuffer.append(Integer.toHexString(0xff & digested[i]));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return null;
    }
}
