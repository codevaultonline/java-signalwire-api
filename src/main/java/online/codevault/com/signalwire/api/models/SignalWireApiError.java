package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignalWireApiError {

    private String code;
    private String message;

    @JsonProperty("more_info")
    private String moreInfo;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
