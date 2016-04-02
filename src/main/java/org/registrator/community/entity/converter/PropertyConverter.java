package org.registrator.community.entity.converter;

/**
 * Converter for settings property value to and from database value
 *
 * All values in Settings table are stored as strings, purpose of this interface to provide conversion.
 *
 * @see org.registrator.community.service.SettingsService
 */
public interface PropertyConverter<T>{

    T getDefaultValue();

    T convertToEntityAttribute(String databaseValue);

    String convertToDatabaseColumn(T value);

    String convertToClientRepresentation(T value);
}
