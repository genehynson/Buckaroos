package com.buckaroos.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Class designed to provide a hashed version of the password for security
 * 
 * @author Jordan
 * @version 1.0
 */
public class PasswordHash {

    public static int PW_HASH_ITERATION_COUNT = 5000;
    private static MessageDigest md;

    public String hashPassword(String pw) {
        String salt = "rjelk;a903u29ujmfadkls;09432ujop;zad";
        byte[] bSalt;
        byte[] bPw;

        try {
            bSalt = salt.getBytes("UTF-8");
            bPw = pw.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding", e);
        }

        byte[] digest = run(bPw, bSalt);
        for (int i = 0; i < PW_HASH_ITERATION_COUNT - 1; i++) {
            digest = run(digest, bSalt);
        }

        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString((int) (b & 0xff)));
        }

        return sb.toString();
    }

    private static byte[] run(byte[] input, byte[] salt) {
        md.update(input);
        return md.digest(salt);
    }

}
