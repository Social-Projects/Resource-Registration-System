package org.registrator.community.init;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.components.SpringApplicationContext;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.SettingsRepository;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.CalculatedParameter;
import org.registrator.community.enumeration.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeDeploy {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void init() {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        long userCount = (long) session.createQuery("select count(u) from User u").uniqueResult();
        if (userCount == 0) {

            Transaction territorialCommunity = session.beginTransaction();
            TerritorialCommunity globalTerritorialCommunity = new TerritorialCommunity();
            globalTerritorialCommunity.setName("Україна");
            session.persist(globalTerritorialCommunity);
            territorialCommunity.commit();

            Transaction roleTransaction = session.beginTransaction();
            System.out.println("In the init and if");
            Role roleAdmin = new Role(RoleType.ADMIN, "description");
            session.persist(roleAdmin);

            Role roleRegistrator = new Role(RoleType.REGISTRATOR, "description");
            session.persist(roleRegistrator);

            Role roleUser = new Role(RoleType.USER, "description");
            session.persist(roleUser);

            Role roleCommissioner = new Role(RoleType.COMMISSIONER, "description");
            session.persist(roleCommissioner);

            roleTransaction.commit();

            Transaction userTransaction = session.beginTransaction();
            User admin = new User("admin", "$2a$10$tkROwYPOXyBmKjarHW1rbuOOez2Z5gfkFCbUXUbOv1OY2wgekbZNC",
                    roleAdmin, "Адміністратор", "Адміністратор", "Адміністратор", "admin@admin.com", "ACTIVE", "+380500000000");


            admin.setTerritorialCommunity(globalTerritorialCommunity);
            admin.setDateOfAccession(new Date());
            session.persist(admin);

            userTransaction.commit();
            Transaction userInfoTransaction = session.beginTransaction();

            Address adminAddress = new Address(admin, "00000", "Львівська", "Галицький", "Львів", "Вітовського",
                    "48", "31");
            PassportInfo adminPassportInfo = new PassportInfo(admin, "AA", "00000", "Народом України");

            session.persist(adminAddress);
            session.persist(adminPassportInfo);
            userInfoTransaction.commit();


            Transaction resourceTypeTransaction = session.beginTransaction();
            ResourceType landType = new ResourceType("земельний");
            ResourceType radioType = new ResourceType("радіочастотний");
            session.persist(landType);
            session.persist(radioType);
            resourceTypeTransaction.commit();

            Transaction parameterForResourceTypeTransaction = session.beginTransaction();
            DiscreteParameter discreteParameter1 = new DiscreteParameter(landType, "периметр", "м");
            discreteParameter1.setCalculatedParameter(CalculatedParameter.PERIMETER);
            DiscreteParameter discreteParameter2 = new DiscreteParameter(landType, "площа", "га");
            discreteParameter1.setCalculatedParameter(CalculatedParameter.AREA);
            DiscreteParameter discreteParameter3 = new DiscreteParameter(radioType, "максимальна потужність", "мВт");
            DiscreteParameter discreteParameter4 = new DiscreteParameter(radioType, "напруженість", "мВт");
            LinearParameter linearParameter1 = new LinearParameter(radioType, "смуга радіочастот", "МГц");
            session.persist(discreteParameter1);
            session.persist(discreteParameter2);
            session.persist(discreteParameter3);
            session.persist(discreteParameter4);
            session.persist(linearParameter1);
            parameterForResourceTypeTransaction.commit();

            Settings settings = new Settings();
            session.persist(settings);

        }

        // check if calculated parameters filled
        // this is one time code - just don't have access to database server
        String queryString = "SELECT d from DiscreteParameter d where d.calculatedParameter is NULL";

        TypedQuery<DiscreteParameter> query = entityManagerFactory
                .createEntityManager()
                .createQuery(queryString, DiscreteParameter.class);

        List<DiscreteParameter> discreteParameterList = query.getResultList();
        DiscreteParameterRepository discreteParameterRepository = SpringApplicationContext.getBean(DiscreteParameterRepository.class);

        for (DiscreteParameter discreteParameter : discreteParameterList) {
            if (discreteParameter.getDiscreteParameterId().equals(1)) {
                discreteParameter.setCalculatedParameter(CalculatedParameter.PERIMETER);
            } else if (discreteParameter.getDiscreteParameterId().equals(2)) {
                discreteParameter.setCalculatedParameter(CalculatedParameter.AREA);
            } else {
                discreteParameter.setCalculatedParameter(CalculatedParameter.NONE);
            }

        }
        discreteParameterRepository.save(discreteParameterList);

        SettingsRepository settingsRepository = SpringApplicationContext.getBean(SettingsRepository.class);
        Settings settings = settingsRepository.getAllSettings();
        if (settings == null) {
            settings = new Settings();
            settingsRepository.save(settings);
        }

    }




}
