package org.registrator.community.service;

import org.registrator.community.enumeration.ApplicationProperty;
import org.registrator.community.enumeration.RegistrationMethod;

import java.util.Map;
import java.util.TimeZone;

/**
 * Service to get and save application settings.
 *
 */
public interface SettingsService {

    RegistrationMethod getRegistrationMethod();

    void setRegistrationMethod(RegistrationMethod registrationMethod);

    TimeZone getTimeZone();

    void setTimeZone(TimeZone timeZone);
}
