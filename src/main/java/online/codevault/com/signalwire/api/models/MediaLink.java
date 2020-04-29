package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MediaLink {

    @JsonProperty("media")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
