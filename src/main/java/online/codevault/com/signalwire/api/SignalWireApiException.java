package online.codevault.com.signalwire.api;

import online.codevault.com.signalwire.api.models.SignalWireApiError;

public class SignalWireApiException extends Exception {

    private SignalWireApiError errorObject;

    public SignalWireApiException(String s) {

        super(s);

        try {
            this.errorObject = ObjectMapperHelper.getInstance().readValue(s, SignalWireApiError.class);
        } catch (Exception ex) {
        }

    }

    public SignalWireApiError getErrorObject() {
        return errorObject;
    }

}
