package org.registrator.community.init;

import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeDeploy {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void init(){
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
		long userCount = (long)session.createQuery("select count(u) from User u").uniqueResult();
		if(userCount==0){
		    
		    Transaction territorialCommunity = session.beginTransaction();
		    TerritorialCommunity globalTerritorialCommunity = new TerritorialCommunity();
		    globalTerritorialCommunity.setName("Україна");
		    session.persist(globalTerritorialCommunity);
		    territorialCommunity.commit();

		    Transaction roleTransaction = session.beginTransaction();
	        System.out.println("In the init and if");
	        Role roleAdmin = new Role(RoleType.ADMIN,"description");
	        session.persist(roleAdmin);
	        
	        Role roleRegistrator = new Role(RoleType.REGISTRATOR,"description");
	        session.persist(roleRegistrator);
	        
	        Role roleUser = new Role(RoleType.USER,"description");
	        session.persist(roleUser);
	        
	        Role roleCommissioner = new Role(RoleType.COMMISSIONER,"description");
	        session.persist(roleCommissioner);
	        
	        roleTransaction.commit();
 
            Transaction userTransaction = session.beginTransaction();
	        User admin = new User("admin","$2a$10$tkROwYPOXyBmKjarHW1rbuOOez2Z5gfkFCbUXUbOv1OY2wgekbZNC",
	        		roleAdmin,"Адміністратор","Адміністратор","Адміністратор","admin@admin.com","ACTIVE","+380500000000");
	        
	          
	        admin.setTerritorialCommunity(globalTerritorialCommunity);
	        admin.setDateOfAccession(new Date());
	        session.persist(admin);
	        
	        userTransaction.commit();
	        Transaction userInfoTransaction = session.beginTransaction();
	        
	        Address adminAddress = new Address(admin,"00000","Львівська","Галицький","Львів","Вітовського",
	        		"48","31");
	        PassportInfo adminPassportInfo = new PassportInfo(admin,"AA","00000","Народом України");
	        
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
            DiscreteParameter discreteParameter1 = new DiscreteParameter(landType, "периметр","м");
            DiscreteParameter discreteParameter2 = new DiscreteParameter(landType, "площа","га");
            DiscreteParameter discreteParameter3 = new DiscreteParameter(radioType, "максимальна потужність","мВт");
            DiscreteParameter discreteParameter4 = new DiscreteParameter(radioType, "напруженість","мВт");
            LinearParameter linearParameter1 = new LinearParameter(radioType, "смуга радіочастот","МГц");
            session.persist(discreteParameter1);
            session.persist(discreteParameter2);
            session.persist(discreteParameter3);
            session.persist(discreteParameter4);
            session.persist(linearParameter1);
            parameterForResourceTypeTransaction.commit();
		}
	}
}
