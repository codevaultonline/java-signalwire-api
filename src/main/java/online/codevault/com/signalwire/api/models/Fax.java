package online.codevault.com.signalwire.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import online.codevault.com.signalwire.api.SignalWire;
import online.codevault.com.signalwire.api.SignalWireApiException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

public class Fax {

    @JsonIgnore
    private SignalWire apiReference;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @SerializedName("date_created")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @SerializedName("date_updated")
    private Date dateUpdated;

    @SerializedName("api_version")
    private String apiVersion;

    private String direction;
    private String from;

    @SerializedName("media_url")
    private String mediaUrl;

    @SerializedName("media_sid")
    private String mediaSid;

    @SerializedName("num_pages")
    private int pageCount;

    private BigDecimal price;

    @SerializedName("price_unit")
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaSid() {
        return mediaSid;
    }

    public void setMediaSid(String mediaSid) {
        this.mediaSid = mediaSid;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public MediaLink getLinks() {
        return links;
    }

    public void setLinks(MediaLink links) {
        this.links = links;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void cancel() throws SignalWireApiException {
        apiReference.cancelFax(getSid());
    }

    public void delete() throws SignalWireApiException {
        apiReference.deleteFax(getSid());
    }

    public void setApiReference(SignalWire apiReference) {
        this.apiReference = apiReference;
    }

    public InputStream getContent() throws SignalWireApiException, UnirestException {
        return apiReference.getFaxContentFromFaxObject(this);
    }

}
