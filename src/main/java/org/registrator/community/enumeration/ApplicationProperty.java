package org.registrator.community.enumeration;

import org.registrator.community.entity.converter.PropertyConverter;
import org.registrator.community.entity.converter.RegistrationMethodPropertyConverter;
import org.registrator.community.entity.converter.TimeZonePropertyConverter;

/**
 * Application properties. This enumeration provide PropertyConverter for value of property.
 *
 * @see org.registrator.community.service.SettingsService
 */
public enum ApplicationProperty {
    REGISTRATION_METHOD(new RegistrationMethodPropertyConverter()),
    TIME_ZONE(new TimeZonePropertyConverter());

    private final PropertyConverter propertyConverter;

    ApplicationProperty(PropertyConverter propertyConverter) {
        this.propertyConverter = propertyConverter;
    }

    public PropertyConverter getPropertyConverter() {
        return propertyConverter;
    }
}
