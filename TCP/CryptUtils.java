import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
keytool -genseckey -keystore aes-keystore.jck -storetype jceks -storepass mystorepass -keyalg AES -keysize 256 -alias jceksaes -keypass mykeypass
InputStream keystoreStream = new FileInputStream(keystoreLocation);

KeyStore keystore = KeyStore.getInstance("JCEKS");
keystore.load(keystoreStream, keystorePass.toCharArray());

if (!keystore.containsAlias(alias))
{
    throw new RuntimeException("Alias for key not found");
}

Key key = keystore.getKey(alias, keyPass.toCharArray());
*/

public class CryptUtils
{
    public static byte[] encrypt(String key, String decrypted)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec spec = new IvParameterSpec(new byte[ cipher.getBlockSize() ]);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, spec);

            return cipher.doFinal(decrypted.getBytes("UTF-8"));
        }
        catch (Exception ex)
        {
            System.err.println("++> " + ex);
        }

        return null;
    }

    public static byte[] decrypt(String key, byte[] encrypted)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec spec = new IvParameterSpec(new byte[ cipher.getBlockSize() ]);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, spec);

            return cipher.doFinal(encrypted);
        }
        catch (Exception ex)
        {
            System.err.println("++> " + ex);
        }

        return null;
    }

    public static void main(String[] args)
    {
        String plaintext = "PupsNasenBaer"; // Note null padding
        String encryptionKey = "0123456789abcdef";

        byte[] crypt = encrypt(encryptionKey, plaintext);

        try
        {
            String value = new String(crypt, "UTF-8");

            System.out.println(value);

            value = new String(decrypt(encryptionKey, crypt), "UTF-8");
            System.out.println(value);
        }
        catch (Exception ex)
        {
            System.err.println("++> " + ex);
        }
    }
}
