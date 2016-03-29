package org.registrator.community.entity;

import org.registrator.community.enumeration.RegistrationMethod;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.TimeZone;

/**
 * Entity to save application settings
 */
public class Settings {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private RegistrationMethod registrationMethod;

    @Column
    private TimeZone timeZone;

    public Settings() {
        id = 1;
    }

    public Integer getId() {
        return id;
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
}
