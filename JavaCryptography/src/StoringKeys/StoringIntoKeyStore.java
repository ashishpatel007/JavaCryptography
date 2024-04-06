/**
 * https://www.tutorialspoint.com/java_cryptography/java_cryptography_storing_keys.htm
 * The following example stores keys into the keystore existing in the "cacerts" file
 * which is stored here on the Mac:
 * /Library/Java/JavaVirtualMachines/jdk-11.0.6.jdk/Contents/Home/lib/security/cacerts
 *
 */
package StoringKeys;

import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;

public class StoringIntoKeyStore {

    public static void main(String[] args) throws Exception {

        // Load keyStore

        // Creating the KeyStore
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        // Loading the KeyStore object
        char[] password = "changeit".toCharArray();
        String path = "/Library/Java/JavaVirtualMachines/jdk-11.0.6.jdk/Contents/Home/lib/security/cacerts";
        FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);

        // Creating the KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        // Creating a SecretKey object
        SecretKey mySecretKey = new SecretKeySpec("myPassword".getBytes(),"DSA");

        // Creating SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(mySecretKey);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);

        // Storing the KeyStore object
        FileOutputStream fos = null;
        fos = new FileOutputStream("newKeyStoreName");
        keyStore.store(fos, password);
        System.out.println("data stored.");




    }

    private KeyStore loadKeyStore() {
        String relativeCacertsPath = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + relativeCacertsPath;
        try {
            FileInputStream is = new FileInputStream(filename);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = "changeit";
            keyStore.load(is, password.toCharArray());

            return keyStore;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void whenLoadingCacertsKeyStore_thenCertificateArePresent() throws InvalidAlgorithmParameterException, KeyStoreException {
        KeyStore keyStore = loadKeyStore();
        PKIXParameters params = new PKIXParameters(keyStore);

        Set<TrustAnchor> trustAnchors = params.getTrustAnchors();
        List<Certificate> certificates = trustAnchors.stream()
                .map(TrustAnchor::getTrustedCert)
                .collect(Collectors.toList());

        assertFalse(certificates.isEmpty());
    }


}
