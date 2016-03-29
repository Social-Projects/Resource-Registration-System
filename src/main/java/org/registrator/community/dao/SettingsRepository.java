package org.registrator.community.dao;

import org.registrator.community.entity.Settings;

/**
 * Interface for getting and saving application settings
 */
public interface SettingsRepository {
    Settings getAllSettings();

    void saveSettings(Settings newSettings);


}
