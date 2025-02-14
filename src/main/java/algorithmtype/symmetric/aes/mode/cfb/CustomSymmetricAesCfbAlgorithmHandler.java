package algorithmtype.symmetric.aes.mode.cfb;

import algorithmtype.symmetric.aes.mode.cfb.padding.ansix923.CustomSymmetricAesCfbANSIX923AlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.padding.iso10126padding.CustomSymmetricAesCfbISO10126PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.padding.nopadding.CustomSymmetricAesCfbNoPaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.padding.pkcs5padding.CustomSymmetricAesCfbPKCS5PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.padding.pkcs7padding.CustomSymmetricAesCfbPKCS7PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.padding.zeropadding.CustomSymmetricAesCfbZeroPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesCfbAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesCfbAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCfbAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        switch (algorithmPadding) {
            case "ZeroPadding" -> {
                CustomSymmetricAesCfbZeroPaddingAlgorithmHandler customSymmetricAesCfbZeroPaddingAlgorithmHandler = new CustomSymmetricAesCfbZeroPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbZeroPaddingAlgorithmHandler.SymmetricAesCfbZeroPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ANSIX923" -> {
                CustomSymmetricAesCfbANSIX923AlgorithmHandler customSymmetricAesCfbANSIX923AlgorithmHandler = new CustomSymmetricAesCfbANSIX923AlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbANSIX923AlgorithmHandler.SymmetricAesCfbANSIX923AlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ISO10126Padding" -> {
                CustomSymmetricAesCfbISO10126PaddingAlgorithmHandler customSymmetricAesCfbISO10126PaddingAlgorithmHandler = new CustomSymmetricAesCfbISO10126PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbISO10126PaddingAlgorithmHandler.SymmetricAesCfbISO10126PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "NoPadding" -> {
                CustomSymmetricAesCfbNoPaddingAlgorithmHandler customSymmetricAesCfbNoPaddingAlgorithmHandler = new CustomSymmetricAesCfbNoPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbNoPaddingAlgorithmHandler.SymmetricAesCfbNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS5Padding" -> {
                CustomSymmetricAesCfbPKCS5PaddingAlgorithmHandler customSymmetricAesCfbPKCS5PaddingAlgorithmHandler = new CustomSymmetricAesCfbPKCS5PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbPKCS5PaddingAlgorithmHandler.SymmetricAesCfbPKCS5PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS7Padding" -> {
                CustomSymmetricAesCfbPKCS7PaddingAlgorithmHandler customSymmetricAesCfbPKCS7PaddingAlgorithmHandler = new CustomSymmetricAesCfbPKCS7PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbPKCS7PaddingAlgorithmHandler.SymmetricAesCfbPKCS7PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
        }

        return handlerBytes;
    }
}
