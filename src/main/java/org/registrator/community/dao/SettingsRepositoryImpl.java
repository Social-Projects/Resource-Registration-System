package org.registrator.community.dao;

import org.registrator.community.entity.Settings;
import org.registrator.community.enumeration.RegistrationMethod;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.TimeZone;

/**
 * Impmlementation of Settings repository based on table in database, all settings saved in one row
 */
@Component
public class SettingsRepositoryImpl implements SettingsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Settings getAllSettings() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Settings> query = criteriaBuilder.createQuery(Settings.class);
        query.select(query.from(Settings.class));
        Settings settings = entityManager.createQuery(query)
                .setMaxResults(1)
                .getSingleResult();

        if (settings == null) {
            settings = createDefaultSettings();
            saveSettings(settings);
        }

        return settings;
    }

    private Settings createDefaultSettings() {
        Settings defaultSettings = new Settings();
        defaultSettings.setRegistrationMethod(RegistrationMethod.MANUAL);
        defaultSettings.setTimeZone(TimeZone.getTimeZone("EET"));

        return defaultSettings;
    }

    @Override
    public void saveSettings(Settings newSettings) {
        entityManager.getTransaction().begin();
        entityManager.persist(newSettings);
    }




}
