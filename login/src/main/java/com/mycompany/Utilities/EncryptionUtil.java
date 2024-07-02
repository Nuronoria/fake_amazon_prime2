package com.mycompany.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for encrypting and decrypting strings using the AES (Advanced Encryption Standard) algorithm.
 * 
 * <p>This class provides methods to encrypt and decrypt data using a symmetric key encryption algorithm (AES).
 * The encrypted data is encoded to Base64 for easy storage and transmission.</p>
 * 
 * <p><b>Note:</b> The encryption key is hardcoded in this example for simplicity, but in a real-world application,
 * keys should be securely stored and managed.</p>
 * 
 * <p>Usage:</p>
 * <pre>
 * String plainText = "SensitiveData";
 * String encryptedText = EncryptionUtil.encrypt(plainText);
 * String decryptedText = EncryptionUtil.decrypt(encryptedText);
 * </pre>
 * 
 * <p>Security considerations:</p>
 * <ul>
 * <li>Keys should be stored securely and not hardcoded in the code.</li>
 * <li>An initialization vector (IV) should be used with AES to ensure that identical plaintexts do not encrypt to identical ciphertexts.</li>
 * <li>Using a 256-bit key is recommended for stronger security, especially for sensitive data.</li>
 * </ul>
 * 
 * <p>Example:</p>
 * <pre>
 * String encryptedData = EncryptionUtil.encrypt("Hello, World!");
 * System.out.println("Encrypted: " + encryptedData);
 * String decryptedData = EncryptionUtil.decrypt(encryptedData);
 * System.out.println("Decrypted: " + decryptedData);
 * </pre>
 * 
 * <p>Ensure to handle exceptions appropriately in production code.</p>
 * 
 */
public class EncryptionUtil {

    private static String Old_KEY;

    // Read the key from the file and trim any whitespace
    static {
        try {
            Old_KEY = new String(Files.readAllBytes(Paths.get("../Secrets/AES_KEY.txt"))).trim();
        } catch (IOException e) {
            System.out.println("Error reading database credentials from files.");
            e.printStackTrace();
        }
    }

    private static final String ALGORITHM = "AES"; // Algorithm used for encryption
    private static final byte[] KEY = Old_KEY.getBytes(); // Convert the key string to byte array


    /**
     * Encrypts the given data using AES algorithm.
     *
     * @param data The plaintext data to be encrypted
     * @return The encrypted data encoded in Base64
     */

     public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM); // Create the secret key
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Create the cipher instance
        cipher.init(Cipher.ENCRYPT_MODE, secretKey); // Initialize the cipher in encrypt mode
        byte[] encryptedBytes = cipher.doFinal(data.getBytes()); // Perform encryption
        return Base64.getEncoder().encodeToString(encryptedBytes); // Return Base64-encoded ciphertext
    }
     /**
     * Decrypts the given Base64-encoded encrypted data using AES algorithm.
     *
     * @param encryptedData The Base64-encoded encrypted data
     * @return The decrypted plaintext data
     */

     public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM); // Create the secret key
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Create the cipher instance
        cipher.init(Cipher.DECRYPT_MODE, secretKey); // Initialize the cipher in decrypt mode
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData)); // Perform decryption
        return new String(decryptedBytes); // Return plaintext
    }
}