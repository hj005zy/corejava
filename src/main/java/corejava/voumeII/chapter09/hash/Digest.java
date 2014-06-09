package corejava.voumeII.chapter09.hash;

import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import util.ClassPathUtils;

/**
 * This program computes the message digest of a file.
 * @version 1.20 2012-06-16
 * @author Cay Horstmann
 */
public class Digest {

    /** 
     * @param args args[0] is the filename, args[1] is optionally the algorithm (SHA-1 or MD5)
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {

        String algname = args.length > 0 ? args[0] : "SHA-1";
        MessageDigest alg = MessageDigest.getInstance(algname);
        byte[] input = Files.readAllBytes(ClassPathUtils.getResourceAsPath(Digest.class, "input.txt"));
        byte[] hash = alg.digest(input);
        String d = "";
        for (byte element : hash) {
            int v = element & 0xFF;
            if (v < 16) {
                d += "0";
            }
            d += Integer.toString(v, 16).toUpperCase() + " ";
        }
        System.out.println(d);
    }
}
