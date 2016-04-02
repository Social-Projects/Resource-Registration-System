package org.registrator.community.entity.converter;

import java.util.TimeZone;

/**
 * Converter for time zone application property
 */
public class TimeZonePropertyConverter implements PropertyConverter<TimeZone> {
    @Override
    public TimeZone getDefaultValue() {
        return TimeZone.getTimeZone("EET");
    }

    @Override
    public TimeZone convertToEntityAttribute(String databaseValue) {
        return TimeZone.getTimeZone(databaseValue);
    }

    @Override
    public String convertToDatabaseColumn(TimeZone value) {
        return value.getID();
    }

    @Override
    public String convertToClientRepresentation(TimeZone value) {
        return value.getDisplayName(false,TimeZone.SHORT);
    }
}
