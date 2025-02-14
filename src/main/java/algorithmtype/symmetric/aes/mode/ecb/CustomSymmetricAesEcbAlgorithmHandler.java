package algorithmtype.symmetric.aes.mode.ecb;

import algorithmtype.symmetric.aes.mode.ecb.padding.ansix923.CustomSymmetricAesEcbANSIX923AlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.padding.iso10126padding.CustomSymmetricAesEcbISO10126PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.padding.nopadding.CustomSymmetricAesEcbNoPaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.padding.pkcs5padding.CustomSymmetricAesEcbPKCS5PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.padding.pkcs7padding.CustomSymmetricAesEcbPKCS7PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.padding.zeropadding.CustomSymmetricAesEcbZeroPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesEcbAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesEcbAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesEcbAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        switch (algorithmPadding) {
            case "ZeroPadding" -> {
                CustomSymmetricAesEcbZeroPaddingAlgorithmHandler customSymmetricAesEcbZeroPaddingAlgorithmHandler = new CustomSymmetricAesEcbZeroPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbZeroPaddingAlgorithmHandler.SymmetricAesEcbZeroPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ANSIX923" -> {
                CustomSymmetricAesEcbANSIX923AlgorithmHandler customSymmetricAesEcbANSIX923AlgorithmHandler = new CustomSymmetricAesEcbANSIX923AlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbANSIX923AlgorithmHandler.SymmetricAesEcbANSIX923AlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ISO10126Padding" -> {
                CustomSymmetricAesEcbISO10126PaddingAlgorithmHandler customSymmetricAesEcbISO10126PaddingAlgorithmHandler = new CustomSymmetricAesEcbISO10126PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbISO10126PaddingAlgorithmHandler.SymmetricAesEcbISO10126PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "NoPadding" -> {
                CustomSymmetricAesEcbNoPaddingAlgorithmHandler customSymmetricAesEcbNoPaddingAlgorithmHandler = new CustomSymmetricAesEcbNoPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbNoPaddingAlgorithmHandler.SymmetricAesEcbNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS5Padding" -> {
                CustomSymmetricAesEcbPKCS5PaddingAlgorithmHandler customSymmetricAesEcbPKCS5PaddingAlgorithmHandler = new CustomSymmetricAesEcbPKCS5PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbPKCS5PaddingAlgorithmHandler.SymmetricAesEcbPKCS5PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS7Padding" -> {
                CustomSymmetricAesEcbPKCS7PaddingAlgorithmHandler customSymmetricAesEcbPKCS7PaddingAlgorithmHandler = new CustomSymmetricAesEcbPKCS7PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbPKCS7PaddingAlgorithmHandler.SymmetricAesEcbPKCS7PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
        }

        return handlerBytes;
    }
}
