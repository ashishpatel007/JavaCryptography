/**
 * Java Cryptography - Retrieving Keys
 * https://www.tutorialspoint.com/java_cryptography/java_cryptography_retrieving_keys.htm
 */
package RetrievingKeys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;

public class RetrievingFromKeyStore {

    public static void main(String[] args) throws Exception {
        // Creating the Keystore object
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        // Loading the KeyStore object
        char[] password = "changeit".toCharArray();
        FileInputStream fis = new FileInputStream("/Library/Java/JavaVirtualMachines/jdk-11.0.6.jdk/Contents/Home/lib/security/cacerts");

        keyStore.load(fis,password);

        // Creating the KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        // Creating SecretKey object
        SecretKey mySecretKey = new SecretKeySpec("myPassword".getBytes(),"DSA");

        // Creating SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias",secretKeyEntry,protectionParam);

        // Storing the Keystore object
        FileOutputStream fos = null;
        fos = new FileOutputStream("newKeyStoreName");
        keyStore.store(fos, password);

        // Creating the KeyStore.SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEnt = (KeyStore.SecretKeyEntry) keyStore.getEntry("secretKeyAlias", protectionParam);

        // Creating SecretKey object
        mySecretKey = secretKeyEnt.getSecretKey();
        System.out.println("Algorithm used to generate key : " + mySecretKey.getAlgorithm());
        System.out.println("Format used for the key: " + mySecretKey.getFormat());


    }
}
