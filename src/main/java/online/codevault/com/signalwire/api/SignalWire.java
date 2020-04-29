package online.codevault.com.signalwire.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import online.codevault.com.signalwire.api.models.Fax;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SignalWire {

    private final String baseUrl;
    private final String spaceId;
    private final String accountSid;
    private final String projectId;
    private final String token;
    private final String apiBaseUrl;
    private final Gson gson = new Gson();

    public SignalWire(String spaceId, String accountSid, String projectId, String token) {

        this.spaceId = spaceId;
        this.accountSid = accountSid;
        this.projectId = projectId;
        this.token = token;
        this.baseUrl = String.format("https://%s.signalwire.com", spaceId, accountSid);
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
            Fax fax = gson.fromJson(f.toString(), Fax.class);
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

            return gson.fromJson(response.getBody(), Fax.class);

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

            return gson.fromJson(response.getBody(), Fax.class);

        } catch (UnirestException e) {
            throw new SignalWireApiException(e.getMessage());
        }

    }

    public InputStream getFaxContentFromFaxObject(Fax fax) throws SignalWireApiException, UnirestException {

        HttpResponse<InputStream> response = Unirest.get(fax.getMediaUrl()).asBinary();

        if (200 != response.getStatus()) {
            throw new SignalWireApiException(response.getStatusText());
        }

        return response.getRawBody();

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

            return gson.fromJson(response.getBody().toString(), Fax.class);

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