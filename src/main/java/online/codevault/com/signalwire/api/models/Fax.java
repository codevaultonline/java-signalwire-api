package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.codevault.com.signalwire.api.SignalWire;
import online.codevault.com.signalwire.api.SignalWireApiException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fax {

    @JsonIgnore
    private SignalWire signalwire;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @JsonProperty("date_updated")
    private LocalDateTime dateUpdated;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("from")
    private String from;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("media_sid")
    private String mediaSid;

    @JsonProperty("num_pages")
    private int pageCount;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("price_unit")
    private String priceCurrencyCode;

    @JsonProperty("quality")
    private String quality;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("to")
    private String to;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("links")
    private MediaLink links;

    @JsonProperty("url")
    private String url;

    public Fax(SignalWire signalwire) {
        this.signalwire = signalwire;
    }

    public void cancel() throws SignalWireApiException {
        signalwire.cancelFax(getSid());
    }

    public void delete() throws SignalWireApiException {
        signalwire.deleteFax(getSid());
    }

    public InputStream getContent() throws SignalWireApiException {
        return signalwire.getFaxContentFromFaxObject(this);
    }

}
