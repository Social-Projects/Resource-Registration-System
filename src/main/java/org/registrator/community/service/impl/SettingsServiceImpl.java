package org.registrator.community.service.impl;

import org.registrator.community.dao.SettingsRepository;
import org.registrator.community.entity.Settings;
import org.registrator.community.enumeration.RegistrationMethod;
import org.registrator.community.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

/**
 * Implementation of SettingsService
 *
 * @see org.registrator.community.service.SettingsService
 */

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public void setTimeZone(TimeZone timeZone) {
        Settings settings = settingsRepository.getAllSettings();
        settings.setTimeZone(timeZone);
        settingsRepository.save(settings);
    }

    @Override
    public TimeZone getTimeZone() {
        return settingsRepository.getAllSettings().getTimeZone();
    }

    @Override
    public void setRegistrationMethod(RegistrationMethod registrationMethod) {
        Settings settings = settingsRepository.getAllSettings();
        settings.setRegistrationMethod(registrationMethod);
        settingsRepository.save(settings);
    }

    @Override
    public RegistrationMethod getRegistrationMethod() {
        return settingsRepository.getAllSettings().getRegistrationMethod();
    }
}
