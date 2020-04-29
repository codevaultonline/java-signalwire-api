package online.codevault.com.signalwire.api;

import com.google.gson.Gson;
import online.codevault.com.signalwire.api.models.SignalWireApiError;

public class SignalWireApiException extends Exception {

    private SignalWireApiError errorObject;

    public SignalWireApiException(String s) {

        super(s);

        Gson gson = new Gson();
        try {
            this.errorObject = gson.fromJson(s, SignalWireApiError.class);
        } catch (Exception ex) {
        }

    }

    public SignalWireApiError getErrorObject() {
        return errorObject;
    }
    
}
