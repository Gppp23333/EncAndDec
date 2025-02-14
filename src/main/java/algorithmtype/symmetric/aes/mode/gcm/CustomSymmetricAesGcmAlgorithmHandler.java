package algorithmtype.symmetric.aes.mode.gcm;


import algorithmtype.symmetric.aes.mode.gcm.padding.nopadding.CustomSymmetricAesGcmNoPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesGcmAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesGcmAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesGcmAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        if (algorithmPadding.equals("NoPadding")) {
            CustomSymmetricAesGcmNoPaddingAlgorithmHandler customSymmetricAesGcmNoPaddingAlgorithmHandler = new CustomSymmetricAesGcmNoPaddingAlgorithmHandler(api);
            handlerBytes = customSymmetricAesGcmNoPaddingAlgorithmHandler.SymmetricAesGcmNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
        }

        return handlerBytes;
    }
}

