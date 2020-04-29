package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListFaxesResponse extends ListResponse {

    @JsonProperty("faxes")
    private List<Fax> faxes;

}
