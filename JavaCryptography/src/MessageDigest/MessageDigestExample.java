/**
 * Java Cryptography - Creating a Message Digest
 */
package MessageDigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MessageDigestExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //Reading data from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the message");
        String message = sc.nextLine();

        // Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Passing data to the created MessageDigest object
        md.update(message.getBytes());

        // Compute the message digest
        byte[] digest = md.digest();
        System.out.println(digest);

        // Converting the byte array into HexString format
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0XFF & digest[i]));
        }
        System.out.println("Hex format : " + hexString.toString());
    }
}
