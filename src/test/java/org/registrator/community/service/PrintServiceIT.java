package org.registrator.community.service;

import java.io.*;
import java.util.Arrays;

import com.itextpdf.text.DocumentException;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
public class PrintServiceIT extends AbstractTestNGSpringContextTests {

    @Autowired
    PrintService printServiceImpl;

    private static final int META_INFORMATION_SIZE = 2300;// contains CreationDate in ms, can't compare different pdf files


    @Test
    public void testPrintProcurationSaveCorrectData() throws IOException, DocumentException {

        ByteArrayOutputStream bos = printServiceImpl.printProcuration(1);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\procuration.pdf");
        byte [] expected = readContentIntoByteArray(file);

        assertEquals(actual, expected);

    }

    @Test
    public void testPrintExtractSaveCorrectData() throws IOException, DocumentException {

        ByteArrayOutputStream bos = printServiceImpl.printExtract(1);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\extract.pdf");
        byte [] expected = readContentIntoByteArray(file);

        assertEquals(actual, expected);

    }

    @Test
    public void testPrintProcurationOnSubmitInfoSaveCorrectData() throws IOException, DocumentException {

        ByteArrayOutputStream bos = printServiceImpl.printProcurationOnSubmitInfo(2);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\procurationOnSubmit.pdf");
        byte [] expected = readContentIntoByteArray(file);

        assertEquals(actual, expected);

    }

    private static byte[] readContentIntoByteArray(File file) throws IOException
    {
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length() - META_INFORMATION_SIZE];
        try
        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            fileInputStream.close();
        }
        return bFile;
    }

}


