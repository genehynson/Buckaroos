package com.buckaroos.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class designed to provide a hashed version of the password for security
 * 
 * @author Jordan
 * @version 1.0
 */
public class PasswordHash {

    private MessageDigest md;

    public String hashPassword(String pw) {
        StringBuffer sb = new StringBuffer();
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] digest = md.digest();
            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
        } catch (NoSuchAlgorithmException e1) {
            // Do Nothing
        }
        return sb.toString();
    }

}
