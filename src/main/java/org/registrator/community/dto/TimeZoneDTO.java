package org.registrator.community.dto;

import java.util.TimeZone;

/**
 * Represents time zone
 */
public class TimeZoneDTO {
    //ID to create instance of java.util.TimeZone
    private String id;

    //this used at frontend as name of time zone
    private String name;

    //this used at frontend as description of time zone in dropdown list
    private String description;

    public TimeZoneDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TimeZoneDTO(TimeZone timeZone) {
        id = timeZone.getID();
        name = id;
        description = id;
    }


    /**
     * Static factory method to create DTO from time zone ID
     *
     * @param timeZoneID ID for time zone as specified in java.util.TimeZone
     * @return new instance of this class with specified time zone ID
     */
    public static TimeZoneDTO from(String timeZoneID) {
        return new TimeZoneDTO(TimeZone.getTimeZone(timeZoneID));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimeZoneDTO)) {
            return false;
        }
        TimeZoneDTO that = (TimeZoneDTO) obj;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    
}
