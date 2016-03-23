package org.registrator.community.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

public class XmlAnalyzerUtil {
	
	public <T> void marshal(T object, String fileName){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(object, new FileOutputStream(fileName));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T unmarshal(Class<T> clazz,String fileName){

		try{
			InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
			if(is != null){
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				return (T)  unmarshaller.unmarshal(is);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	public boolean isDocumentValib(String fileName,String schemaName){
		return false;
	}
}
