/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stb.api.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author toantns26467
 */
public class PinRSA {
    static final int NUM_OF_BYTES_IN_FMT2_PIN_BLOCK = 8;
    static final int ENCODING_PARAMETER_SIZE_IN_BYTES = 16;
    static String HashMode;
    static int HashSize;
    /**
     * @param hash must be SHA1, SHA-256, SHA-384 or SHA-512
     * @return Parameter encoding and PIN encrypted
     */
    public static String[] Encrypt(String oldPINString, String newPINString, String exponent, String modulus, String RN, String hash) {
        HashMode = hash;
        HashSize = HashMode.equals("SHA-1") ? 20 : hash.equals("SHA-256") ? 32 : hash.equals("SHA-384") ? 48 : 64;
        String pinMessage = CreatePINMessage(oldPINString, newPINString, RN);
        String[] encoded = OAEPEncodedMessage(pinMessage, RN, modulus.length()/2 - 1 - HashSize);
        BigInteger value = OS2IP(encoded[1]);
        String result = value.modPow(OS2IP(exponent), OS2IP(modulus)).toString(16).toUpperCase();
        if (result.length() < modulus.length()) {
            result = StringUtils.repeat('0', modulus.length() - result.length()) + result;
        } else if (result.length() > modulus.length()) {
            result = result.substring(result.length() - modulus.length());
        }
        return new String[]{encoded[0], result};
    }
    /**
     * @param hash must be SHA1, SHA-256, SHA-384 or SHA-512
     * @return Parameter encoding and PIN encrypted
     */
    public static String[] Encrypt(String pinString, String exponent, String modulus, String RN, String hash) {
        HashMode = hash;
        HashSize = HashMode.equals("SHA-1") ? 20 : hash.equals("SHA-256") ? 32 : hash.equals("SHA-384") ? 48 : 64;
        String pinMessage = CreatePINMessage(pinString, RN);
        String[] encoded = OAEPEncodedMessage(pinMessage, RN, modulus.length()/2 - 1 - HashSize);
        BigInteger value = OS2IP(encoded[1]);
        String result = value.modPow(OS2IP(exponent), OS2IP(modulus)).toString(16).toUpperCase();
        if (result.length() < modulus.length()) {
            result = StringUtils.repeat('0', modulus.length() - result.length()) + result;
        } else if (result.length() > modulus.length()) {
            result = result.substring(result.length() - modulus.length());
        }
        return new String[]{encoded[0], result};
    }

    static String CreateFormat12PINBlock(String pinString) {
        int PINLength = pinString.length();
        int numberOfPINBlocks = PINLength <= 6 ? 1 : 2 + (PINLength - 7) / NUM_OF_BYTES_IN_FMT2_PIN_BLOCK;
        String pinCode = "";
        for (int i = 0; i < pinString.length(); i++) {
            pinCode += String.format("%02X", (int) pinString.charAt(i));
        }
        return StringUtils.rightPad("C1" + (PINLength <= 15 ? "0" : "") + Integer.toHexString(PINLength).toUpperCase() + pinCode, numberOfPINBlocks * NUM_OF_BYTES_IN_FMT2_PIN_BLOCK * 2, 'F');
    }

    static String CreatePINMessage(String pinString1, String pinString2, String RN) {
        return "02" + CreateFormat12PINBlock(pinString1) + CreateFormat12PINBlock(pinString2) + RN;
    }
    
    static String CreatePINMessage(String pinString, String RN) {
        return "01" + CreateFormat12PINBlock(pinString) + RN;
    }

    static String GetRandomString(int length) {
        String randomString = "";
        Random rnd = new Random();
        while (0 < length--) {
            randomString += Integer.toHexString(rnd.nextInt(16)).toUpperCase();
        }
        return randomString;
    }

    static String GetHash(String input) {
        try {
            return Byte2Hex(MessageDigest.getInstance(HashMode).digest(Hex2Byte(input)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String MGF(String input, int length) {
        int maxCount = length / (HashSize * 2);
        int remainingBytes = length - maxCount * HashSize * 2;
        if (remainingBytes > 0) {
            maxCount++;
        }
        int numberOfBytesToCopy = 2 * HashSize;
        String result = "";
        for (int counter = 0; counter < maxCount; counter++) {
            if (counter == (maxCount - 1) && remainingBytes > 0) {
                numberOfBytesToCopy = remainingBytes;
            }
            result += GetHash(input + StringUtils.repeat('0', 7) + Integer.toHexString(counter)).substring(0, numberOfBytesToCopy);
        }
        return result;
    }

    static String XOR(String input1, String input2) {
        return Byte2Hex(new BigInteger(input1, 16).xor(new BigInteger(input2, 16)).toByteArray());
    }

    static String[] OAEPEncodedMessage(String pinMessage, String RN, int dataSize) {
        int pinMsgLength = pinMessage.length() / 2;
        String P = GetRandomString(ENCODING_PARAMETER_SIZE_IN_BYTES * 2);
        //P = "46584B506BA5522051617153FAAC2CAF";
        //	DB = lHash || PS || 0x01 || M
        String DB = GetHash(P) + StringUtils.repeat('0', 2 * (dataSize - HashSize - pinMsgLength - 1)) + "01" + pinMessage;
        // generate a random octet String seed of length hLen (HASH_ALGO_SIZE_IN_BYTES*2)
        String seed = GetRandomString(HashSize * 2);
        //seed = "891FCF2CD3E59D7A050CCE317917B8BFDF065A69";
        //dbMask = MGF(seed, k � hLen � 1)
        String dbMask = MGF(seed, 2 * dataSize);
        // maskedDB = DB XOR dbMask
        String maskedDB = XOR(DB, dbMask);
        maskedDB = maskedDB.substring(maskedDB.length()-dbMask.length());
        // seedMask = MGF (maskedDB, hLen).
        String seedMask = MGF(maskedDB, 2 * HashSize);
        // maskedSeed = seed XOR seedMask.
        String maskedSeed = XOR(seed, seedMask);
        // EM = 0x00 || maskedSeed || maskedDB.
        return new String[]{P,  "00" + maskedSeed + maskedDB};
    }

    static BigInteger OS2IP(String input) {
        byte[] data = Hex2Byte(input);
        BigInteger out = new BigInteger("0");
        BigInteger twofiftysix = new BigInteger("256");
        for (int i = 1; i <= data.length; i++) {
            out = out.add((BigInteger.valueOf(0xFF & data[i - 1])).multiply(twofiftysix.pow(data.length - i)));
        }
        return out;
    }

    static String Byte2Hex(byte[] ba) {
        String hex = "";
        for (byte b : ba) {
            hex+=String.format("%02x", b);
        }
        return hex;
    }

    static byte[] Hex2Byte(String input) {
        byte[] output = new byte[input.length() / 2];
        for (int i = 0; i < input.length() / 2; i++) {
            output[i] = (byte) Integer.parseInt(input.substring(i * 2, i * 2 + 2), 16);
        }
        return output;
    }
}
