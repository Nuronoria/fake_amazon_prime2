# User Login and Registration System

## Overview

This project implements a user login and registration system with enhanced security measures, including protection against SQL injection and encryption of sensitive information such as passwords.

## Encryption Details

### Purpose

The primary purpose of encryption in this project is to ensure that sensitive information, specifically user passwords, is not stored in clear text in the database. This enhances the security of the system by protecting user data from being compromised.

### How Encryption Works

1. **Encryption Algorithm**: The project uses the Advanced Encryption Standard (AES) algorithm to encrypt and decrypt data.
2. **Key Management**: A secret key is used to encrypt and decrypt the data. This key must be securely stored and managed.
3. **Encryption Process**: When a user registers, their password is encrypted before being saved to the database.
4. **Decryption Process**: When a user logs in, the entered password is encrypted and compared with the stored encrypted password to verify the user's identity.

### EncryptionUtil Class

The `EncryptionUtil` class handles the encryption and decryption processes. Below is the implementation:

```java
package com.mycompany.login;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "MySuperSecretKey".getBytes(); // Ensure this key is securely stored

    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
````
