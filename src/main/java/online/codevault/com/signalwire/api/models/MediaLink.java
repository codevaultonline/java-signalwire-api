package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaLink {

    @JsonProperty("media")
    private String link;

}
