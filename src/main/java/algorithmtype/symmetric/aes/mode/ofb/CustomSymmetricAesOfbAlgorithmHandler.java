package algorithmtype.symmetric.aes.mode.ofb;

import algorithmtype.symmetric.aes.mode.ofb.padding.ansix923.CustomSymmetricAesOfbANSIX923AlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.padding.iso10126padding.CustomSymmetricAesOfbISO10126PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.padding.nopadding.CustomSymmetricAesOfbNoPaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.padding.pkcs5padding.CustomSymmetricAesOfbPKCS5PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.padding.pkcs7padding.CustomSymmetricAesOfbPKCS7PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.padding.zeropadding.CustomSymmetricAesOfbZeroPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesOfbAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesOfbAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesOfbAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        switch (algorithmPadding) {
            case "ZeroPadding" -> {
                CustomSymmetricAesOfbZeroPaddingAlgorithmHandler customSymmetricAesOfbZeroPaddingAlgorithmHandler = new CustomSymmetricAesOfbZeroPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbZeroPaddingAlgorithmHandler.SymmetricAesOfbZeroPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ANSIX923" -> {
                CustomSymmetricAesOfbANSIX923AlgorithmHandler customSymmetricAesOfbANSIX923AlgorithmHandler = new CustomSymmetricAesOfbANSIX923AlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbANSIX923AlgorithmHandler.SymmetricAesOfbANSIX923AlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ISO10126Padding" -> {
                CustomSymmetricAesOfbISO10126PaddingAlgorithmHandler customSymmetricAesOfbISO10126PaddingAlgorithmHandler = new CustomSymmetricAesOfbISO10126PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbISO10126PaddingAlgorithmHandler.SymmetricAesOfbISO10126PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "NoPadding" -> {
                CustomSymmetricAesOfbNoPaddingAlgorithmHandler customSymmetricAesOfbNoPaddingAlgorithmHandler = new CustomSymmetricAesOfbNoPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbNoPaddingAlgorithmHandler.SymmetricAesOfbNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS5Padding" -> {
                CustomSymmetricAesOfbPKCS5PaddingAlgorithmHandler customSymmetricAesOfbPKCS5PaddingAlgorithmHandler = new CustomSymmetricAesOfbPKCS5PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbPKCS5PaddingAlgorithmHandler.SymmetricAesOfbPKCS5PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS7Padding" -> {
                CustomSymmetricAesOfbPKCS7PaddingAlgorithmHandler customSymmetricAesOfbPKCS7PaddingAlgorithmHandler = new CustomSymmetricAesOfbPKCS7PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbPKCS7PaddingAlgorithmHandler.SymmetricAesOfbPKCS7PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
        }

        return handlerBytes;
    }
}
