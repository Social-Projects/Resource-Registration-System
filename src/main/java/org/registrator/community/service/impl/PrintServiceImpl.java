package org.registrator.community.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dto.*;
import org.registrator.community.dto.HeaderDTO.HeaderContent;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.ResourceDiscreteValueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * <p>This class works with PDF documents. It has methods to generate : </p>
 * <p>"Доручення про надання витягу"</p>
 * <p>"Витяг"</p>
 * <p>"Доручення на внесення"</p>
 * @author Vitalii Horban
 * @version 1.0
 */

@Service
public class PrintServiceImpl implements PrintService {

	@Autowired
	InquiryRepository inquiryRepository;

	@Autowired
	PolygonRepository polygonRepository;

	@Autowired
	ResourceDiscreteValueService resourceDiscrete;

	private Font fontTimesNewRomanBase;
	private Font fontTimesNewRomanFat;
	private Font fontTimesNewRomanItalic;

	public PrintServiceImpl() throws IOException, DocumentException {
		BaseFont timesNewRomanBase = BaseFont.createFont("Times New Roman/TIMES.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		fontTimesNewRomanBase = new Font(timesNewRomanBase);
		fontTimesNewRomanFat = new Font(timesNewRomanBase, 14, Font.BOLD);
		fontTimesNewRomanItalic = new Font(timesNewRomanBase, 14, Font.ITALIC);
	}

	/**
	 * <p>
	 * creates pdf document "Доручення про надання витягу "
	 * </p>
	 */

	@Override
	public ByteArrayOutputStream printProcuration(Integer inquiryId) throws IOException, DocumentException {

		Inquiry inquiry = inquiryRepository.getOne(inquiryId);

		ByteArrayOutputStream document = null;
		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)) {

			document = createMandatToExtractPdf(createMandatToExractDto(inquiryId), createMandateToExtractHeader());

		}
        else {
            throw new IllegalArgumentException();
        }
		return document;
	}

	/**
	 * <p>
	 * creates Dto to fill content of mandate to extract
	 * "Доручення про надання витягу "
	 * </p>
	 */

	private MandatToExtractDTO createMandatToExractDto(int inquiryId) {
		MandatToExtractDTO extract = null;

		Inquiry inquiry = inquiryRepository.getOne(inquiryId);
		User user = inquiry.getUser();
		Address address = user.getAddress().get((user.getAddress().size() - 1));
		User registrator = inquiry.getRegistrator();
		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));
		String identifier = inquiry.getResource().getIdentifier();

		extract = new MandatToExtractDTO();
		extract.setDestinationOfFile("D:\\mandateToExtract" + inquiryId + ".pdf");
		extract.setFirstNameOfUser(user.getFirstName());
		extract.setLastNameOfUser(user.getLastName());
		extract.setMiddleNameOfUser(user.getMiddleName());
		extract.setZipcodeOfUser(address.getPostCode());
		extract.setCountryOfUser("Україна");
		extract.setCityOfUser(address.getCity());
		extract.setStreetNameOfUser(address.getStreet());
		extract.setStreetNumberOfUser(address.getBuilding());
		extract.setHomeNumberOfUser(address.getFlat());
		extract.setRegistratorFirtstName(registrator.getFirstName());
		extract.setRegistratorLastName(registrator.getLastName());
		extract.setRegistratorMiddleName(registrator.getMiddleName());
		extract.setRegistratorZipcode(addressRegistrator.getPostCode());
		extract.setRegistratorCountry("Україна");
		extract.setRegistratorCity(addressRegistrator.getCity());
		extract.setRegistratorStreetName(addressRegistrator.getStreet());
		extract.setRegistratorStreetNumber(addressRegistrator.getBuilding());
		extract.setRegistratorHomeNumber(addressRegistrator.getFlat());
		extract.setObjectNumber(identifier);

		return extract;
	}

	/**
	 * <p>
	 * creates header of mandate to extract document
	 * "Доручення про надання витягу "
	 * </p>
	 */

	private HeaderDTO createMandateToExtractHeader() throws BadElementException, IOException{
		HeaderDTO header = new HeaderDTO();

		header.setImage("/images/herb.png", 28, 39);
		header.addSentenceToHeaderContent("Народ України", 13, fontTimesNewRomanFat);
		header.addSentenceToHeaderContent("Безпосередня влада народу", 16, fontTimesNewRomanFat);
		header.addSentenceToHeaderContent("Людина як найвища соціальна цінність в Україні", 19,
				fontTimesNewRomanFat);

		return header;
	}

	/**
	 * <p>
	 * creates main content of mandate to extract document
	 * "Доручення про надання витягу"
	 * </p>
	 */
	private ByteArrayOutputStream createMandatToExtractPdf(MandatToExtractDTO extract, HeaderDTO header)
			throws IOException, DocumentException {
		ByteArrayOutputStream bos = null;

		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);
		bos = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, bos);
		document.open();

		document.add(header.getImage());

		for (HeaderContent h : header.getHeaderText()) {
			Paragraph p = new Paragraph(h.getStringContent(), h.getFontType());
			p.getFont().setSize(h.getFontSize());
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
		}

		Paragraph header4 = new Paragraph(extract.getLastNameOfUser() + " " + extract.getFirstNameOfUser() + " "
				+ extract.getMiddleNameOfUser(), fontTimesNewRomanFat);
		header4.getFont().setSize(22f);
		header4.setAlignment(Element.ALIGN_CENTER);
		document.add(header4);

		Paragraph blank = new Paragraph("   ");
		blank.getFont().setSize(10f);
		document.add(blank);

		Paragraph header5 = new Paragraph("Адреса для листування: " + extract.getZipcodeOfUser() + " , "
				+ extract.getCityOfUser() + " , " + extract.getStreetNameOfUser() + " "
				+ extract.getStreetNumberOfUser() + "/" + extract.getHomeNumberOfUser(), fontTimesNewRomanBase);
		header5.getFont().setSize(10f);
		document.add(header5);

		Paragraph header6 = new Paragraph("«" + extract.getCurrentDay() + "» " + extract.getCurrentMonth() + " 20"
				+ extract.getCurrentYear() + " року", fontTimesNewRomanBase);
		header6.getFont().setSize(10f);
		document.add(header6);

		Paragraph header7 = new Paragraph("Народному реєстратору", fontTimesNewRomanFat);
		header7.setAlignment(Element.ALIGN_RIGHT);
		header7.getFont().setSize(12f);
		document.add(header7);

		Paragraph header8 = new Paragraph(extract.getRegistratorLastName() + " "
				+ extract.getRegistratorFirtstName() + " " + extract.getRegistratorMiddleName(),
				fontTimesNewRomanFat);
		header8.setAlignment(Element.ALIGN_RIGHT);
		header8.getFont().setSize(12f);
		document.add(header8);

		Paragraph header9 = new Paragraph("(вул. " + extract.getRegistratorStreetName()
				+ extract.getRegistratorStreetNumber() + " / " + extract.getRegistratorHomeNumber() + ", " + "м. "
				+ extract.getRegistratorCity() + ", " + extract.getRegistratorZipcode() + ")",
				fontTimesNewRomanBase);
		header9.setAlignment(Element.ALIGN_RIGHT);
		header9.getFont().setSize(12f);
		document.add(header9);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header10 = new Paragraph("Доручення", fontTimesNewRomanFat);
		header10.setAlignment(Element.ALIGN_CENTER);
		header10.getFont().setSize(22f);
		document.add(header10);

		Paragraph header11 = new Paragraph("про надання витягу з Децентралізованого", fontTimesNewRomanBase);
		header11.setAlignment(Element.ALIGN_CENTER);
		header11.getFont().setSize(12f);
		document.add(header11);

		Paragraph header12 = new Paragraph("майнового реєстру природних ресурсів України", fontTimesNewRomanBase);
		header12.setAlignment(Element.ALIGN_CENTER);
		header12.getFont().setSize(12f);
		document.add(header12);

		document.add(Chunk.NEWLINE);

		Paragraph header13 = new Paragraph("Я, людина " + extract.getLastNameOfUser() + " "
				+ extract.getFirstNameOfUser() + " " + extract.getMiddleNameOfUser()
				+ " доручаю надати мені витяг з Децентралізованого майнового реєстру природних ресурсів України щодо об’єкту з номером "
				+ extract.getObjectNumber(), fontTimesNewRomanBase);
		header13.setAlignment(Element.ALIGN_LEFT);
		header13.getFont().setSize(12f);
		document.add(header13);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header14 = new Paragraph("Людина: ", fontTimesNewRomanBase);
		header14.setAlignment(Element.ALIGN_LEFT);
		header14.getFont().setSize(12f);
		header14.add(Chunk.TABBING);
		char[] firstNameArray = extract.getFirstNameOfUser().toCharArray();
		char[] MiddleNameArray = extract.getMiddleNameOfUser().toCharArray();
		String firstNameC = "" + firstNameArray[0];
		String MiddleNameC = "" + MiddleNameArray[0];
		String nameForFooter = extract.getLastNameOfUser() + " " + firstNameC.toUpperCase() + ". "
				+ MiddleNameC.toUpperCase() + ".";
		header14.add(nameForFooter);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);

		header14.add("______________(підпис)");
		document.add(header14);

		document.close();

		return bos;
	}

	/**
	 * <p>
	 * creates pdf document "Витяг"
	 * </p>
	 */
	@Override
	public ByteArrayOutputStream printExtract(Integer inquiryId) throws IOException, DocumentException{

		ByteArrayOutputStream document = null;
		Inquiry inquiry = inquiryRepository.getOne(inquiryId);

		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)) {

			document = createExtractPdf(createExractDto(inquiryId), createExtractHeader());

		}
        else {
            throw new IllegalArgumentException();
        }
		return document;
	}

	/**
	 * <p>
	 * creates header of extract document "Витяг"
	 * </p>
	 */
	private HeaderDTO createExtractHeader() throws IOException, BadElementException{
		HeaderDTO header = new HeaderDTO();

		header.setImage("/images/herb.png", 28, 39);
		header.addSentenceToHeaderContent("Територіальна громада міста Львова", 14, fontTimesNewRomanFat);
		header.addSentenceToHeaderContent("Уповноважений з питань реєстрації(народний реєстратор)", 16,
				fontTimesNewRomanFat);

		return header;
	}

	/**
	 * <p>
	 * creates Dto to fill content of extract "Витяг"
	 * </p>
	 */
	@SuppressWarnings("deprecation")
	private ExtractDTO createExractDto(int inquiryId) {
		ExtractDTO extract = null;

		Inquiry inquiry = inquiryRepository.getOne(inquiryId);

		User registrator = inquiry.getRegistrator();
		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));
		String identifier = inquiry.getResource().getIdentifier();
		Resource resource = inquiry.getResource();
		ResourceType resType = inquiry.getResource().getType();
		String reasonForInclusion = inquiry.getResource().getReasonInclusion();
		Date inquireDate = inquiry.getDate();
		List<Polygon> polygon = polygonRepository.findByResource(resource);
		List<Double> totalCoordinates = new ArrayList<>();
		List<ResourceDiscreteValue> discreteValues = resourceDiscrete.findByResource(resource);
		Double perimetr = null;
		Double squire = null;
		for (ResourceDiscreteValue r : discreteValues) {
			if (r.getDiscreteParameter().getDiscreteParameterId() == 1) {
				perimetr = r.getValue();
			} else if (r.getDiscreteParameter().getDiscreteParameterId() == 2) {
				squire = r.getValue();
			}
		}

		String dateInquireYear = String.valueOf(inquireDate.getYear());
		char parsedDateInquire[] = dateInquireYear.toCharArray();
		dateInquireYear = "" + parsedDateInquire[1] + parsedDateInquire[2];

		for (Polygon p : polygon) {
			Gson gson = new Gson();
			List<PointDTO> pointDTOs = gson.fromJson(p.getCoordinates(), new TypeToken<List<PointDTO>>() {
			}.getType());

			for (PointDTO a : pointDTOs) {
				totalCoordinates.add(a.getLng());
				totalCoordinates.add(a.getLat());
			}
		}

		extract = new ExtractDTO();
		extract.setResourceId(resource.getResourcesId());
		extract.setDestination("D:\\extract" + inquiryId + ".pdf");
		extract.setObjectName(resource.getDescription());
		extract.setObjectClass("природний ресурс");
		extract.setSubObjectClass(resType.getTypeName());
		extract.setTotalCoordinates(totalCoordinates);
		extract.setReasonForInclusion(reasonForInclusion);
		extract.setRequestDay(String.valueOf(inquireDate.getDate()));
		extract.setRequestMonth(currentMonth(String.valueOf(inquireDate.getMonth())));
		extract.setRequestMonthNumber(String.valueOf(inquireDate.getMonth() + 1));
		extract.setObjectPerimetr(String.valueOf(perimetr));
		extract.setTotalSquareOfObject(String.valueOf(squire));
		int month = inquireDate.getMonth() + 1;
		if (month < 10) {
			extract.setRequestMonthNumber("0" + month);
		} else {
			extract.setRequestMonthNumber(String.valueOf(month));
		}

		extract.setRequestYear(String.valueOf(dateInquireYear));
		extract.setRegistratorFirtstName(registrator.getFirstName());
		extract.setRegistratorLastName(registrator.getLastName());
		extract.setRegistratorMiddleName(registrator.getMiddleName());
		extract.setRegistratorZipcode(addressRegistrator.getPostCode());
		extract.setRegistratorCountry("Україна");
		extract.setRegistratorCity(addressRegistrator.getCity());
		extract.setRegistratorStreetName(addressRegistrator.getStreet());
		extract.setRegistratorStreetNumber(addressRegistrator.getBuilding());
		extract.setRegistratorHomeNumber(addressRegistrator.getFlat());
		extract.setObjectNumber(identifier);

		return extract;
	}

	/**
	 * <p>
	 * creates main content of extract document "Витяг"
	 * </p>
	 */

	private ByteArrayOutputStream createExtractPdf(ExtractDTO extract, HeaderDTO header)
			throws IOException, DocumentException {

		ByteArrayOutputStream bos = null;

		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);
		bos = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, bos);
		document.open();

		document.add(header.getImage());

		for (HeaderContent h : header.getHeaderText()) {
			Paragraph p = new Paragraph(h.getStringContent(), h.getFontType());
			p.getFont().setSize(h.getFontSize());
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
		}

		document.add(Chunk.NEWLINE);

		Paragraph header4 = new Paragraph("«" + extract.getCurrentDay() + "» " + extract.getCurrentMonth() + " 20"
				+ extract.getCurrentYear() + " року", fontTimesNewRomanFat);
		header4.getFont().setSize(12f);
		header4.setTabSettings(new TabSettings(440f));
		header4.add(Chunk.TABBING);
		header4.add(new Chunk("№" + extract.getCurrentDay() + extract.getCurrentMonthNumber()
				+ extract.getCurrentYear() + "/" + extract.getResourceId()));
		document.add(header4);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header5 = new Paragraph("Витяг", fontTimesNewRomanFat);
		header5.getFont().setSize(22f);
		header5.setAlignment(Element.ALIGN_CENTER);
		document.add(header5);

		Paragraph header6 = new Paragraph("з Децентралізованого майнового реєстру", fontTimesNewRomanFat);
		header6.getFont().setSize(14f);
		header6.setAlignment(Element.ALIGN_CENTER);
		document.add(header6);

		Paragraph header7 = new Paragraph("природних ресурсів України", fontTimesNewRomanFat);
		header7.getFont().setSize(14f);
		header7.setAlignment(Element.ALIGN_CENTER);
		document.add(header7);

		document.add(Chunk.NEWLINE);

		Paragraph header8 = new Paragraph(
				"Відомості надаються на підставі пункту 4 Положення про "
						+ "Децентралізований майновий реєстр природних ресурсів України, що затверджено Народним волевиявленням №"
						+ extract.getRequestDay() + extract.getRequestMonthNumber() + extract.getRequestYear() + "/"
						+ extract.getResourceId() + " від " + extract.getRequestDay() + "."
						+ extract.getRequestMonthNumber() + "." + "20" + extract.getRequestYear(),
				fontTimesNewRomanBase);
		header8.getFont().setSize(12f);
		header8.setAlignment(Element.ALIGN_JUSTIFIED);
		header8.setTabSettings(new TabSettings(440f));
		document.add(header8);

		document.add(Chunk.NEWLINE);

		document.add(createExtractTable(extract));

		document.add(Chunk.NEWLINE);

		Paragraph footer = new Paragraph("Народний реєстратор", fontTimesNewRomanFat);
		footer.getFont().setSize(12f);
		footer.setTabSettings(new TabSettings(370f));
		footer.add(Chunk.TABBING);

		char[] firstNameArray = extract.getRegistratorFirtstName().toCharArray();
		char[] middleNameArray = extract.getRegistratorMiddleName().toCharArray();
		String firstNameC = "" + firstNameArray[0];
		String middleNameC = "" + middleNameArray[0];
		String nameForFooter = extract.getRegistratorLastName() + " " + firstNameC.toUpperCase() + ". "
				+ middleNameC.toUpperCase() + ".";

		footer.add(new Chunk("людина" + " " + nameForFooter));
		document.add(footer);

		document.close();

		return bos;
	}

	/**
	 * <p>
	 * creates table inside main content of extract document "Витяг"
	 * </p>
	 */
	public PdfPTable createExtractTable(ExtractDTO extract) {
		PdfPTable table = null;

		table = new PdfPTable(2);
		table.setWidthPercentage(100);
		Paragraph th = new Paragraph("Найменування об’єкту", fontTimesNewRomanFat);
		th.getFont().setSize(11f);
		th.setAlignment(Element.ALIGN_LEFT);

		Paragraph td = new Paragraph(extract.getObjectName(), fontTimesNewRomanItalic);
		td.getFont().setSize(11f);
		td.setAlignment(Element.ALIGN_LEFT);

		PdfPCell cell1 = new PdfPCell(th);
		PdfPCell cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Клас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(extract.getObjectClass(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Підклас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(extract.getSubObjectClass(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Власник об’єкту", fontTimesNewRomanFat);
		td = new Paragraph("народ України (Український народ)", fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Географічні координати кутів (вершин) об’єкту у форматі ГГ°ММ'СС,СС.",
				fontTimesNewRomanFat);
		cell1 = new PdfPCell(th);
		cell1.setMinimumHeight(30);
		table.addCell(cell1);

		// Neasted table
		PdfPTable nestedTable = new PdfPTable(2);
		Paragraph nth = new Paragraph("Північна широта", fontTimesNewRomanBase);
		nth.getFont().setSize(9);
		nth.setAlignment(Element.ALIGN_MIDDLE);
		nestedTable.addCell(nth);

		nth = new Paragraph("Східна довгота", fontTimesNewRomanBase);
		nestedTable.addCell(nth);

		for (Double d : extract.getTotalCoordinates()) {
			td = new Paragraph(String.format("%.4f", d), fontTimesNewRomanItalic);
			td.getFont().setSize(11f);
			nestedTable.addCell(td);

		}

		cell2 = new PdfPCell(nestedTable);
		table.addCell(cell2);
		// finish of neasted table

		th = new Paragraph("Лінійні розміри об’єкту, Д:Ш:В, м", fontTimesNewRomanFat);
		td = new Paragraph(extract.getLinearObjectSize(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Загальна площа об’єкту, га", fontTimesNewRomanFat);

		td = new Paragraph((extract.getTotalSquareOfObject() != null
				&& !extract.getTotalSquareOfObject().equalsIgnoreCase("null")) ? extract.getTotalSquareOfObject()
				: "відомості відсутні",
				fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Маса (вага) об’єкту, кг", fontTimesNewRomanFat);
		td = new Paragraph(extract.getWeight(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Периметр об’єкту, м", fontTimesNewRomanFat);

		td = new Paragraph(
				(extract.getObjectPerimetr() != null && !extract.getObjectPerimetr().equalsIgnoreCase("null"))
						? extract.getObjectPerimetr() : "відомості відсутні",
				fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Об’єм об’єкту, м3", fontTimesNewRomanFat);
		td = new Paragraph(extract.getObjectCapacity(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Підстава для внесення відомостей до Реєстру", fontTimesNewRomanFat);
		td = new Paragraph((extract.getReasonForInclusion() != null
				&& !extract.getReasonForInclusion().equalsIgnoreCase("null")) ? extract.getReasonForInclusion()
				: "відомості відсутні",
				fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("ПІБ та поштова адреса народного реєстратора", fontTimesNewRomanFat);
		td = new Paragraph(
				extract.getRegistratorLastName() + " " + extract.getRegistratorFirtstName() + " "
						+ extract.getRegistratorMiddleName() + ", " + extract.getRegistratorZipcode() + ", м. "
						+ extract.getRegistratorCity() + ", вул." + extract.getRegistratorStreetName() + " "
						+ extract.getRegistratorStreetNumber() + ", кв. " + extract.getRegistratorHomeNumber(),
				fontTimesNewRomanItalic);

		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Реєстраційний номер об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(extract.getObjectNumber(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Дата створення запису", fontTimesNewRomanFat);
		td = new Paragraph(extract.getRequestDay() + "." + extract.getRequestMonthNumber() + ".20"
				+ extract.getRequestYear() + " року", fontTimesNewRomanItalic);

		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		return table;
	}

	/**
	 * <p>
	 * creates pdf document "Доручення на внесення"
	 * </p>
	 */

	private HeaderDTO createMandateToInputHeader() throws IOException, BadElementException{
		HeaderDTO header = null;

		header = new HeaderDTO();
		header.setImage("/images/herb.png", 28, 39);
		header.addSentenceToHeaderContent("Народ України", 13, fontTimesNewRomanFat);
		header.addSentenceToHeaderContent("Безпосередня влада народу", 16, fontTimesNewRomanFat);
		header.addSentenceToHeaderContent("Людина як найвища соціальна цінність в Україні", 19,
				fontTimesNewRomanFat);

		return header;
	}

	/**
	 * <p>
	 * creates main content of document "Доручення на внесення"
	 * </p>
	 */
	private ByteArrayOutputStream createInputPdf(InputDTO extract, HeaderDTO header)
			throws IOException, DocumentException {

		ByteArrayOutputStream bos = null;

		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);

		bos = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, bos);
		document.open();

		document.add(header.getImage());

		for (HeaderContent h : header.getHeaderText()) {
			Paragraph p = new Paragraph(h.getStringContent(), h.getFontType());
			p.getFont().setSize(h.getFontSize());
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
		}

		document.add(Chunk.NEWLINE);

		Paragraph header5 = new Paragraph("Адреса для листування: " + extract.getRegistratorZipcode() + " , "
				+ extract.getRegistratorCity() + " , " + extract.getRegistratorStreetName() + " "
				+ extract.getRegistratorStreetNumber() + "/" + extract.getRegistratorHomeNumber(),
				fontTimesNewRomanBase);
		header5.getFont().setSize(10f);
		document.add(header5);

		Paragraph header6 = new Paragraph("«" + extract.getCurrentDay() + "» " + extract.getCurrentMonth() + " 20"
				+ extract.getCurrentYear() + " року", fontTimesNewRomanBase);
		header6.getFont().setSize(10f);
		document.add(header6);

		Paragraph header7 = new Paragraph("Народному реєстратору", fontTimesNewRomanFat);
		header7.setAlignment(Element.ALIGN_RIGHT);
		header7.getFont().setSize(12f);
		document.add(header7);

		Paragraph header8 = new Paragraph(extract.getRegistratorLastName() + " "
				+ extract.getRegistratorFirtstName() + " " + extract.getRegistratorMiddleName(),
				fontTimesNewRomanFat);
		header8.setAlignment(Element.ALIGN_RIGHT);
		header8.getFont().setSize(12f);
		document.add(header8);

		Paragraph header9 = new Paragraph("(вул. " + extract.getRegistratorStreetName()
				+ extract.getRegistratorStreetNumber() + " / " + extract.getRegistratorHomeNumber() + ", " + "м. "
				+ extract.getRegistratorCity() + ", " + extract.getRegistratorZipcode() + ")",
				fontTimesNewRomanBase);
		header9.setAlignment(Element.ALIGN_RIGHT);
		header9.getFont().setSize(12f);
		document.add(header9);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header10 = new Paragraph("Доручення", fontTimesNewRomanFat);
		header10.setAlignment(Element.ALIGN_CENTER);
		header10.getFont().setSize(22f);
		document.add(header10);

		Paragraph header11 = new Paragraph("про про внесення відомостей до  Децентралізованого",
				fontTimesNewRomanBase);
		header11.setAlignment(Element.ALIGN_CENTER);
		header11.getFont().setSize(12f);
		document.add(header11);

		Paragraph header12 = new Paragraph("майнового реєстру природних ресурсів України", fontTimesNewRomanBase);
		header12.setAlignment(Element.ALIGN_CENTER);
		header12.getFont().setSize(12f);
		document.add(header12);

		document.add(Chunk.NEWLINE);

		Paragraph header13 = new Paragraph(
				"Я, людина " + extract.getLastNameOfUser() + " " + extract.getFirstNameOfUser() + " "
						+ extract.getMiddleNameOfUser() + " доручаю внести до ДМРПРУ такі відомості:",
				fontTimesNewRomanBase);
		header13.setAlignment(Element.ALIGN_LEFT);
		header13.getFont().setSize(12f);
		document.add(header13);

		Paragraph h = new Paragraph("    ");
		h.getFont().setSize(8f);
		document.add(h);

		document.add(createInputTable(extract));

		document.add(Chunk.NEWLINE);

		Paragraph header14 = new Paragraph("Людина: ", fontTimesNewRomanBase);
		header14.setAlignment(Element.ALIGN_LEFT);
		header14.getFont().setSize(12f);
		header14.add(Chunk.TABBING);
		char[] firstNameArray = extract.getFirstNameOfUser().toCharArray();
		char[] MiddleNameArray = extract.getMiddleNameOfUser().toCharArray();
		String firstNameC = "" + firstNameArray[0];
		String MiddleNameC = "" + MiddleNameArray[0];
		String nameForFooter = extract.getLastNameOfUser() + " " + firstNameC.toUpperCase() + ". "
				+ MiddleNameC.toUpperCase() + ".";
		header14.add(nameForFooter);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);

		header14.add("______________(підпис)");
		document.add(header14);

		document.close();

		return bos;
	}

	/**
	 * <p>
	 * creates Dto to fill content of printProcurationOnSubmit
	 * "Доручення на внесення"
	 * </p>
	 */
	@SuppressWarnings("deprecation")
	private InputDTO createInputDto(int inquiryId) {

		InputDTO extract = null;

		Inquiry inquiry = inquiryRepository.getOne(inquiryId);

		User user = inquiry.getUser();
		User registrator = inquiry.getRegistrator();
		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));
		String identifier = inquiry.getResource().getIdentifier();
		Resource resource = inquiry.getResource();
		ResourceType resType = inquiry.getResource().getType();
		String reasonForInclusion = inquiry.getResource().getReasonInclusion();
		Date inquireDate = inquiry.getDate();
		List<Polygon> polygon = polygonRepository.findByResource(resource);
		List<Double> totalCoordinates = new ArrayList<>();
		List<ResourceDiscreteValue> discreteValues = resourceDiscrete.findByResource(resource);
		Double perimetr = null;
		Double squire = null;
		for (ResourceDiscreteValue r : discreteValues) {
			if (r.getDiscreteParameter().getDiscreteParameterId() == 1) {
				perimetr = r.getValue();
			} else if (r.getDiscreteParameter().getDiscreteParameterId() == 2) {
				squire = r.getValue();
			}
		}

		String dateInquireYear = String.valueOf(inquireDate.getYear());
		char parsedDateInquire[] = dateInquireYear.toCharArray();
		dateInquireYear = "" + parsedDateInquire[1] + parsedDateInquire[2];

		for (Polygon p : polygon) {
			Gson gson = new Gson();
			List<PointDTO> pointDTOs = gson.fromJson(p.getCoordinates(), new TypeToken<List<PointDTO>>() {
			}.getType());

			for (PointDTO a : pointDTOs) {
				totalCoordinates.add(a.getLng());
				totalCoordinates.add(a.getLat());
			}
		}

		extract = new InputDTO();
		extract.setFirstNameOfUser(user.getFirstName());
		extract.setLastNameOfUser(user.getLastName());
		extract.setMiddleNameOfUser(user.getMiddleName());
		extract.setResourceId(resource.getResourcesId());
		extract.setDestination("D:\\mandateToInput" + inquiryId + ".pdf");
		extract.setObjectName(resource.getDescription());
		extract.setObjectClass("природний ресурс");
		extract.setSubObjectClass(resType.getTypeName());
		extract.setTotalCoordinates(totalCoordinates);
		extract.setReasonForInclusion(reasonForInclusion);
		extract.setRequestDay(String.valueOf(inquireDate.getDate()));
		extract.setRequestMonth(currentMonth(String.valueOf(inquireDate.getMonth())));
		extract.setRequestMonthNumber(String.valueOf(inquireDate.getMonth() + 1));
		extract.setObjectPerimetr(String.valueOf(perimetr));
		extract.setTotalSquareOfObject(String.valueOf(squire));
		int month = inquireDate.getMonth() + 1;
		if (month < 10) {
			extract.setRequestMonthNumber("0" + month);
		} else {
			extract.setRequestMonthNumber(String.valueOf(month));
		}

		extract.setRequestYear(String.valueOf(dateInquireYear));
		extract.setRegistratorFirtstName(registrator.getFirstName());
		extract.setRegistratorLastName(registrator.getLastName());
		extract.setRegistratorMiddleName(registrator.getMiddleName());
		extract.setRegistratorZipcode(addressRegistrator.getPostCode());
		extract.setRegistratorCountry("Україна");
		extract.setRegistratorCity(addressRegistrator.getCity());
		extract.setRegistratorStreetName(addressRegistrator.getStreet());
		extract.setRegistratorStreetNumber(addressRegistrator.getBuilding());
		extract.setRegistratorHomeNumber(addressRegistrator.getFlat());
		extract.setObjectNumber(identifier);

		return extract;
	}

	/**
	 * <p>
	 * creates table inside main content of printProcurationOnSubmit document
	 * "Доручення на внесення"
	 * </p>
	 */
	public PdfPTable createInputTable(InputDTO extract) {

		PdfPTable table = null;
		table = new PdfPTable(2);
		table.setWidthPercentage(100);
		Paragraph th = new Paragraph("Найменування об’єкту", fontTimesNewRomanFat);
		th.getFont().setSize(11f);
		th.setAlignment(Element.ALIGN_LEFT);

		Paragraph td = new Paragraph(extract.getObjectName(), fontTimesNewRomanItalic);
		td.getFont().setSize(11f);
		td.setAlignment(Element.ALIGN_LEFT);

		PdfPCell cell1 = new PdfPCell(th);
		PdfPCell cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Клас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(extract.getObjectClass(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Підклас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(extract.getSubObjectClass(), fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Географічні координати кутів (вершин) об’єкту у форматі ГГ°ММ'СС,СС.",
				fontTimesNewRomanFat);
		cell1 = new PdfPCell(th);
		cell1.setMinimumHeight(10);
		table.addCell(cell1);

		// Neasted table
		PdfPTable nestedTable = new PdfPTable(2);
		Paragraph nth = new Paragraph("Північна широта", fontTimesNewRomanBase);
		nth.getFont().setSize(9);
		nth.setAlignment(Element.ALIGN_MIDDLE);
		nestedTable.addCell(nth);

		nth = new Paragraph("Східна довгота", fontTimesNewRomanBase);
		nestedTable.addCell(nth);

		for (Double d : extract.getTotalCoordinates()) {
			td = new Paragraph(String.format("%.4f", d), fontTimesNewRomanItalic);
			td.getFont().setSize(11f);
			nestedTable.addCell(td);

		}

		cell2 = new PdfPCell(nestedTable);
		table.addCell(cell2);
		// finish of neasted table

		th = new Paragraph("Загальна площа об’єкту, га", fontTimesNewRomanFat);
		td = new Paragraph((extract.getTotalSquareOfObject() != null
				&& !extract.getTotalSquareOfObject().equalsIgnoreCase("null")) ? extract.getTotalSquareOfObject()
				: "відомості відсутні",
				fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Периметр об’єкту, м", fontTimesNewRomanFat);
		td = new Paragraph(
				(extract.getObjectPerimetr() != null && !extract.getTotalSquareOfObject().equalsIgnoreCase("null"))
						? extract.getObjectPerimetr() : "відомості відсутні",
				fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		return table;
	}

	/**
	 * <p>
	 * creates pdf document "Доручення на внесення"
	 * </p>
	 */

	@Override
	public ByteArrayOutputStream printProcurationOnSubmitInfo(Integer inquiryId) throws DocumentException, IOException{

		ByteArrayOutputStream document = null;

		Inquiry inquiry = inquiryRepository.getOne(inquiryId);

		if (inquiry.getInquiryType().equals(InquiryType.INPUT)) {

			document = createInputPdf(createInputDto(inquiryId), createMandateToInputHeader());

		}
        else {
            throw new IllegalArgumentException();
        }
		return document;
	}

	/**
	 * <p>
	 * create string representation of month in cyrillic
	 * </p>
	 */
	private String currentMonth(String month) {

		if (month.equals("0"))
			return "Січня";
		else if (month.equals("1"))
			return "Лютого";
		else if (month.equals("2"))
			return "Березня";
		else if (month.equals("3"))
			return "Квітня";
		else if (month.equals("4"))
			return "Травня";
		else if (month.equals("5"))
			return "Червня";
		else if (month.equals("6"))
			return "Липня";
		else if (month.equals("7"))
			return "Серпня";
		else if (month.equals("8"))
			return "Вересня";
		else if (month.equals("9"))
			return "Жовтня";
		else if (month.equals("10"))
			return "Листопада";
		else if (month.equals("11"))
			return "Грудня";
		return "UNDEFINED";
	}

}
