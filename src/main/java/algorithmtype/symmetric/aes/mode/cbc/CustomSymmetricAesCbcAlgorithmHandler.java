package algorithmtype.symmetric.aes.mode.cbc;

import algorithmtype.symmetric.aes.mode.cbc.padding.ansix923.CustomSymmetricAesCbcANSIX923AlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cbc.padding.iso10126padding.CustomSymmetricAesCbcISO10126PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cbc.padding.nopadding.CustomSymmetricAesCbcNoPaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cbc.padding.pkcs5padding.CustomSymmetricAesCbcPKCS5PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cbc.padding.pkcs7padding.CustomSymmetricAesCbcPKCS7PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cbc.padding.zeropadding.CustomSymmetricAesCbcZeroPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesCbcAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesCbcAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCbcAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        switch (algorithmPadding) {
            case "ZeroPadding" -> {
                CustomSymmetricAesCbcZeroPaddingAlgorithmHandler customSymmetricAesCbcZeroPaddingAlgorithmHandler = new CustomSymmetricAesCbcZeroPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcZeroPaddingAlgorithmHandler.SymmetricAesCbcZeroPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ANSIX923" -> {
                CustomSymmetricAesCbcANSIX923AlgorithmHandler customSymmetricAesCbcANSIX923AlgorithmHandler = new CustomSymmetricAesCbcANSIX923AlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcANSIX923AlgorithmHandler.SymmetricAesCbcANSIX923AlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ISO10126Padding" -> {
                CustomSymmetricAesCbcISO10126PaddingAlgorithmHandler customSymmetricAesCbcISO10126PaddingAlgorithmHandler = new CustomSymmetricAesCbcISO10126PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcISO10126PaddingAlgorithmHandler.SymmetricAesCbcISO10126PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "NoPadding" -> {
                CustomSymmetricAesCbcNoPaddingAlgorithmHandler customSymmetricAesCbcNoPaddingAlgorithmHandler = new CustomSymmetricAesCbcNoPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcNoPaddingAlgorithmHandler.SymmetricAesCbcNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS5Padding" -> {
                CustomSymmetricAesCbcPKCS5PaddingAlgorithmHandler customSymmetricAesCbcPKCS5PaddingAlgorithmHandler = new CustomSymmetricAesCbcPKCS5PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcPKCS5PaddingAlgorithmHandler.SymmetricAesCbcPKCS5PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS7Padding" -> {
                CustomSymmetricAesCbcPKCS7PaddingAlgorithmHandler customSymmetricAesCbcPKCS7PaddingAlgorithmHandler = new CustomSymmetricAesCbcPKCS7PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcPKCS7PaddingAlgorithmHandler.SymmetricAesCbcPKCS7PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
        }

        return handlerBytes;
    }
}
