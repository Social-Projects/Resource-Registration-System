package org.registrator.community.service;

import com.itextpdf.text.DocumentException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.PrintServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

public class PrintServiceTest {

    @InjectMocks
    private PrintService printService;
    @Mock
    private InquiryRepository inquiryRepository;
    @Mock
    private PolygonRepository polygonRepository;
    @Mock
    private ResourceDiscreteValueService resourceDiscrete;

    private static final Integer OUTPUT_ID = 1; // Inquiry ID with OUTPUT Type
    private static final Integer INPUT_ID = 2; // Inquiry ID with INPUT Type
    private static TerritorialCommunity globalTerritorialCommunity;
    private static User user, registrator;
    private static ResourceType land;
    private static Tome tome;
    private static Resource resource;
    private static Inquiry inquiryOutput;
    private static Inquiry inquiryInput;
    private static Polygon polygon;
    private static DiscreteParameter discreteParameterPerimetr;
    private static DiscreteParameter discreteParameterSquire;
    private static ResourceDiscreteValue resourceDiscretePerimetrValue;
    private static ResourceDiscreteValue resourceDiscreteSquireValue;

    @BeforeMethod
    public void init() throws IOException, DocumentException {

        printService = new PrintServiceImpl();

        MockitoAnnotations.initMocks(this);
        globalTerritorialCommunity = new TerritorialCommunity();
        globalTerritorialCommunity.setName("Україна");

        Role roleUser = new Role(RoleType.USER,"description");
        Role roleRegistrator = new Role(RoleType.REGISTRATOR,"description");

        user = new User("user","$2a$10$Wcuw6mLD18wVT5diGYncJeVyL8J1bTSIly2IbLUX2bJ.UWZPC.qS.",
                roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","ACTIVE");
        user.setDateOfAccession(new Date());
        user.setTerritorialCommunity(globalTerritorialCommunity);
        user.setAddress(Arrays.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
        user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));

        registrator = new User("registrator","$2a$10$KJdq1wmP3MctLh.lEdAuseUCnSRdhJo8S7qwaZHFEUoGhfjOsOnrm",
                roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","ACTIVE");
        registrator.setDateOfAccession(new Date());
        registrator.setTerritorialCommunity(globalTerritorialCommunity);
        registrator.setAddress(Arrays.asList(
                new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12")));
        registrator.setPassport(Arrays.asList(new PassportInfo(registrator, "ББ", "11111", "Народом України")));

        land = new ResourceType("земельний");
        tome = new Tome(registrator, "12345");

        resource = new Resource(land, "111111", "ліс", registrator, new Date(), "active", tome, "підстава на внесення");
        resource.setResourcesId(1);

        inquiryOutput = new Inquiry("OUTPUT", new Date(), user, registrator, resource);
        inquiryInput = new Inquiry("INPUT", new Date(), user, registrator, resource);

        polygon = new Polygon(24.0, 49.0, 26.0, 50.0, resource);
        polygon.setCoordinates("[{\"lat\":49,\"lng\":24},{\"lat\":50,\"lng\":26}]");

        discreteParameterPerimetr = new DiscreteParameter(land, "периметр", "м");
        discreteParameterPerimetr.setDiscreteParameterId(1);

        discreteParameterSquire = new DiscreteParameter(land, "площа", "га");
        discreteParameterSquire.setDiscreteParameterId(2);

        land.setDiscreteParameters(Arrays.asList(discreteParameterPerimetr, discreteParameterSquire));

        resourceDiscretePerimetrValue = new ResourceDiscreteValue(resource, discreteParameterPerimetr, 50.0);
        resourceDiscreteSquireValue = new ResourceDiscreteValue(resource, discreteParameterSquire, 400.0);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPrintProcurationThrowsExceptionOnInvalidInquiryType() throws IOException, DocumentException{

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryInput);

        printService.printProcuration(OUTPUT_ID);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPrintExtractThrowsExceptionOnInvalidInquiryType() throws IOException, DocumentException {

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryInput);

        printService.printExtract(OUTPUT_ID);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPrintProcurationOnSubmitInfoThrowsExceptionOnInvalidInquiryType() throws IOException, DocumentException {

        when(inquiryRepository.getOne(INPUT_ID)).thenReturn(inquiryOutput);

        printService.printProcurationOnSubmitInfo(INPUT_ID);

    }

    @Test
    public void testPrintProcurationInvokesEntitiesWithCorrectParameter() throws IOException, DocumentException {

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryOutput);

        printService.printProcuration(OUTPUT_ID);

        verify(inquiryRepository, times(2)).getOne(OUTPUT_ID);

    }

    @Test
    public void testPrintExtractInvokesEntitiesWithCorrectParameter() throws IOException, DocumentException {

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryOutput);

        printService.printExtract(OUTPUT_ID);

        verify(inquiryRepository, times(2)).getOne(OUTPUT_ID);
        verify(polygonRepository).findByResource(resource);
        verify(resourceDiscrete).findByResource(resource);

    }

    @Test
    public void testPrintProcurationOnSubmitInfoInvokesEntitiesWithCorrectParameter() throws IOException, DocumentException {

        when(inquiryRepository.getOne(INPUT_ID)).thenReturn(inquiryInput);

        printService.printProcurationOnSubmitInfo(INPUT_ID);

        verify(inquiryRepository, times(2)).getOne(INPUT_ID);
        verify(polygonRepository).findByResource(resource);
        verify(resourceDiscrete).findByResource(resource);

    }


    @Test
    public void testPrintProcurationExistsNotNullCorrectFileFormat() throws IOException, DocumentException {

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryOutput);

        ByteArrayOutputStream bos = printService.printProcuration(OUTPUT_ID);

        OutputStream os = null;
        File file = null;
        try {
            file = new File(".\\target\\procuration.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

        checkExistsNotNullCorrectFileFormat(file);

    }


    @Test
    public void testPrintExtractExistsNotNullCorrectFileFormat() throws IOException, DocumentException {

        when(inquiryRepository.getOne(OUTPUT_ID)).thenReturn(inquiryOutput);
        when(polygonRepository.findByResource(resource)).thenReturn(Arrays.asList(polygon));
        when(resourceDiscrete.findByResource(resource)).thenReturn(Arrays.asList(resourceDiscretePerimetrValue, resourceDiscreteSquireValue));

        ByteArrayOutputStream bos = printService.printExtract(OUTPUT_ID);

        OutputStream os = null;
        File file = null;
        try {
            file = new File(".\\target\\extract.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

        checkExistsNotNullCorrectFileFormat(file);

    }


    @Test
    public void testPrintProcurationOnSubmitInfoExistsNotNullCorrectFileFormat() throws IOException, DocumentException {

        when(inquiryRepository.getOne(INPUT_ID)).thenReturn(inquiryInput);
        when(polygonRepository.findByResource(resource)).thenReturn(Arrays.asList(polygon));
        when(resourceDiscrete.findByResource(resource)).thenReturn(Arrays.asList(resourceDiscretePerimetrValue, resourceDiscreteSquireValue));

        ByteArrayOutputStream bos = printService.printProcurationOnSubmitInfo(INPUT_ID);

        OutputStream os = null;
        File file = null;
        try {
            file = new File(".\\target\\procurationOnSubmit.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

        checkExistsNotNullCorrectFileFormat(file);

    }


    public static void checkExistsNotNullCorrectFileFormat(File file){

        if(!file.exists()){
            Assert.fail("file is not created");
        }
        if(file.length() == 0){
            Assert.fail("file is empty");
        }
        String [] name = file.getName().split("\\.");
        if(!name[name.length-1].equals("pdf")){
            Assert.fail("created file is not pdf format");
        }
    }

}

