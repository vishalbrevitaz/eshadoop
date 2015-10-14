package com.packtpub.esh.spark;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by vishalshukla on 16/08/15.
 *
 */
public class Crime implements Serializable {
    private String id;
    private String caseNumber;
    private Long eventDate;
    private String block;
    private String iucr;
    private String primaryType;
    private String description;
    private String location;
    private Boolean arrest;
    private Boolean domestic;
    private Map<String, Double> geoLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mm");
        Date date = format.parse(eventDate);
        this.eventDate = date.getTime();
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getIucr() {
        return iucr;
    }

    public void setIucr(String iucr) {
        this.iucr = iucr;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getArrest() {
        return arrest;
    }

    public void setArrest(Boolean arrest) {
        this.arrest = arrest;
    }

    public Boolean getDomestic() {
        return domestic;
    }

    public void setDomestic(Boolean domestic) {
        this.domestic = domestic;
    }

    public Map<String, Double> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Map<String, Double> geoLocation) {
        this.geoLocation = geoLocation;
    }
}
