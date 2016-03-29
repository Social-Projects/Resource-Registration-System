package org.registrator.community.init;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public void init(){
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
		
		Transaction territorialCommunity = session.beginTransaction();
        TerritorialCommunity globalTerritorialCommunity = new TerritorialCommunity();
        globalTerritorialCommunity.setName("Україна");
        session.persist(globalTerritorialCommunity);
        territorialCommunity.commit();

		
        Transaction roleTransaction = session.beginTransaction();
        Role roleUser = new Role(RoleType.USER,"description");
        session.persist(roleUser);

        Role roleAdmin = new Role(RoleType.ADMIN,"description");
        session.persist(roleAdmin);

        Role roleRegistrator = new Role(RoleType.REGISTRATOR,"description");
        session.persist(roleRegistrator);

        Role roleCommissioner = new Role(RoleType.COMMISSIONER,"description");
        session.persist(roleCommissioner);
        roleTransaction.commit();


        Transaction userTransaction = session.beginTransaction();
        User user = new User("user","$2a$10$Wcuw6mLD18wVT5diGYncJeVyL8J1bTSIly2IbLUX2bJ.UWZPC.qS.",
        		roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","ACTIVE");
        user.setDateOfAccession(new Date());
        user.setTerritorialCommunity(globalTerritorialCommunity);
        session.persist(user);
        
        User admin = new User("admin","$2a$10$tkROwYPOXyBmKjarHW1rbuOOez2Z5gfkFCbUXUbOv1OY2wgekbZNC",
        		roleAdmin,"Сергій","Головатий","Сергійович","sergey@gmail.com","ACTIVE");
        admin.setDateOfAccession(new Date());
        admin.setTerritorialCommunity(globalTerritorialCommunity);
        session.persist(admin);
        
        User registrator = new User("registrator","$2a$10$KJdq1wmP3MctLh.lEdAuseUCnSRdhJo8S7qwaZHFEUoGhfjOsOnrm",
        		roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","ACTIVE");
        registrator.setDateOfAccession(new Date());
        registrator.setTerritorialCommunity(globalTerritorialCommunity);
        session.persist(registrator);
        userTransaction.commit();


        Transaction addressTransaction = session.beginTransaction();
        Address userAddress = new Address(user,"00000","Львівська","Галицький","Львів","Вітовського","48","31");
        session.persist(userAddress);

        PassportInfo userPassportInfo = new PassportInfo(user,"AA","00000","Народом України");
        session.persist(userPassportInfo);

        Address registratorAddress = new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12");
        session.persist(registratorAddress);
        addressTransaction.commit();


        Transaction resourceTypeTransaction = session.beginTransaction();
        ResourceType land = new ResourceType("земельний");
        session.persist(land);
        resourceTypeTransaction.commit();

        Transaction tomeTransaction = session.beginTransaction();
        Tome tome = new Tome(registrator, "12345");
        session.persist(tome);
        tomeTransaction.commit();


        Transaction resourceTransaction = session.beginTransaction();
        Resource resource = new Resource(land, "111111", "ліс", registrator, new Date(), "active", tome, "підстава на внесення");
        session.persist(resource);
        resourceTransaction.commit();


        Transaction polygonTransaction = session.beginTransaction();
        Polygon polygon = new Polygon(24.0, 49.0, 26.0, 50.0, resource);
        polygon.setCoordinates("[{\"lat\":49,\"lng\":24},{\"lat\":50,\"lng\":26}]");
        session.persist(polygon);
        polygonTransaction.commit();


        Transaction discreteParameterTransaction = session.beginTransaction();
        DiscreteParameter perimetr = new DiscreteParameter(land, "периметр", "м");
        session.persist(perimetr);

        DiscreteParameter squire = new DiscreteParameter(land, "площа", "га");
        session.persist(squire);
        discreteParameterTransaction.commit();


        Transaction resourceDiscreteValue = session.beginTransaction();
        ResourceDiscreteValue perimetrValue = new ResourceDiscreteValue(resource, perimetr, 50.0);
        session.persist(perimetrValue);

        ResourceDiscreteValue squireValue = new ResourceDiscreteValue(resource, squire, 400.0);
        session.persist(squireValue);
        resourceDiscreteValue.commit();


        Transaction resourceSetDiscreteParameters = session.beginTransaction();
        land.setDiscreteParameters(Arrays.asList(perimetr, squire));
        session.persist(land);
        resourceSetDiscreteParameters.commit();


        Transaction inquiryTransaction = session.beginTransaction();
        Inquiry inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
        session.persist(inquiry);

        Inquiry inquiryInput = new Inquiry("INPUT", new Date(), user, registrator, resource);
        session.persist(inquiryInput);
        inquiryTransaction.commit();


        Transaction tokenTransaction = session.beginTransaction();
        VerificationToken verificationToken = new VerificationToken("token", "ivan@gmail.com", new Date(),TokenType.RECOVER_PASSWORD);
        session.persist(verificationToken);
        tokenTransaction.commit();
	}

}
