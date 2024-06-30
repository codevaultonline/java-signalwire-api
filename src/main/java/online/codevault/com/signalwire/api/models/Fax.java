package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fax {

    @JsonIgnore
    private SignalWire apiReference;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @JsonProperty("date_updated")
    private LocalDateTime dateUpdated;

    @JsonProperty("api_version")
    private String apiVersion;

    private String direction;
    private String from;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("media_sid")
    private String mediaSid;

    @JsonProperty("num_pages")
    private int pageCount;

    private BigDecimal price;

    @JsonProperty("price_unit")
    private String priceCurrencyCode;
    private String quality;
    private String sid;
    private String status;
    private String to;
    private int duration;
    private MediaLink links;
    private String url;

    public Fax(SignalWire apiReference) {
        this.apiReference = apiReference;
    }

    public void cancel() throws SignalWireApiException {
        apiReference.cancelFax(getSid());
    }

    public void delete() throws SignalWireApiException {
        apiReference.deleteFax(getSid());
    }

    public InputStream getContent() throws SignalWireApiException {
        return apiReference.getFaxContentFromFaxObject(this);
    }

}
