package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignalWireApiError {

    private String code;
    private String message;

    @JsonProperty("more_info")
    private String moreInfo;

    private String status;

}
