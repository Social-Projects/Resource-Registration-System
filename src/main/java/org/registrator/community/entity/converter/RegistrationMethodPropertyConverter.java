package org.registrator.community.entity.converter;

import org.registrator.community.enumeration.RegistrationMethod;

/**
 * Property converter for RegistrationMethod application property
 * @see PropertyConverter
 */
public class RegistrationMethodPropertyConverter implements PropertyConverter<RegistrationMethod> {
    @Override
    public RegistrationMethod getDefaultValue() {
        return RegistrationMethod.MANUAL;
    }

    @Override
    public RegistrationMethod convertToEntityAttribute(String databaseValue) {
        return RegistrationMethod.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseColumn(RegistrationMethod value) {
        return value.toString();
    }

    @Override
    public String convertToClientRepresentation(RegistrationMethod value) {
        return value.toString();
    }
}
