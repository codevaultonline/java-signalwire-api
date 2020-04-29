package online.codevault.com.signalwire.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.core.*;
import lombok.Getter;
import online.codevault.com.signalwire.api.models.Fax;
import online.codevault.com.signalwire.api.models.ListFaxesResponse;

import java.io.InputStream;
import java.time.LocalDate;

public class SignalWire {

    @Getter
    private final String spaceId;

    @Getter
    private final String accountSid;

    @Getter
    private final String projectId;

    private final String token;

    private final String baseUrl;
    private final String apiBaseUrl;

    public SignalWire(String spaceId, String accountSid, String projectId, String token) {

        this.spaceId = spaceId;
        this.accountSid = accountSid;
        this.projectId = projectId;
        this.token = token;
        this.baseUrl = String.format("https://%s.signalwire.com", spaceId);
        this.apiBaseUrl = String.format("%s/api/laml/2010-04-01/Accounts/%s", baseUrl, accountSid);

    }

    private String getEndpoint(String action) {
        return String.format("%s/%s", this.apiBaseUrl, action);
    }

    private String getEndpoint(String resource, String action) {
        return String.format("%s/%s/%s.json", this.apiBaseUrl, resource, action);
    }

    public ListFaxesResponse getFaxes() throws SignalWireApiException, JsonProcessingException {
        return getFaxes(null, null, null, null, null);
    }


    public ListFaxesResponse getFaxes(LocalDate createdAfter, LocalDate createdOnOrBefore, String from, String to, ListFaxesResponse listFaxesResponse) throws SignalWireApiException, JsonProcessingException {

        HttpResponse<JsonNode> response = null;

        GetRequest getRequest;

        if (null != listFaxesResponse) {

            if (null == listFaxesResponse.getNextPageUri()) {
                return null;
            }

            getRequest = Unirest
                        .get(baseUrl + "/" + listFaxesResponse.getNextPageUri())
                        .basicAuth(this.projectId, this.token);

        } else {

            getRequest = Unirest
                    .get(getEndpoint("Faxes"))
                    .basicAuth(this.projectId, this.token);

            if (null != createdAfter) {
                getRequest.queryString("DateCreatedAfter", createdAfter.toString());
            }

            if (null != createdOnOrBefore) {
                getRequest.queryString("DateCreatedOnOrBefore", createdOnOrBefore.toString());
            }

            if (null != from) {
                getRequest.queryString("From", from);
            }

            if (null != to) {
                getRequest.queryString("To", to);
            }

        }

        try {
            response = getRequest
                    .asJson();
        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

        if (200 != response.getStatus()) {
            throw new SignalWireApiException(response.getBody().toString());
        }

        return ObjectMapperHelper.getInstance().readValue(response.getBody().toString(), ListFaxesResponse.class);

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

    public InputStream getFaxMedia(String mediaUrl) throws SignalWireApiException {

        HttpResponse<InputStream> response = null;
        try {

            response = Unirest
                    .get(mediaUrl)
                    .basicAuth(this.projectId, this.token)
                    .asObject(RawResponse::getContent);

            if (200 != response.getStatus()) {
                throw new SignalWireApiException(response.getStatusText());
            }

            return response.getBody();

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