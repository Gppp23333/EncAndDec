package algorithmtype.symmetric.aes.mode.ctr;


import algorithmtype.symmetric.aes.mode.ctr.padding.ansix923.CustomSymmetricAesCtrANSIX923AlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.padding.iso10126padding.CustomSymmetricAesCtrISO10126PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.padding.nopadding.CustomSymmetricAesCtrNoPaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.padding.pkcs5padding.CustomSymmetricAesCtrPKCS5PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.padding.pkcs7padding.CustomSymmetricAesCtrPKCS7PaddingAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.padding.zeropadding.CustomSymmetricAesCtrZeroPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesCtrAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesCtrAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCtrAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;

        switch (algorithmPadding) {
            case "ZeroPadding" -> {
                CustomSymmetricAesCtrZeroPaddingAlgorithmHandler customSymmetricAesCtrZeroPaddingAlgorithmHandler = new CustomSymmetricAesCtrZeroPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrZeroPaddingAlgorithmHandler.SymmetricAesCtrZeroPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ANSIX923" -> {
                CustomSymmetricAesCtrANSIX923AlgorithmHandler customSymmetricAesCtrANSIX923AlgorithmHandler = new CustomSymmetricAesCtrANSIX923AlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrANSIX923AlgorithmHandler.SymmetricAesCtrANSIX923AlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "ISO10126Padding" -> {
                CustomSymmetricAesCtrISO10126PaddingAlgorithmHandler customSymmetricAesCtrISO10126PaddingAlgorithmHandler = new CustomSymmetricAesCtrISO10126PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrISO10126PaddingAlgorithmHandler.SymmetricAesCtrISO10126PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "NoPadding" -> {
                CustomSymmetricAesCtrNoPaddingAlgorithmHandler customSymmetricAesCtrNoPaddingAlgorithmHandler = new CustomSymmetricAesCtrNoPaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrNoPaddingAlgorithmHandler.SymmetricAesCtrNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS5Padding" -> {
                CustomSymmetricAesCtrPKCS5PaddingAlgorithmHandler customSymmetricAesCtrPKCS5PaddingAlgorithmHandler = new CustomSymmetricAesCtrPKCS5PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrPKCS5PaddingAlgorithmHandler.SymmetricAesCtrPKCS5PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
            case "PKCS7Padding" -> {
                CustomSymmetricAesCtrPKCS7PaddingAlgorithmHandler customSymmetricAesCtrPKCS7PaddingAlgorithmHandler = new CustomSymmetricAesCtrPKCS7PaddingAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrPKCS7PaddingAlgorithmHandler.SymmetricAesCtrPKCS7PaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
            }
        }
        return handlerBytes;
    }
}

