package algorithmtype.symmetric.aes.mode.cts;


import algorithmtype.symmetric.aes.mode.cts.padding.nopadding.CustomSymmetricAesCtsNoPaddingAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.ArrayList;

public class CustomSymmetricAesCtsAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSymmetricAesCtsAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public byte[] SymmetricAesCtsAlgorithmHandler(String algorithmPadding, Boolean isDecrypt, byte[] selectedBytes, ArrayList<String> paramHexValueList) {
        byte[] handlerBytes = null;
        if (algorithmPadding.equals("NoPadding")) {
            CustomSymmetricAesCtsNoPaddingAlgorithmHandler customSymmetricAesCtsNoPaddingAlgorithmHandler = new CustomSymmetricAesCtsNoPaddingAlgorithmHandler(api);
            handlerBytes = customSymmetricAesCtsNoPaddingAlgorithmHandler.SymmetricAesCtsNoPaddingAlgorithmHandler(isDecrypt, selectedBytes, paramHexValueList);
        }

        return handlerBytes;
    }
}

