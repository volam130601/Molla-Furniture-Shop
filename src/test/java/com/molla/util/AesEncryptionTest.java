package com.molla.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AesEncryptionTest {
    @Autowired
    private AesEncryption aesEncryption;
    @Test
    void testAES() {
        String secretKey = "EMAIL";
        String originalString = "l2323am2131231321lbx123@gmail.com";
        String encryptedString = aesEncryption.encrypt(originalString, secretKey);
        System.out.println("Encrypt: " + encryptedString);
        String decryptedString = aesEncryption.decrypt("6DEaFGWJXPLCDm9VpFTlItjxI IskNIqUCE16CR YLA=", secretKey);
        System.out.println("Decrypt: " + decryptedString);
    }
}