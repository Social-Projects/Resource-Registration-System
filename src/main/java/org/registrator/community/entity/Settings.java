package org.registrator.community.entity;

import org.registrator.community.enumeration.RegistrationMethod;

import javax.persistence.*;
import java.util.TimeZone;

/**
 * Entity to save application settings
 *
 * @see org.registrator.community.service.SettingsService
 */
@Entity
@Table(name = "SETTINGS")
public class Settings {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    @Access(AccessType.PROPERTY)
    private RegistrationMethod registrationMethod;

    @Column
    @Access(AccessType.PROPERTY)
    @Convert(converter = Settings.TimeZoneConverter.class)
    private TimeZone timeZone;

    @Embedded
    private SmtpParameters smtpParameters;

    public Settings() {
        id = 1;
        registrationMethod = RegistrationMethod.MANUAL;
        timeZone = TimeZone.getTimeZone("EET");
        smtpParameters = new SmtpParameters();
    }

    public RegistrationMethod getRegistrationMethod() {
        return registrationMethod;
    }

    public void setRegistrationMethod(RegistrationMethod registrationMethod) {
        this.registrationMethod = registrationMethod;
    }

    public TimeZone getTimeZone() {

        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public SmtpParameters getSmtpParameters() {
        return smtpParameters;
    }

    public void setSmtpParameters(SmtpParameters smtpParameters) {
        this.smtpParameters = smtpParameters;
    }

    @Converter
    public static class TimeZoneConverter implements AttributeConverter<TimeZone, String> {
        @Override
        public String convertToDatabaseColumn(TimeZone attribute) {
            return attribute.getID();
        }

        @Override
        public TimeZone convertToEntityAttribute(String dbData) {
            return TimeZone.getTimeZone(dbData);
        }
    }

}
