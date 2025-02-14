package algorithmtype.symmetric.aes;

import algorithmtype.symmetric.aes.mode.cbc.CustomSymmetricAesCbcAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cfb.CustomSymmetricAesCfbAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ctr.CustomSymmetricAesCtrAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.cts.CustomSymmetricAesCtsAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ecb.CustomSymmetricAesEcbAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.gcm.CustomSymmetricAesGcmAlgorithmHandler;
import algorithmtype.symmetric.aes.mode.ofb.CustomSymmetricAesOfbAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesAlgorithmHandler(String algorithmMode, String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        switch (algorithmMode) {
            case "ECB" -> {
                CustomSymmetricAesEcbAlgorithmHandler customSymmetricAesEcbAlgorithmHandler = new CustomSymmetricAesEcbAlgorithmHandler(api);
                handlerBytes = customSymmetricAesEcbAlgorithmHandler.SymmetricAesEcbAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "CBC" -> {
                CustomSymmetricAesCbcAlgorithmHandler customSymmetricAesCbcAlgorithmHandler = new CustomSymmetricAesCbcAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCbcAlgorithmHandler.SymmetricAesCbcAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "CFB" -> {
                CustomSymmetricAesCfbAlgorithmHandler customSymmetricAesCfbAlgorithmHandler = new CustomSymmetricAesCfbAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCfbAlgorithmHandler.SymmetricAesCfbAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "OFB" -> {
                CustomSymmetricAesOfbAlgorithmHandler customSymmetricAesOfbAlgorithmHandler = new CustomSymmetricAesOfbAlgorithmHandler(api);
                handlerBytes = customSymmetricAesOfbAlgorithmHandler.SymmetricAesOfbAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "CTR" -> {
                CustomSymmetricAesCtrAlgorithmHandler customSymmetricAesCtrAlgorithmHandler = new CustomSymmetricAesCtrAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtrAlgorithmHandler.SymmetricAesCtrAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "GCM" -> {
                CustomSymmetricAesGcmAlgorithmHandler customSymmetricAesGcmAlgorithmHandler = new CustomSymmetricAesGcmAlgorithmHandler(api);
                handlerBytes = customSymmetricAesGcmAlgorithmHandler.SymmetricAesGcmAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
            case "CTS" -> {
                CustomSymmetricAesCtsAlgorithmHandler customSymmetricAesCtsAlgorithmHandler = new CustomSymmetricAesCtsAlgorithmHandler(api);
                handlerBytes = customSymmetricAesCtsAlgorithmHandler.SymmetricAesCtsAlgorithmHandler(algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            }
        }

        return handlerBytes;
    }
}

