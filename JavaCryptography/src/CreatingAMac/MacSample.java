/**
 * Java Cryptography - Creating a Mac
 * https://www.tutorialspoint.com/java_cryptography/java_cryptography_creating_mac.htm
 */
package CreatingAMac;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.security.Key;
import java.security.SecureRandom;

public class MacSample {

    public static void main(String[] args) throws Exception {
        // Creating a KeyGenerator Object
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");

        // Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();

        // Initializing the KeyGenerator
        keyGen.init(secRandom);

        // Creating / Generating a key
        Key key = keyGen.generateKey();
        
        // Creating a Mac object
        Mac mac = Mac.getInstance("HmacSHA256");

        // Initializing the Mac object
        mac.init(key);

        // Computing the Mac
        String msg = new String("Hi how are you?");
        byte[] bytes = msg.getBytes();
        byte[] macResult = mac.doFinal(bytes);

        System.out.println("Mac Result: ");
        System.out.println(new String(macResult));

    }
}
