package algorithmtype.symmetric.aes.mode.cbc.padding.zeropadding;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;

import static burp.api.montoya.core.ByteArray.byteArray;

public class CustomSymmetricAesCbcZeroPaddingAlgorithmHandler {

    MontoyaApi api;
    Logging logging;
    Boolean isBase64 = true;
    //单例BouncyCastleProvider对象
    private final static BouncyCastleProvider bouncyCastleProvider;
    byte zeroByte = 0x00;

    public CustomSymmetricAesCbcZeroPaddingAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCbcZeroPaddingAlgorithmHandler(Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
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
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", bouncyCastleProvider);
            int blockSize = cipher.getBlockSize();
            cipher.init(isDecrypt, secretKeySpec, ivParameterSpec);

            if (data[data.length - 1] == zeroByte) {
                api.logging().logToOutput("The last byte of the original data is 0, which will cause the ZeroPadding padding method to decrypt the data missing!");
            }

            byte[] inputBytes;
            if (isBase64) {
                if (isDecrypt == Cipher.DECRYPT_MODE) {
                    inputBytes = api.utilities().base64Utils().decode(byteArray(data)).getBytes();
                } else {
                    inputBytes = encryptDataZeroPaddingHandler(blockSize, data);
                }
            } else {
                if (isDecrypt == Cipher.ENCRYPT_MODE) {
                    inputBytes = encryptDataZeroPaddingHandler(blockSize, data);
                } else {
                    inputBytes = data;
                }
            }

            // 执行加密/解密操作
            byte[] processedBytes = cipher.doFinal(inputBytes);

            byte[] result;
            if (isBase64) {
                if (isDecrypt == Cipher.ENCRYPT_MODE) {
                    // 如果是Base64编码并且是加密模式，先进行Base64编码再转换为字节数组
                    result = api.utilities().base64Utils().encode(byteArray(processedBytes)).getBytes();
                } else {
                    result = getRealBytes(blockSize, processedBytes);

                }
            } else {
                if (isDecrypt == Cipher.ENCRYPT_MODE) {
                    // 转换为字节数组
                    result = processedBytes;
                } else {
                    // 如果解密模式，先进行填充移除再转换为字节数组
                    result = getRealBytes(blockSize, processedBytes);
                }
            }

            return result;


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

    public byte[] encryptDataZeroPaddingHandler(int blockSize, byte[] data) {
        int dataLength = data.length;
        int addDataLength = blockSize - (dataLength % blockSize);
        if (dataLength % blockSize != 0) {
            dataLength = dataLength + addDataLength;
        }
        byte[] newData = new byte[dataLength];
        Arrays.fill(newData, zeroByte);
        System.arraycopy(data, 0, newData, 0, data.length);
        return newData;
    }

    public byte[] getRealBytes(int blockSize, byte[] src) {
        byte[] dest;
        if (src.length % blockSize == 0) {
            dest = src;
        } else {
            int index = 0;
            for (int i = src.length - 1; i >= 0; i--) {
                if (src[i] != zeroByte) {
                    index = i;
                    break;
                }
            }

            dest = new byte[index + 1]; // 创建目标数组，长度为要截取的长度
            System.arraycopy(src, 0, dest, 0, index + 1);
        }
        return dest;
    }

    static {
        bouncyCastleProvider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
    }

}
