package algorithmtype.symmetric;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import algorithmtype.symmetric.aes.CustomSymmetricAesAlgorithmHandler;

import java.util.ArrayList;

public class CustomSymmetricAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAlgorithmHandler(String algorithm, String algorithmMode, String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        if (algorithm.equals("AES")) {
            CustomSymmetricAesAlgorithmHandler customSymmetricAesAlgorithmHandler = new CustomSymmetricAesAlgorithmHandler(api);
            handlerBytes = customSymmetricAesAlgorithmHandler.SymmetricAesAlgorithmHandler(algorithmMode, algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
        } else if (algorithm.equals("DES")) {
            return null;
        }

        return handlerBytes;
    }
}
