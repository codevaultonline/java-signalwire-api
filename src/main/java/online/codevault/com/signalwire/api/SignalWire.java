package online.codevault.com.signalwire.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.core.*;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import lombok.Getter;
import online.codevault.com.signalwire.api.models.Fax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SignalWire {

    @Getter
    private final String spaceId;

    @Getter
    private final String accountSid;

    @Getter
    private final String projectId;

    private final String token;

    private final String apiBaseUrl;

    public SignalWire(String spaceId, String accountSid, String projectId, String token) {

        this.spaceId = spaceId;
        this.accountSid = accountSid;
        this.projectId = projectId;
        this.token = token;
        String baseUrl = String.format("https://%s.signalwire.com", spaceId);
        this.apiBaseUrl = String.format("%s/api/laml/2010-04-01/Accounts/%s", baseUrl, accountSid);

    }

    private String getEndpoint(String action) {
        return String.format("%s/%s.json", this.apiBaseUrl, action);
    }

    private String getEndpoint(String resource, String action) {
        return String.format("%s/%s/%s.json", this.apiBaseUrl, resource, action);
    }

    public List<Fax> getFaxes() throws SignalWireApiException {

        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest
                    .get(getEndpoint("Faxes"))
                    .basicAuth(this.projectId, this.token)
                    .asJson();
        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

        if (200 != response.getStatus()) {
            throw new SignalWireApiException(response.getBody().toString());
        }

        JSONObject json = response.getBody().getObject();
        JSONArray jsonFaxes = json.getJSONArray("faxes");

        List<Fax> faxes = new ArrayList<>();

        jsonFaxes.forEach(f -> {
            Fax fax = null;
            try {
                fax = ObjectMapperHelper.getInstance().readValue(f.toString(), Fax.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            fax.setApiReference(this);
            faxes.add(fax);
        });

        return faxes;

    }

    public Fax getFax(String sid) throws SignalWireApiException {

        HttpResponse<String> response = null;
        try {

            response = Unirest
                    .get(getEndpoint("Faxes", sid))
                    .basicAuth(this.projectId, this.token)
                    .asString();

            if (200 != response.getStatus()) {
                throw new SignalWireApiException(response.getBody());
            }

            try {
                return ObjectMapperHelper.getInstance().readValue(response.getBody(), Fax.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

    }

    public Fax cancelFax(String sid) throws SignalWireApiException {

        HttpResponse<String> response = null;
        try {

            response = Unirest
                    .post(getEndpoint("Faxes", sid))
                    .field("Status", "Cancelled")
                    .basicAuth(this.projectId, this.token)
                    .asString();

            if (200 != response.getStatus()) {
                throw new SignalWireApiException(response.getBody());
            }

            try {
                return ObjectMapperHelper.getInstance().readValue(response.getBody(), Fax.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

    }

    public InputStream getFaxContentFromFaxObject(Fax fax) throws SignalWireApiException, UnirestException {

        HttpResponse<InputStream> response = Unirest.get(fax.getMediaUrl()).asObject(RawResponse::getContent);

        if (200 != response.getStatus()) {
            throw new SignalWireApiException(response.getStatusText());
        }

        return response.getBody();

    }

    public Fax sendFax(String to, String from, String mediaUrl) throws SignalWireApiException {

        HttpResponse<JsonNode> response = null;
        try {

            response = Unirest
                    .post(getEndpoint("Faxes"))
                    .basicAuth(this.projectId, this.token)
                    .field("To", to)
                    .field("From", from)
                    .field("MediaUrl", mediaUrl)
                    .asJson();

            if (201 != response.getStatus()) {
                throw new SignalWireApiException(response.getBody().toString());
            }

            try {
                return ObjectMapperHelper.getInstance().readValue(response.getBody().toString(), Fax.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

    }

    public void deleteFax(String sid) throws SignalWireApiException {

        HttpResponse<String> response = null;
        try {

            response = Unirest
                    .delete(getEndpoint("Faxes", sid))
                    .basicAuth(this.projectId, this.token)
                    .asString();

        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

        if (204 != response.getStatus()) {
            throw new SignalWireApiException(response.getBody());
        }

    }

}