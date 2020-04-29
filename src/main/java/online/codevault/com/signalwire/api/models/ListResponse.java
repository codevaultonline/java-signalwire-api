package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("first_page_uri")
    private String firstPageUri;

    @JsonProperty("next_page_uri")
    private String nextPageUri;

    @JsonProperty("previous_page_uri")
    private String previousPageUri;

    @JsonProperty("page")
    private int page;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("faxes")
    private List<Fax> faxes;

}
