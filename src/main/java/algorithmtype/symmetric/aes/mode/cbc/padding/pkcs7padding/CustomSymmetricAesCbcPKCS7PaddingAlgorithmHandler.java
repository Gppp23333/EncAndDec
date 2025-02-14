package algorithmtype.symmetric.aes.mode.cbc.padding.pkcs7padding;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.ArrayList;

import static burp.api.montoya.core.ByteArray.byteArray;

public class CustomSymmetricAesCbcPKCS7PaddingAlgorithmHandler {

    MontoyaApi api;
    Logging logging;
    Boolean isBase64 = true;
    //单例BouncyCastleProvider对象
    private final static BouncyCastleProvider bouncyCastleProvider;

    public CustomSymmetricAesCbcPKCS7PaddingAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCbcPKCS7PaddingAlgorithmHandler(Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes;
        if (isDecrypt) {
            handlerBytes = decrypt(selectedBytes, paramHexValueList);
        } else {
            handlerBytes = encrypt(selectedBytes, paramHexValueList);
        }
        return handlerBytes;
    }

    public byte[] DecryptEncryptHandler(int isDecrypt, byte[] data, String keyHex, String ivHex) {
        try {
            // 初始化 IV
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivHex.getBytes());

            // 初始化 KEY
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyHex.getBytes(), "AES");

            // 初始化 AES 加密模块
            //使用前添加单例BouncyCastleProvider对象到JVM
            Security.addProvider(bouncyCastleProvider);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", bouncyCastleProvider);

            cipher.init(isDecrypt, secretKeySpec, ivParameterSpec);

            // 如果是 Base64 编码和解密操作，则进行解码操作，否则返回原始字节数据
            byte[] inputBytes = (isBase64 && isDecrypt == Cipher.DECRYPT_MODE)
                    ? api.utilities().base64Utils().decode(byteArray(data)).getBytes()
                    : data;

            // 执行加密/解密操作
            byte[] processedBytes = cipher.doFinal(inputBytes);

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
        String ivHex = paramHexValueList.get(1);
        isBase64 = Boolean.valueOf(paramHexValueList.get(paramHexValueList.size() - 1));

        return DecryptEncryptHandler(Cipher.DECRYPT_MODE, data, keyHex, ivHex);
    }

    public byte[] encrypt(byte[] data, ArrayList<String> paramHexValueList) {
        String keyHex = paramHexValueList.get(0);
        String ivHex = paramHexValueList.get(1);
        isBase64 = Boolean.valueOf(paramHexValueList.get(paramHexValueList.size() - 1));

        return DecryptEncryptHandler(Cipher.ENCRYPT_MODE, data, keyHex, ivHex);
    }

    static {
        bouncyCastleProvider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
    }

}
