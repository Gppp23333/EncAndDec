package algorithmtype.symmetric.aes.mode.cbc.padding.ansix923;


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

public class CustomSymmetricAesCbcANSIX923AlgorithmHandler {

    MontoyaApi api;
    Logging logging;
    Boolean isBase64 = true;
    //单例BouncyCastleProvider对象
    private final static BouncyCastleProvider bouncyCastleProvider;
    byte zeroByte = 0x00;

    public CustomSymmetricAesCbcANSIX923AlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCbcANSIX923AlgorithmHandler(Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
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
                api.logging().logToOutput("The last byte of the original data is 0, which will cause the ANSI X.923 padding method to decrypt the data missing!");
            }

            byte[] inputBytes;
            if (isBase64) {
                if (isDecrypt == Cipher.DECRYPT_MODE) {
                    inputBytes = api.utilities().base64Utils().decode(byteArray(data)).getBytes();
                } else {
                    inputBytes = encryptDataANSIX923Handler(blockSize, data);
                }
            } else {
                if (isDecrypt == Cipher.ENCRYPT_MODE) {
                    inputBytes = encryptDataANSIX923Handler(blockSize, data);
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

    public byte[] encryptDataANSIX923Handler(int blockSize, byte[] data) {
        int dataLength = data.length;
        int addDataLength = blockSize - (dataLength % blockSize);
        if (dataLength % blockSize != 0) {
            dataLength = dataLength + addDataLength;
        }
        byte[] newData = new byte[dataLength];

        Arrays.fill(newData, zeroByte);

        if (addDataLength > 0) {
            // 将计数转换为十六进制表示
            String hexValue = Integer.toHexString(addDataLength).toUpperCase();  // 转换为大写十六进制

            byte hexByte = (byte) Integer.parseInt(hexValue, 16);
            Arrays.fill(newData, newData.length - 1, newData.length, hexByte);
        }

        System.arraycopy(data, 0, newData, 0, data.length);
        return newData;
    }

    public byte[] getRealBytes(int blockSize, byte[] src) {

        byte[] dest;
        if (src.length % blockSize == 0) {
            dest = src;
        } else {
            // 获取最后一个元素对应的字符值，转换为需要移除的zeroByte个数
            byte lastElement = src[src.length - 1];
            int value = (char) lastElement;
            // 移除从后往前的zeroByte元素数量
            int endIndex = src.length - value;// 去除最后一个元素及其后的zeroByte元素
            // 创建新的数组，只复制有效的部分
            dest = Arrays.copyOf(src, endIndex);
        }

        return dest;
    }

    static {
        bouncyCastleProvider = new BouncyCastleProvider();
    }

}

