package algorithmtype.symmetric.aes.mode.gcm.padding.nopadding;


import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;

import static burp.api.montoya.core.ByteArray.byteArray;

public class CustomSymmetricAesGcmNoPaddingAlgorithmHandler {

    MontoyaApi api;
    Logging logging;
    Boolean isBase64 = true;

    public CustomSymmetricAesGcmNoPaddingAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesGcmNoPaddingAlgorithmHandler(Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes;
        if (isDecrypt) {
            handlerBytes = decrypt(selectedBytes, paramHexValueList);
        } else {
            handlerBytes = encrypt(selectedBytes, paramHexValueList);
        }
        return handlerBytes;
    }

    public byte[] DecryptEncryptHandler(int isDecrypt, byte[] data, String keyHex, String ivHex, String associatedDataHex) {
        try {
            // 初始化 AssociatedData
            byte[] associatedDataBytes = associatedDataHex.getBytes();

            // 初始化 IV 12字节
            byte[] iv = ivHex.getBytes();

            // 初始化 KEY
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyHex.getBytes(), "AES");

            // 初始化 AES 加密模块
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(associatedDataBytes.length, iv);
            cipher.init(isDecrypt, secretKeySpec, gcmParameterSpec);

            // 如果是 Base64 编码和解密操作，则进行解码操作，否则返回原始字节数据
            byte[] inputBytes = (isBase64 && isDecrypt == Cipher.DECRYPT_MODE)
                    ? api.utilities().base64Utils().decode(byteArray(data)).getBytes()
                    : data;

            if (isDecrypt == Cipher.DECRYPT_MODE) {
                if (Arrays.equals(iv, Arrays.copyOfRange(inputBytes, 0, 12))) {
                    // 密文中iv与传入iv相同时，分离IV和密文
                    inputBytes = Arrays.copyOfRange(inputBytes, 12, inputBytes.length);
                }
            }

            cipher.updateAAD(associatedDataBytes);

            // 执行加密/解密操作
            byte[] processedBytes = cipher.doFinal(inputBytes);

            byte[] encryptedIvAndText;
            if (isDecrypt == Cipher.ENCRYPT_MODE) {
                encryptedIvAndText = new byte[iv.length + processedBytes.length];
                System.arraycopy(iv, 0, encryptedIvAndText, 0, iv.length);
                System.arraycopy(processedBytes, 0, encryptedIvAndText, iv.length, processedBytes.length);
                processedBytes = encryptedIvAndText;
            }
            // 如果是加密模式，返回 Base64 编码结果
            return isBase64 && isDecrypt == Cipher.ENCRYPT_MODE
                    ? api.utilities().base64Utils().encode(byteArray(processedBytes)).getBytes()
                    : processedBytes;
        } catch (Exception e) {
            logging.logToError(e);
            throw new RuntimeException("Encryption/Decryption failed:", e);
        }
    }

    public byte[] decrypt(byte[] data, ArrayList<String> paramHexValueList) {
        String keyHex = paramHexValueList.get(0);
        String nonceHex = paramHexValueList.get(1);
        String associatedDataHex = paramHexValueList.get(2);
        isBase64 = Boolean.valueOf(paramHexValueList.get(paramHexValueList.size() - 1));

        return DecryptEncryptHandler(Cipher.DECRYPT_MODE, data, keyHex, nonceHex, associatedDataHex);
    }

    public byte[] encrypt(byte[] data, ArrayList<String> paramHexValueList) {
        String keyHex = paramHexValueList.get(0);
        String nonceHex = paramHexValueList.get(1);
        String associatedDataHex = paramHexValueList.get(2);
        isBase64 = Boolean.valueOf(paramHexValueList.get(paramHexValueList.size() - 1));

        return DecryptEncryptHandler(Cipher.ENCRYPT_MODE, data, keyHex, nonceHex, associatedDataHex);
    }
}

